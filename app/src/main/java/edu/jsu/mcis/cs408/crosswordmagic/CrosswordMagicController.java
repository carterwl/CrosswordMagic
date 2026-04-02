package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.Context;

import edu.jsu.mcis.cs408.crosswordmagic.model.PuzzleListItem;

public class CrosswordMagicController extends AbstractController {

    public static final String CLUES_ACROSS_PROPERTY = "acrossClues";
    public static final String CLUES_DOWN_PROPERTY = "downClues";
    public static final String GRID_LETTERS_PROPERTY = "gridLetters";
    public static final String GRID_NUMBERS_PROPERTY = "gridNumbers";
    public static final String GRID_DIMENSION_PROPERTY = "gridDimensions";
    public static final String PUZZLE_LIST_PROPERTY = "puzzleList";

    private CrosswordMagicModel model;
    private Context context;

    public CrosswordMagicController(Context context) {
        this.context = context;
        model = new CrosswordMagicModel(context);
    }

    public CrosswordMagicController(Context context, int puzzleid) {
        this.context = context;
        model = new CrosswordMagicModel(context, puzzleid);
    }

    public boolean setGuess(int boxNumber, String guess) {
        boolean correct = model.setGuess(boxNumber, guess);

        if (correct) {
            setModelProperty(GRID_LETTERS_PROPERTY, model.getLetters());
        }

        return correct;
    }

    public void getGridLetters() {
        setModelProperty(GRID_LETTERS_PROPERTY, model.getLetters());
    }

    public void getGridNumbers() {
        setModelProperty(GRID_NUMBERS_PROPERTY, model.getNumbers());
    }

    public void getGridDimensions() {
        setModelProperty(GRID_DIMENSION_PROPERTY, model.getDimensions());
    }

    public void getCluesAcross() {
        setModelProperty(CLUES_ACROSS_PROPERTY, model.getAcrossClues());
    }

    public void getCluesDown() {
        setModelProperty(CLUES_DOWN_PROPERTY, model.getDownClues());
    }

    public void getPuzzleList() {
        PuzzleListItem[] list = model.getPuzzleList(context);
        setModelProperty(PUZZLE_LIST_PROPERTY, list);
    }
}