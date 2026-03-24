package edu.jsu.mcis.cs408.crosswordmagic;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import edu.jsu.mcis.cs408.crosswordmagic.PuzzleFragment;

public class MainActivity extends AppCompatActivity {

    private CrosswordMagicController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new CrosswordMagicController(this);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Puzzle"));
        tabLayout.addTab(tabLayout.newTab().setText("Clues"));

        showFragment(new PuzzleFragment());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    showFragment(new PuzzleFragment());
                }
                else {
                    showFragment(new ClueFragment());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit();
    }

    public CrosswordMagicController getController() {
        return controller;
    }
}