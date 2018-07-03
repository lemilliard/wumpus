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
    private int xGold;
    private int yGold;
    private int idWumpus;
    private boolean[][][][] proxi;

    public Dijkstra(Board board) {
        this.boardWidth = board.getWidth();
        this.boardHeight = board.getHeight();
        this.tabContent = board.getCases();
        this.idGold = 0;
        this.idWumpus = 0;
        this.proxi = new boolean[25][25][25][25];

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
                        proxi[value.getX()][value.getY()][value.getX()][value.getY()] = false;
                    } else if (value.containsContent(Content.WUMPUS) && idWumpus != value.getId()) {
                        edges.add(new Edge(id, value.getId(), 11));
                        System.out.println("WUMPUS : " + value.getId());
                        idWumpus = value.getId();
                        proxi[value.getX()][value.getY()][value.getX()][value.getY()] = true;
                    } else if (value.containsContent(Content.WALL)) {
                        proxi[value.getX()][value.getY()][value.getX()][value.getY()] = false;
                    } else if (value.containsContent(Content.GOLD) && idGold != value.getId()) {
                        edges.add(new Edge(id, value.getId(), -1));
                        System.out.println("GOLD : " + value.getId());
                        xGold = value.getX();
                        yGold = value.getY();
                        idGold = value.getId();
                        proxi[value.getX()][value.getY()][value.getX()][value.getY()] = true;
                    } else {
                        edges.add(new Edge(id, value.getId(), 10));
                        proxi[value.getX()][value.getY()][value.getX()][value.getY()] = true;
                    }
                }
            }
        }
        Graph g = new Graph(edges);
        g.calculateShortestDistances();
        g.printResult(idGold, board, xGold, yGold, tabContent);

//        ShortestPath shortestPath = new ShortestPath(boardHeight - 2, boardWidth - 2,1, 1, xGold, yGold, proxi);
//        shortestPath.djikstra();
//        shortestPath.path();
//        for (int i = 0; i < shortestPath.path.size() - 1; i++) {
//            for (int j = 0; j < shortestPath.path.size() - 1; j++) {
//                board.getCase(i, j).addContent(Content.DIJKSTRA);
//            }
//        }
    }
}