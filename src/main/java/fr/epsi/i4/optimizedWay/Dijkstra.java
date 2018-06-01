package fr.epsi.i4.optimizedWay;

import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.Case;
import fr.epsi.i4.back.model.board.Direction;
import fr.epsi.i4.back.model.board.content.Content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dijkstra {
    private int boardWidth;
    private int boardHeight;
    private Case[][] tabContent;
    private ArrayList<Edge> edges = new ArrayList();
    private int idGold;
    private int idWumpus;

    public Dijkstra(Board board) {
        this.boardWidth = board.getWidth();
        this.boardHeight = board.getHeight();
        this.tabContent = board.getCases();
        this.idGold = 0;
        this.idWumpus = 0;

        System.out.println(boardHeight + " - " + boardWidth);
        System.out.println(tabContent.length);

        for (int i = 1; i < tabContent.length - 1; i++) {
            for (int j = 1; j < tabContent.length - 1; j++) {
                Case cases = tabContent[i][j];
                int id = cases.getId();
                HashMap<Direction, Case> caseAround = board.getCasesAround(cases.getX(), cases.getY());
                for (Map.Entry<Direction, Case> aroundEntry : caseAround.entrySet()) {
                    Case value = aroundEntry.getValue();
                    if (value.containsContent(Content.PIT)) {
                        edges.add(new Edge(id, value.getId(), 1000));
                        System.out.println("PIT : " + value.getId());
                    } else if (value.containsContent(Content.WUMPUS) && idWumpus != value.getId()) {
                        edges.add(new Edge(id, value.getId(), 101));
                        System.out.println("WUMPUS : " + value.getId());
                        idWumpus = value.getId();
                    } else if (value.containsContent(Content.WALL)) {
                    } else if (value.containsContent(Content.GOLD) && idGold != value.getId()) {
                        edges.add(new Edge(id, value.getId(), -1));
                        System.out.println("GOLD : " + value.getId());
                        idGold = value.getId();
                    } else {
                        edges.add(new Edge(id, value.getId(), 100));
                    }
                }
            }
        }
        Graph g = new Graph(edges);
        g.calculateShortestDistances();
        g.printResult(idGold, tabContent);
    }
}