package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

public class PuzzleDAO {


    public int create(SQLiteDatabase db, Puzzle puzzle) {

        ContentValues values = new ContentValues();

        values.put("name", puzzle.getName());
        values.put("description", puzzle.getDescription());
        values.put("height", puzzle.getHeight());
        values.put("width", puzzle.getWidth());

        return (int) db.insert("puzzles", null, values);
    }


    public Puzzle get(SQLiteDatabase db) {

        Cursor cursor = db.rawQuery("SELECT * FROM puzzles LIMIT 1", null);

        Puzzle puzzle = null;

        if (cursor.moveToFirst()) {

            Map<String, String> params = new HashMap<>();

            params.put("name", cursor.getString(cursor.getColumnIndexOrThrow("name")));
            params.put("description", cursor.getString(cursor.getColumnIndexOrThrow("description")));
            params.put("height", String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("height"))));
            params.put("width", String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("width"))));

            puzzle = new Puzzle(params);
        }

        cursor.close();

        return puzzle;
    }
}