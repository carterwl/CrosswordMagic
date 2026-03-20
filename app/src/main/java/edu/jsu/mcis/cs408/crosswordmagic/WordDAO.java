package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WordDAO {

    public void create(SQLiteDatabase db, Word word) {
        ContentValues values = new ContentValues();

        values.put("rowNum", word.getRow());
        values.put("colNum", word.getCol());
        values.put("box", word.getBox());
        values.put("direction", word.getDirection());
        values.put("word", word.getWord());
        values.put("clue", word.getClue());

        db.insert("words", null, values);
    }

    public int countWords(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM words", null);
        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }
}