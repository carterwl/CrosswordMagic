package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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


    public List<Word> getAll(SQLiteDatabase db) {

        List<Word> words = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM words", null);

        while (cursor.moveToNext()) {

            Word word = new Word(
                    cursor.getInt(cursor.getColumnIndexOrThrow("rowNum")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("colNum")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("box")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("direction")),
                    cursor.getString(cursor.getColumnIndexOrThrow("word")),
                    cursor.getString(cursor.getColumnIndexOrThrow("clue"))
            );

            words.add(word);
        }

        cursor.close();

        return words;
    }
}