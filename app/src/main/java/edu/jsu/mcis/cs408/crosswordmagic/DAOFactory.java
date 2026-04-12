package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DAOFactory extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crossword.db";
    private static final int DATABASE_VERSION = 5;

    private Context context;
    private WordDAO wordDAO;
    private PuzzleDAO puzzleDAO;
    private GuessDAO guessDAO;

    public DAOFactory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        wordDAO = new WordDAO();
        puzzleDAO = new PuzzleDAO();
        guessDAO = new GuessDAO();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE puzzles (" +
                        "rowid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT, " +
                        "description TEXT, " +
                        "height INTEGER, " +
                        "width INTEGER" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE words (" +
                        "puzzleId INTEGER, " +
                        "rowNum INTEGER, " +
                        "colNum INTEGER, " +
                        "box INTEGER, " +
                        "direction INTEGER, " +
                        "word TEXT, " +
                        "clue TEXT" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE guesses (" +
                        "puzzleId INTEGER, " +
                        "box INTEGER, " +
                        "guess TEXT" +
                        ")"
        );

        loadPuzzle(db, "Crossword Magic");
        loadPuzzle(db, "NY Times (Mon, Feb 24, 2025)");
        loadPuzzle(db, "NY Times (Tue, Feb 25, 2025)");
        loadPuzzle(db, "NY Times (Wed, Feb 26, 2025)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS guesses");
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

    public GuessDAO getGuessDAO() {
        return guessDAO;
    }

    private void loadPuzzle(SQLiteDatabase db, String puzzleName) {

        try {
            Map<String, String> params = new HashMap<>();
            params.put("name", puzzleName);
            params.put("description", "Sample crossword puzzle");
            params.put("height", "0");
            params.put("width", "0");

            Puzzle puzzle = new Puzzle(params);
            int puzzleId = puzzleDAO.create(db, puzzle);

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

                String[] parts = line.trim().split("\\s+");

                Word word = new Word(
                        puzzleId,
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}