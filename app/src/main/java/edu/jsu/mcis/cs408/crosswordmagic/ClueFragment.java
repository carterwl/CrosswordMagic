package edu.jsu.mcis.cs408.crosswordmagic;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.beans.PropertyChangeEvent;

import edu.jsu.mcis.cs408.crosswordmagic.databinding.FragmentClueBinding;
import edu.jsu.mcis.cs408.crosswordmagic.view.AbstractView;

public class ClueFragment extends Fragment implements TabFragment, AbstractView {

    public static final String TAG = "ClueFragment";

    private String title;
    private CrosswordMagicController controller;
    private FragmentClueBinding binding;

    public ClueFragment() {
        this.title = "Clues";
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = ((MainActivity) requireActivity()).getController();
        controller.addView(this);

        binding.aContainer.setMovementMethod(new ScrollingMovementMethod());
        binding.dContainer.setMovementMethod(new ScrollingMovementMethod());

        controller.getCluesAcross();
        controller.getCluesDown();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String property = event.getPropertyName();

        if (property.equals(CrosswordMagicController.CLUES_ACROSS_PROPERTY)) {
            binding.aContainer.setText((String) event.getNewValue());
        }
        else if (property.equals(CrosswordMagicController.CLUES_DOWN_PROPERTY)) {
            binding.dContainer.setText((String) event.getNewValue());
        }
    }
}