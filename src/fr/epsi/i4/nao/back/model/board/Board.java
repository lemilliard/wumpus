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

    private Board(int width, int height, int pitsPercentage) {
        this.width = width;
        this.height = height;
        generate(width, height, pitsPercentage);
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

    public static Board getInstance(int width, int height, int pitsPercentage) {
        if (instance == null) {
            instance = new Board(width, height, pitsPercentage);
        }
        return instance;
    }

    public static Board getInstance() {
        return getInstance(4, 4, 15);
    }

    @Override
    public Case getCase(int x, int y) {
        return cases[y][x];
    }

    @Override
    public void generate(int width, int height, int pitsPercentage) {
        cases = new Case[height][width];
        for (int y = 0; y < height; y++) {
            cases[y] = new Case[width];
            for (int x = 0; x < width; x++) {
                cases[y][x] = new Case();
            }
        }
        // Ajout de l'agent
        setCaseContent(AGENT, 0, 0);
        // Ajout des puits
        int count = (int) ((double) pitsPercentage / 100.0d * ((double) width * height));
        for (int i = 0; i < count; i++) {
            addCaseContent(PIT, BREEZE);
        }
        // Ajout du Wumpus
        addCaseContent(WUMPUS, STENCH);
        // Ajout de l'or
        addCaseContent(GOLD);
    }

    @Override
    public void addCaseContent(Content content, int x, int y) {
        getCase(x, y).addContent(content);
    }

    private void addCaseContentAround(int x, int y, Content content) {
        if (x > 0
                && getCase(x - 1, y).canContain(content)) {
            addCaseContent(content, x - 1, y);
        }
        if (y > 0
                && getCase(x, y - 1).canContain(content)) {
            addCaseContent(content, x, y - 1);
        }
        if (x < width - 1
                && getCase(x + 1, y).canContain(content)) {
            addCaseContent(content, x + 1, y);
        }
        if (y < height - 1
                && getCase(x, y + 1).canContain(content)) {
            addCaseContent(content, x, y + 1);
        }
    }

    private void setCaseContent(Content content, int x, int y) {
        getCase(x, y).setContent(content);
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

    private void addCaseContent(Content content) {
        addCaseContent(content, null);
    }

    private void addCaseContent(Content content, Content around) {
        int[] xy = getRandomCoordinatesForContent(content);
        int x = xy[0];
        int y = xy[1];
        addCaseContent(content, x, y);
        if (around != null) {
            addCaseContentAround(x, y, around);
        }
    }
}
