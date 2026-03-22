package edu.jsu.mcis.cs408.crosswordmagic;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CrosswordMagicController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new CrosswordMagicController(this);
    }

    public CrosswordMagicController getController() {
        return controller;
    }
}