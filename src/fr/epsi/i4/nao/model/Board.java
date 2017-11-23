package fr.epsi.i4.nao.model;

import java.util.Arrays;

/**
 * Created by tkint on 23/11/2017.
 */
public class Board {

    private Case[][] cases;

    public Board(int width, int height) {
        cases = new Case[height][width];
        for (int y = 0; y < height; y++) {
            cases[y] = new Case[width];
            for (int x = 0; x < width; x++) {
                cases[y][x] = new Case();
            }
        }

        cases[0][0].setContent("P", 0);
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < cases.length; i++) {
            str += "\n------------------------\n";
            for (int j = 0; j < cases[i].length; j++) {
                str += "|" + cases[i][j].getContentAsString() + "|";
            }
        }

        return str;
    }

    public void movePlayer(int direction) {

    }
}
