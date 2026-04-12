package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.Context;

import edu.jsu.mcis.cs408.crosswordmagic.model.CrosswordMagicModel;
import edu.jsu.mcis.cs408.crosswordmagic.view.AbstractView;

public class CrosswordMagicController extends AbstractController {
    public int downloadPuzzle(int webPuzzleId) {
        return model.downloadPuzzle(webPuzzleId);
    }
    public static final String CLUES_ACROSS_PROPERTY = "acrossClues";
    public static final String CLUES_DOWN_PROPERTY = "downClues";
    public static final String GRID_LETTERS_PROPERTY = "gridLetters";
    public static final String GRID_NUMBERS_PROPERTY = "gridNumbers";
    public static final String GRID_DIMENSION_PROPERTY = "gridDimensions";
    public static final String PUZZLE_LIST_PROPERTY = "puzzleList";

    private CrosswordMagicModel model;

    public CrosswordMagicController(Context context) {
        model = new CrosswordMagicModel(context);
    }

    public CrosswordMagicController(Context context, int puzzleid) {
        model = new CrosswordMagicModel(context, puzzleid);
    }

    public void getGridLetters() {
        Character[][] oldValue = null;
        Character[][] newValue = model.getLetters();
        firePropertyChange(GRID_LETTERS_PROPERTY, oldValue, newValue);
    }

    public void getGridNumbers() {
        Integer[][] oldValue = null;
        Integer[][] newValue = model.getNumbers();
        firePropertyChange(GRID_NUMBERS_PROPERTY, oldValue, newValue);
    }

    public void getGridDimensions() {
        Integer[] oldValue = null;
        Integer[] newValue = model.getDimensions();
        firePropertyChange(GRID_DIMENSION_PROPERTY, oldValue, newValue);
    }

    public void getCluesAcross() {
        String oldValue = null;
        String newValue = model.getAcrossClues();
        firePropertyChange(CLUES_ACROSS_PROPERTY, oldValue, newValue);
    }

    public void getCluesDown() {
        String oldValue = null;
        String newValue = model.getDownClues();
        firePropertyChange(CLUES_DOWN_PROPERTY, oldValue, newValue);
    }

    public PuzzleListItem[] getPuzzleList() {
        PuzzleListItem[] oldValue = null;
        PuzzleListItem[] newValue = model.getPuzzleList();
        firePropertyChange(PUZZLE_LIST_PROPERTY, oldValue, newValue);
        return newValue;
    }

    public boolean setGuess(int boxNumber, String guess) {
        boolean result = model.setGuess(boxNumber, guess);

        if (result) {
            Character[][] oldValue = null;
            Character[][] newValue = model.getLetters();
            firePropertyChange(GRID_LETTERS_PROPERTY, oldValue, newValue);
        }

        return result;
    }
}