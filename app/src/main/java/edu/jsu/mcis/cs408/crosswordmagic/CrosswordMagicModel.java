package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import edu.jsu.mcis.cs408.crosswordmagic.model.PuzzleListItem;

public class CrosswordMagicModel {

    private PropertyChangeSupport pcs;

    private Character[][] letters;
    private Integer[][] numbers;
    private Integer[] dimensions;

    private String acrossClues;
    private String downClues;

    private List<Word> words;

    public CrosswordMagicModel(Context context) {
        this(context, 0);
    }

    public CrosswordMagicModel(Context context, int puzzleid) {

        pcs = new PropertyChangeSupport(this);

        DAOFactory daoFactory = new DAOFactory(context);
        SQLiteDatabase db = daoFactory.getReadableDatabase();

        PuzzleDAO puzzleDAO = daoFactory.getPuzzleDAO();
        WordDAO wordDAO = daoFactory.getWordDAO();

        if (puzzleid > 0) {
            puzzleDAO.get(db, puzzleid);
        } else {
            puzzleDAO.get(db);
        }

        words = wordDAO.getAll(db);

        int maxRow = 0;
        int maxCol = 0;

        for (Word w : words) {
            maxRow = Math.max(maxRow, w.getRow());
            maxCol = Math.max(maxCol, w.getCol());
        }

        letters = new Character[maxRow + 1][maxCol + 1];
        numbers = new Integer[maxRow + 1][maxCol + 1];

        for (int y = 0; y <= maxRow; y++) {
            for (int x = 0; x <= maxCol; x++) {
                letters[y][x] = '*';
                numbers[y][x] = 0;
            }
        }

        List<String> acrossList = new ArrayList<>();
        List<String> downList = new ArrayList<>();

        for (Word w : words) {

            int row = w.getRow();
            int col = w.getCol();
            String word = w.getWord();

            numbers[row][col] = w.getBox();

            if (w.getDirection() == 0) {
                for (int i = 0; i < word.length(); i++) {
                    letters[row][col + i] = ' ';
                }
                acrossList.add(w.getBox() + ": " + w.getClue());
            } else {
                for (int i = 0; i < word.length(); i++) {
                    letters[row + i][col] = ' ';
                }
                downList.add(w.getBox() + ": " + w.getClue());
            }
        }

        dimensions = new Integer[]{letters.length, letters[0].length};

        StringBuilder acrossBuilder = new StringBuilder();
        for (String clue : acrossList) {
            acrossBuilder.append(clue).append("\n");
        }
        acrossClues = acrossBuilder.toString();

        StringBuilder downBuilder = new StringBuilder();
        for (String clue : downList) {
            downBuilder.append(clue).append("\n");
        }
        downClues = downBuilder.toString();

        db.close();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
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

    public PuzzleListItem[] getPuzzleList(Context context) {

        DAOFactory daoFactory = new DAOFactory(context);
        SQLiteDatabase db = daoFactory.getReadableDatabase();

        PuzzleDAO puzzleDAO = daoFactory.getPuzzleDAO();
        PuzzleListItem[] puzzleList = puzzleDAO.list(db);

        db.close();

        return puzzleList;
    }

    public boolean setGuess(int boxNumber, String guess) {

        if (guess == null || guess.trim().isEmpty()) {
            return false;
        }

        String cleanGuess = guess.trim().toUpperCase();

        for (Word w : words) {

            if (w.getBox() == boxNumber) {

                String answer = w.getWord().toUpperCase();

                if (cleanGuess.equals(answer)) {

                    int row = w.getRow();
                    int col = w.getCol();

                    if (w.getDirection() == 0) {
                        for (int i = 0; i < answer.length(); i++) {
                            letters[row][col + i] = answer.charAt(i);
                        }
                    } else {
                        for (int i = 0; i < answer.length(); i++) {
                            letters[row + i][col] = answer.charAt(i);
                        }
                    }

                    return true;
                }

                return false;
            }
        }

        return false;
    }
}