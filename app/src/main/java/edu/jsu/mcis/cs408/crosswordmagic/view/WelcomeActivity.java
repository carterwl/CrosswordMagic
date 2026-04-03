package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import edu.jsu.mcis.cs408.crosswordmagic.CrosswordMagicModel;
import edu.jsu.mcis.cs408.crosswordmagic.MainActivity;
import edu.jsu.mcis.cs408.crosswordmagic.R;
import edu.jsu.mcis.cs408.crosswordmagic.model.PuzzleListItem;

public class WelcomeActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        spinner = findViewById(R.id.spinner_puzzles);
        playButton = findViewById(R.id.button_play);

        CrosswordMagicModel model = new CrosswordMagicModel(this);
        PuzzleListItem[] puzzles = model.getPuzzleList(this);

        ArrayAdapter<PuzzleListItem> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                puzzles
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PuzzleListItem selected = (PuzzleListItem) spinner.getSelectedItem();

                if (selected != null) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    intent.putExtra("puzzleid", selected.getId());
                    startActivity(intent);
                }
            }
        });
    }
}