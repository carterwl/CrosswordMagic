package edu.jsu.mcis.cs408.crosswordmagic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.beans.PropertyChangeEvent;

import edu.jsu.mcis.cs408.crosswordmagic.CrosswordMagicController;
import edu.jsu.mcis.cs408.crosswordmagic.MainActivity;
import edu.jsu.mcis.cs408.crosswordmagic.databinding.FragmentPuzzleBinding;
import edu.jsu.mcis.cs408.crosswordmagic.view.AbstractView;
public class PuzzleFragment extends Fragment implements AbstractView {

    public static final String TAG = "PuzzleFragment";

    private CrosswordMagicController controller;
    private FragmentPuzzleBinding binding;

    public PuzzleFragment() {
        super();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPuzzleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = ((MainActivity) requireActivity()).getController();
        controller.addView(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();
        Object value = evt.getNewValue();


        }
    }
