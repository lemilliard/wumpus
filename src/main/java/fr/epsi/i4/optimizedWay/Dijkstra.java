package fr.epsi.i4.optimizedWay;

import com.sun.org.apache.xpath.internal.SourceTree;
import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.content.Content;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;

public class Dijkstra {

    private int boardWidth;
    private int boardHeight;
    private ArrayList pit;
    private ArrayList wumpus;

    public Dijkstra(Board board) {
        this.boardWidth = board.getWidth();
        this.boardHeight = board.getHeight();
        this.pit = board.getCaseContent(Content.PIT);
        this.wumpus = board.getCaseContent(Content.WUMPUS);

        System.out.println(boardHeight + " - " + boardWidth);

        for (int i = 0; i < pit.size(); i++) {
            System.out.println(pit.get(i));
        }

        for (int i = 0; i < wumpus.size(); i++) {
            System.out.println(wumpus.get(i));
        }
    }
}