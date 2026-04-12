package edu.jsu.mcis.cs408.crosswordmagic;
import java.util.Map;
public class Puzzle {
    private String name;
    private String description;
    private int height;
    private int width;
    public Puzzle(Map<String, String> params) {
        this.name = params.get("name");
        this.description = params.get("description");
        this.height = Integer.parseInt(params.get("height"));
        this.width = Integer.parseInt(params.get("width"));
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
}