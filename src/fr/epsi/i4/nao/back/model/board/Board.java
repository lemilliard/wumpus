package fr.epsi.i4.nao.back.model.board;

import fr.epsi.i4.nao.back.model.board.content.Content;
import fr.epsi.i4.nao.util.Util;

import static fr.epsi.i4.nao.back.model.board.content.Content.*;

/**
 * Created by tkint on 23/11/2017.
 */
public class Board implements IBoard {

    private static Board instance;
    private int width;
    private int height;
    private Case[][] cases;

    private Board(int width, int height) {
        this.width = width;
        this.height = height;
        generate(width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Case[][] getCases() {
        return cases;
    }

    @Override
    public String toString() {
        String str = "";

        for (int y = cases.length - 1; y > -1; y--) {
            str += "\n------------------------\n";
            for (int x = 0; x < cases[y].length; x++) {
                str += "|" + cases[y][x].toString() + "|";
            }
        }

        return str;
    }

    public static Board getInstance(int width, int height) {
        if (instance == null) {
            instance = new Board(width, height);
        }
        return instance;
    }

    public static Board getInstance() {
        return instance;
    }

    @Override
    public Case getCase(int x, int y) {
        return cases[y][x];
    }

    @Override
    public void generate(int width, int height) {
        cases = new Case[height][width];
        for (int y = 0; y < height; y++) {
            cases[y] = new Case[width];
            for (int x = 0; x < width; x++) {
                cases[y][x] = new Case();
            }
        }
        addAgent();
        addPits(0.15d);
    }

    @Override
    public void addCaseContent(int x, int y, Content content) {
        getCase(x, y).addContent(content);
    }

    private void setCaseContent(int x, int y, Content content) {
        getCase(x, y).setContent(content);
    }

    @Override
    public void addPits(double percentage) {
        int count = (int) (percentage * ((double) width * height));
        for (int i = 0; i < count; i++) {
            int[] xy = getRandomCoordinatesForContent(BREEZE);
            addPit(xy[0], xy[1]);
        }
    }

    private int[] getRandomCoordinatesForContent(Content content) {
        int[] xy = new int[2];
        xy[0] = Util.randomInt(0, width - 1);
        xy[1] = Util.randomInt(0, height - 1);
        if (!getCase(xy[0], xy[1]).canContain(content)) {
            return getRandomCoordinatesForContent(content);
        }
        return xy;
    }

    public void addPit(int x, int y) {
        if (x > 0
                && getCase(x - 1, y).canContain(BREEZE)) {
            addCaseContent(x - 1, y, BREEZE);
        }
        if (y > 0
                && getCase(x, y - 1).canContain(BREEZE)) {
            addCaseContent(x, y - 1, BREEZE);
        }
        if (x < width - 1
                && getCase(x + 1, y).canContain(BREEZE)) {
            addCaseContent(x + 1, y, BREEZE);
        }
        if (y < height - 1
                && getCase(x, y + 1).canContain(BREEZE)) {
            addCaseContent(x, y + 1, BREEZE);
        }
        setCaseContent(x, y, PIT);
    }

    @Override
    public void addAgent() {
        addCaseContent(0, 0, AGENT);
    }

    @Override
    public void addWumpus() {

    }

    @Override
    public void addGold() {

    }
}
