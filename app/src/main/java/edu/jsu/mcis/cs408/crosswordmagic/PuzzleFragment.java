package edu.jsu.mcis.cs408.crosswordmagic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PuzzleFragment extends Fragment {

    private CrosswordMagicController controller;

    public PuzzleFragment() {
        super();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_puzzle, container, false);

        controller = ((MainActivity) requireActivity()).getController();

        controller.getGridLetters();
        controller.getGridNumbers();
        controller.getGridDimensions();
        controller.getCluesAcross();
        controller.getCluesDown();

        return view;
    }
}