package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DAOFactory extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crossword.db";
    private static final int DATABASE_VERSION = 1;

    private WordDAO wordDAO;
    private PuzzleDAO puzzleDAO;

    public DAOFactory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        wordDAO = new WordDAO();
        puzzleDAO = new PuzzleDAO();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE puzzles (" +
                        "name TEXT, " +
                        "description TEXT, " +
                        "height INTEGER, " +
                        "width INTEGER)"
        );

        db.execSQL(
                "CREATE TABLE words (" +
                        "rowNum INTEGER, " +
                        "colNum INTEGER, " +
                        "box INTEGER, " +
                        "direction INTEGER, " +
                        "word TEXT, " +
                        "clue TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS words");
        db.execSQL("DROP TABLE IF EXISTS puzzles");
        onCreate(db);
    }

    public WordDAO getWordDAO() {
        return wordDAO;
    }

    public PuzzleDAO getPuzzleDAO() {
        return puzzleDAO;
    }
}
