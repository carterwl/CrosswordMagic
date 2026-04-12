package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

public class GuessDAO {

    public void save(SQLiteDatabase db, int puzzleId, int box, String guess) {

        ContentValues values = new ContentValues();
        values.put("puzzleId", puzzleId);
        values.put("box", box);
        values.put("guess", guess);

        db.delete("guesses", "puzzleId = ? AND box = ?",
                new String[]{ String.valueOf(puzzleId), String.valueOf(box) });

        db.insert("guesses", null, values);
    }

    public Map<Integer, String> getAll(SQLiteDatabase db, int puzzleId) {

        Map<Integer, String> guesses = new HashMap<>();

        Cursor cursor = db.rawQuery(
                "SELECT box, guess FROM guesses WHERE puzzleId = ?",
                new String[]{ String.valueOf(puzzleId) }
        );

        while (cursor.moveToNext()) {
            int box = cursor.getInt(cursor.getColumnIndexOrThrow("box"));
            String guess = cursor.getString(cursor.getColumnIndexOrThrow("guess"));
            guesses.put(box, guess);
        }

        cursor.close();

        return guesses;
    }
}