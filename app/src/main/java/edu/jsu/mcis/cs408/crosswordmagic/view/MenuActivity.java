package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.jsu.mcis.cs408.crosswordmagic.CrosswordMagicController;
import edu.jsu.mcis.cs408.crosswordmagic.MainActivity;
import edu.jsu.mcis.cs408.crosswordmagic.R;
import edu.jsu.mcis.cs408.crosswordmagic.WebServiceDAO;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button playButton;

    private int selectedPuzzleId = 1;
    private int[] puzzleIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recycler_puzzles);
        playButton = findViewById(R.id.button_play);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadPuzzleList();

        playButton.setOnClickListener(v -> downloadAndOpenPuzzle());
    }

    private void loadPuzzleList() {
        new Thread(() -> {

            WebServiceDAO webServiceDAO = new WebServiceDAO();
            JSONArray data = webServiceDAO.list();

            String[] puzzles = new String[0];
            int[] ids = new int[0];

            try {
                if (data != null) {
                    puzzles = new String[data.length()];
                    ids = new int[data.length()];

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject puzzle = data.getJSONObject(i);
                        puzzles[i] = puzzle.getString("name");
                        ids[i] = puzzle.getInt("id");
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            String[] finalPuzzles = puzzles;
            int[] finalIds = ids;

            runOnUiThread(() -> {
                puzzleIds = finalIds;

                if (puzzleIds.length > 0) {
                    selectedPuzzleId = puzzleIds[0];
                }

                PuzzleAdapter adapter = new PuzzleAdapter(finalPuzzles, position -> {
                    selectedPuzzleId = puzzleIds[position];
                });

                recyclerView.setAdapter(adapter);

                if (finalPuzzles.length == 0) {
                    Toast.makeText(MenuActivity.this,
                            "No puzzles found.",
                            Toast.LENGTH_SHORT).show();
                }
            });

        }).start();
    }

    private void downloadAndOpenPuzzle() {
        new Thread(() -> {

            CrosswordMagicController controller =
                    new CrosswordMagicController(MenuActivity.this);

            int databasePuzzleId = controller.downloadPuzzle(selectedPuzzleId);

            runOnUiThread(() -> {
                if (databasePuzzleId > 0) {
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("puzzleid", databasePuzzleId);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MenuActivity.this,
                            "Unable to download puzzle.",
                            Toast.LENGTH_SHORT).show();
                }
            });

        }).start();
    }
}