package edu.jsu.mcis.cs408.crosswordmagic;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);

        DAOFactory factory = new DAOFactory(this);
        SQLiteDatabase db = factory.getReadableDatabase();

        int count = factory.getWordDAO().countWords(db);
        statusText.setText("Number of Words in Default Puzzle: " + count);

        db.close();
    }
}