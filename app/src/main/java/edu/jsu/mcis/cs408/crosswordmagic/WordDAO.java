package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class WordDAO {

    public void create(SQLiteDatabase db, Word word) {

        ContentValues values = new ContentValues();

        values.put("puzzleId", word.getPuzzleId());
        values.put("rowNum", word.getRow());
        values.put("colNum", word.getColumn());
        values.put("box", word.getBox());
        values.put("direction", word.getDirection());
        values.put("word", word.getWord());
        values.put("clue", word.getClue());

        db.insert("words", null, values);
    }
}