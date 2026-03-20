package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class PuzzleDAO {

    public int create(SQLiteDatabase db, Puzzle puzzle) {
        ContentValues values = new ContentValues();

        values.put("name", puzzle.getName());
        values.put("description", puzzle.getDescription());
        values.put("height", puzzle.getHeight());
        values.put("width", puzzle.getWidth());

        return (int) db.insert("puzzles", null, values);
    }
}