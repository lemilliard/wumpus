package fr.epsi.i4.nao.back.model.board;

import fr.epsi.i4.nao.back.model.board.content.Agent;

/**
 * Created by tkint on 23/11/2017.
 */
public class Board implements IBoard {

    private int width;
    private int height;
    private Case[][] cases;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        generate(width, height);
    }

    @Override
    public String toString() {
        String str = "";

        for (int y = cases.length - 1; y > -1; y--) {
            str += "\n------------------------\n";
            for (int x = 0; x < cases[y].length; x++) {
                str += "|" + cases[y][x].getContentAsString() + "|";
            }
        }

        return str;
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
        addPuits(0.5d);
    }

    @Override
    public void addPuits(double percentage) {
        int count = (int) (percentage * ((double) width * height));
        for (int i = 0; i < count; i++) {

        }
    }

    @Override
    public void addAgent() {
        cases[0][0].addContent(new Agent());
    }

    @Override
    public void addWumpus() {

    }

    @Override
    public void addGold() {

    }

    @Override
    public void addWinds() {

    }

    @Override
    public void addSmell() {

    }
}
