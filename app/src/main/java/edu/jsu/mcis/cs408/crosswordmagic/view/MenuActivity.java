package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.jsu.mcis.cs408.crosswordmagic.CrosswordMagicController;
import edu.jsu.mcis.cs408.crosswordmagic.MainActivity;
import edu.jsu.mcis.cs408.crosswordmagic.R;
import edu.jsu.mcis.cs408.crosswordmagic.WebServiceDAO;

public class MenuActivity extends AppCompatActivity {

    private ListView listView;
    private Button playButton;

    private int selectedPuzzleId = 1;
    private int[] puzzleIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listView = findViewById(R.id.list_puzzles);
        playButton = findViewById(R.id.button_play);

        WebServiceDAO webServiceDAO = new WebServiceDAO();
        JSONArray data = webServiceDAO.list();

        String[] puzzles = new String[0];
        puzzleIds = new int[0];

        try {
            if (data != null) {
                puzzles = new String[data.length()];
                puzzleIds = new int[data.length()];

                for (int i = 0; i < data.length(); i++) {
                    JSONObject puzzle = data.getJSONObject(i);
                    puzzles[i] = puzzle.getString("name");
                    puzzleIds[i] = puzzle.getInt("id");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_single_choice,
                puzzles
        );

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        if (puzzles.length > 0) {
            listView.setItemChecked(0, true);
            selectedPuzzleId = puzzleIds[0];
        }

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPuzzleId = puzzleIds[position];
        });

        playButton.setOnClickListener((View v) -> {
            CrosswordMagicController controller =
                    new CrosswordMagicController(MenuActivity.this);

            int databasePuzzleId = controller.downloadPuzzle(selectedPuzzleId);

            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            intent.putExtra("puzzleid", databasePuzzleId);
            startActivity(intent);
        });
    }
}