package edu.jsu.mcis.cs408.crosswordmagic.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.jsu.mcis.cs408.crosswordmagic.DAOFactory;
import edu.jsu.mcis.cs408.crosswordmagic.Puzzle;
import edu.jsu.mcis.cs408.crosswordmagic.PuzzleListItem;
import edu.jsu.mcis.cs408.crosswordmagic.WebServiceDAO;
import edu.jsu.mcis.cs408.crosswordmagic.Word;

public class CrosswordMagicModel {

    private Context context;
    private int puzzleId;

    private Character[][] letters;
    private Integer[][] numbers;
    private Integer[] dimensions;

    private String acrossClues;
    private String downClues;

    private List<Word> words;

    public CrosswordMagicModel(Context context) {
        this(context, 0);
    }

    public CrosswordMagicModel(Context context, int puzzleId) {

        this.context = context;
        this.puzzleId = (puzzleId > 0) ? puzzleId : 1;

        DAOFactory daoFactory = new DAOFactory(context);
        SQLiteDatabase db = daoFactory.getReadableDatabase();

        words = daoFactory.getWordDAO().getAll(db, this.puzzleId);

        if (words == null) {
            words = new ArrayList<>();
        }

        if (words.isEmpty()) {
            letters = new Character[1][1];
            numbers = new Integer[1][1];
            letters[0][0] = ' ';
            numbers[0][0] = 0;
            dimensions = new Integer[] { 1, 1 };
            acrossClues = "";
            downClues = "";
            db.close();
            return;
        }

        int maxRow = 0;
        int maxCol = 0;

        for (Word word : words) {

            int row = word.getRow();
            int col = word.getCol();
            String answer = word.getWord();

            if (word.getDirection() == 0) {
                maxRow = Math.max(maxRow, row);
                maxCol = Math.max(maxCol, col + answer.length() - 1);
            }
            else {
                maxRow = Math.max(maxRow, row + answer.length() - 1);
                maxCol = Math.max(maxCol, col);
            }
        }

        letters = new Character[maxRow + 1][maxCol + 1];
        numbers = new Integer[maxRow + 1][maxCol + 1];

        for (int row = 0; row < letters.length; row++) {
            for (int col = 0; col < letters[row].length; col++) {
                letters[row][col] = ' ';
                numbers[row][col] = 0;
            }
        }

        List<String> across = new ArrayList<>();
        List<String> down = new ArrayList<>();

        for (Word word : words) {

            int row = word.getRow();
            int col = word.getCol();
            String answer = word.getWord();

            numbers[row][col] = word.getBox();

            if (word.getDirection() == 0) {
                for (int i = 0; i < answer.length(); i++) {
                    letters[row][col + i] = '_';
                }
                across.add(word.getBox() + ": " + word.getClue());
            }
            else {
                for (int i = 0; i < answer.length(); i++) {
                    letters[row + i][col] = '_';
                }
                down.add(word.getBox() + ": " + word.getClue());
            }
        }

        dimensions = new Integer[] { letters.length, letters[0].length };

        acrossClues = "";
        for (String clue : across) {
            acrossClues += clue + "\n";
        }

        downClues = "";
        for (String clue : down) {
            downClues += clue + "\n";
        }

        restoreSavedGuesses(db, daoFactory);

        db.close();
    }

    public Character[][] getLetters() {
        return letters;
    }

    public Integer[][] getNumbers() {
        return numbers;
    }

    public Integer[] getDimensions() {
        return dimensions;
    }

    public String getAcrossClues() {
        return acrossClues;
    }

    public String getDownClues() {
        return downClues;
    }

    public PuzzleListItem[] getPuzzleList() {
        DAOFactory daoFactory = new DAOFactory(context);
        SQLiteDatabase db = daoFactory.getReadableDatabase();
        PuzzleListItem[] puzzles = daoFactory.getPuzzleDAO().list(db);
        db.close();
        return puzzles;
    }

