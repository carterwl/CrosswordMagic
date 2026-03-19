package edu.jsu.mcis.cs408.crosswordmagic;

import java.util.Map;

public class Word {

    private int puzzleId;
    private int row;
    private int column;
    private int box;
    private String direction;
    private String word;
    private String clue;

    public Word(Map<String, String> params) {
        this.puzzleId = Integer.parseInt(params.get("puzzleId"));
        this.row = Integer.parseInt(params.get("rowNum"));
        this.column = Integer.parseInt(params.get("colNum"));
        this.box = Integer.parseInt(params.get("box"));
        this.direction = params.get("direction");
        this.word = params.get("word");
        this.clue = params.get("clue");
    }

    public int getPuzzleId() { return puzzleId; }
    public int getRow() { return row; }
    public int getColumn() { return column; }
    public int getBox() { return box; }
    public String getDirection() { return direction; }
    public String getWord() { return word; }
    public String getClue() { return clue; }
}