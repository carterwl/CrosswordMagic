package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DAOFactory extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crossword.db";
    private static final int DATABASE_VERSION = 1;

    private WordDAO wordDAO;
    private PuzzleDAO puzzleDAO;
    private Context context;

    public DAOFactory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

        loadPuzzle(db);
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

    private void loadPuzzle(SQLiteDatabase db) {

        try {

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            context.getResources().openRawResource(R.raw.puzzle)
                    )
            );

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.trim().split("\\s+");// Break the lines into parts //

                Word word = new Word(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        parts[4],
                        parts[5]
                );

                wordDAO.create(db, word);
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}