package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.jsu.mcis.cs408.crosswordmagic.CrosswordMagicController;
import edu.jsu.mcis.cs408.crosswordmagic.MainActivity;
import edu.jsu.mcis.cs408.crosswordmagic.PuzzleListItem;
import edu.jsu.mcis.cs408.crosswordmagic.R;

public class WelcomeActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button playButton;
    private Button getNewPuzzleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        spinner = findViewById(R.id.spinner_puzzles);
        playButton = findViewById(R.id.button_play);
        getNewPuzzleButton = findViewById(R.id.button_get_new_puzzles);

        CrosswordMagicController controller = new CrosswordMagicController(this);
        PuzzleListItem[] puzzles = controller.getPuzzleList();

        ArrayAdapter<PuzzleListItem> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                puzzles
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        playButton.setOnClickListener(v -> {
            PuzzleListItem selected = (PuzzleListItem) spinner.getSelectedItem();

            if (selected == null) {
                Toast.makeText(
                        WelcomeActivity.this,
                        "No puzzle available. Download a puzzle first.",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            intent.putExtra("puzzleid", selected.getId());
            startActivity(intent);
        });

        getNewPuzzleButton.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }
}