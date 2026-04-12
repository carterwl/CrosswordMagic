package edu.jsu.mcis.cs408.crosswordmagic;

public class Word {

    private int puzzleId;
    private int rowNum;
    private int colNum;
    private int box;
    private int direction;
    private String word;
    private String clue;

    public Word(int puzzleId, int rowNum, int colNum, int box, int direction, String word, String clue) {
        this.puzzleId = puzzleId;
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.box = box;
        this.direction = direction;
        this.word = word;
        this.clue = clue;
    }

    public Word(int rowNum, int colNum, int box, int direction, String word, String clue) {
        this(1, rowNum, colNum, box, direction, word, clue);
    }

    public int getPuzzleId() {
        return puzzleId;
    }

    public int getRow() {
        return rowNum;
    }

    public int getCol() {
        return colNum;
    }

    public int getBox() {
        return box;
    }

    public int getDirection() {
        return direction;
    }

    public String getWord() {
        return word;
    }

    public String getClue() {
        return clue;
    }
}