    public boolean setGuess(int boxNumber, String guess) {

        if (guess == null || guess.trim().isEmpty()) {
            return false;
        }

        String userGuess = guess.trim().toUpperCase();

        for (Word word : words) {

            if (word.getBox() == boxNumber) {

                String answer = word.getWord().toUpperCase();

                if (!userGuess.equals(answer)) {
                    continue;
                }

                fillWord(word, answer);
                saveGuess(boxNumber, answer);

                return true;
            }
        }

        return false;
    }

    private void fillWord(Word word, String answer) {

        int row = word.getRow();
        int col = word.getCol();

        if (word.getDirection() == 0) {
            for (int i = 0; i < answer.length(); i++) {
                letters[row][col + i] = answer.charAt(i);
            }
        }
        else {
            for (int i = 0; i < answer.length(); i++) {
                letters[row + i][col] = answer.charAt(i);
            }
        }
    }

    private void saveGuess(int boxNumber, String guess) {
        DAOFactory daoFactory = new DAOFactory(context);
        SQLiteDatabase db = daoFactory.getWritableDatabase();
        daoFactory.getGuessDAO().save(db, puzzleId, boxNumber, guess);
        db.close();
    }

    private void restoreSavedGuesses(SQLiteDatabase db, DAOFactory daoFactory) {

        Map<Integer, String> guesses = daoFactory.getGuessDAO().getAll(db, puzzleId);

        for (Word word : words) {
            String savedGuess = guesses.get(word.getBox());

            if (savedGuess != null && savedGuess.equalsIgnoreCase(word.getWord())) {
                fillWord(word, savedGuess.toUpperCase());
            }
        }
    }

    public int downloadPuzzle(int webPuzzleId) {

        int newPuzzleId = 0;
        SQLiteDatabase db = null;

        try {
            WebServiceDAO webServiceDAO = new WebServiceDAO();
            String response = webServiceDAO.get(webPuzzleId);

            if (response == null || response.trim().isEmpty()) {
                return 0;
            }

            JSONObject puzzleData = new JSONObject(response);
            JSONArray wordsArray = null;

            if (puzzleData.has("words")) {
                wordsArray = puzzleData.getJSONArray("words");
            }
            else if (puzzleData.has("puzzle")) {
                Object nested = puzzleData.get("puzzle");

                if (nested instanceof JSONArray) {
                    wordsArray = (JSONArray) nested;
                }
                else if (nested instanceof JSONObject) {
                    JSONObject nestedPuzzle = (JSONObject) nested;

                    if (nestedPuzzle.has("words")) {
                        wordsArray = nestedPuzzle.getJSONArray("words");
                    }
                }
            }

            if (wordsArray == null || wordsArray.length() == 0) {
                return 0;
            }

            DAOFactory daoFactory = new DAOFactory(context);
            db = daoFactory.getWritableDatabase();

            Map<String, String> params = new HashMap<>();
            params.put("name", puzzleData.optString("name", "Downloaded Puzzle"));
            params.put("description", puzzleData.optString("description", "Downloaded puzzle"));
            params.put("height", String.valueOf(puzzleData.optInt("height", 0)));
            params.put("width", String.valueOf(puzzleData.optInt("width", 0)));

            Puzzle puzzle = new Puzzle(params);
            newPuzzleId = daoFactory.getPuzzleDAO().create(db, puzzle);

            for (int i = 0; i < wordsArray.length(); i++) {
                JSONObject wordData = wordsArray.getJSONObject(i);

                Word word = new Word(
                        newPuzzleId,
                        wordData.optInt("row", 0),
                        wordData.optInt("column", 0),
                        wordData.optInt("box", 0),
                        wordData.optInt("direction", 0),
                        wordData.optString("word", ""),
                        wordData.optString("clue", "")
                );

                daoFactory.getWordDAO().create(db, word);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (db != null) {
                db.close();
            }
        }

        return newPuzzleId;
    }
}