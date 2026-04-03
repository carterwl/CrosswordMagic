package edu.jsu.mcis.cs408.crosswordmagic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import edu.jsu.mcis.cs408.crosswordmagic.model.PuzzleListItem;
public class CrosswordMagicModel {
    private Character[][] letters;
    private Integer[][] numbers;
    private Integer[] dimensions;
    private String acrossClues, downClues;
    private List<Word> words;
    public CrosswordMagicModel(Context context) { this(context, 0); }
    public CrosswordMagicModel(Context context, int puzzleid) {
        DAOFactory dao = new DAOFactory(context);
        SQLiteDatabase db = dao.getReadableDatabase();
        if (puzzleid > 0) dao.getPuzzleDAO().get(db, puzzleid);
        else dao.getPuzzleDAO().get(db);
        words = dao.getWordDAO().getAll(db);
        int maxRow = 0, maxCol = 0;
        for (Word w : words) {
            maxRow = Math.max(maxRow, w.getRow());
            maxCol = Math.max(maxCol, w.getCol());
        }
        letters = new Character[maxRow + 1][maxCol + 1];
        numbers = new Integer[maxRow + 1][maxCol + 1];
        for (int r = 0; r <= maxRow; r++)
            for (int c = 0; c <= maxCol; c++) {
                letters[r][c] = '*';
                numbers[r][c] = 0;
            }
        List<String> across = new ArrayList<>(), down = new ArrayList<>();
        for (Word w : words) {
            int r = w.getRow(), c = w.getCol();
            String word = w.getWord();
            numbers[r][c] = w.getBox();
            if (w.getDirection() == 0) {
                for (int i = 0; i < word.length(); i++) letters[r][c + i] = ' ';
                across.add(w.getBox() + ": " + w.getClue());
            } else {
                for (int i = 0; i < word.length(); i++) letters[r + i][c] = ' ';
                down.add(w.getBox() + ": " + w.getClue());
            }
        }
        dimensions = new Integer[]{letters.length, letters[0].length};

        acrossClues = "";
        for (String s : across) acrossClues += s + "\n";

        downClues = "";
        for (String s : down) downClues += s + "\n";
        db.close();
    }
    public Character[][] getLetters() { return letters; }
    public Integer[][] getNumbers() { return numbers; }
    public Integer[] getDimensions() { return dimensions; }
    public String getAcrossClues() { return acrossClues; }
    public String getDownClues() { return downClues; }

    public PuzzleListItem[] getPuzzleList(Context context) {
        DAOFactory dao = new DAOFactory(context);
        SQLiteDatabase db = dao.getReadableDatabase();
        PuzzleListItem[] list = dao.getPuzzleDAO().list(db);
        db.close();
        return list;
    }
    public boolean setGuess(int boxNumber, String guess) {
        if (guess == null || guess.trim().isEmpty()) return false;
        String g = guess.trim().toUpperCase();
        for (Word w : words) {
            if (w.getBox() == boxNumber) {

                String answer = w.getWord().toUpperCase();
                if (!g.equals(answer)) return false;
                int r = w.getRow(), c = w.getCol();
                if (w.getDirection() == 0)
                    for (int i = 0; i < answer.length(); i++) letters[r][c + i] = answer.charAt(i);
                else
                    for (int i = 0; i < answer.length(); i++) letters[r + i][c] = answer.charAt(i);
                return true;
            }
        }
        return false;
    }
}