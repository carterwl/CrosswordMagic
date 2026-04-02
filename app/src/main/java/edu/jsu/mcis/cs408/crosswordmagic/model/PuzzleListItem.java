package edu.jsu.mcis.cs408.crosswordmagic.model;

public class PuzzleListItem {

    private Integer id;
    private String name;

    public PuzzleListItem(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}