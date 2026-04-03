package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import edu.jsu.mcis.cs408.crosswordmagic.MainActivity;
import edu.jsu.mcis.cs408.crosswordmagic.R;

public class MenuActivity extends AppCompatActivity {

    private ListView listView;
    private Button playButton;
    private int selectedPuzzleId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listView = findViewById(R.id.list_puzzles);
        playButton = findViewById(R.id.button_play);

        String[] puzzles = {
                "1: NY Times (Mon, Feb 24, 2025)",
                "2: NY Times (Tue, Feb 25, 2025)",
                "3: NY Times (Wed, Feb 26, 2025)"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_single_choice,
                puzzles
        );

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(0, true);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPuzzleId = position + 1;
        });

        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            intent.putExtra("puzzleid", selectedPuzzleId);
            startActivity(intent);
        });
    }
}