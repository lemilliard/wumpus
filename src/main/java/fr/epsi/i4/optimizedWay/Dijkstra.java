package fr.epsi.i4.optimizedWay;

import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.Case;
import fr.epsi.i4.back.model.board.content.Content;

import java.util.ArrayList;

public class Dijkstra {
    private int boardWidth;
    private int boardHeight;
    private ArrayList<Case> listContent;
    private ArrayList<Edge[]> edges = new ArrayList();

    public Dijkstra(Board board) {
        this.boardWidth = board.getWidth();
        this.boardHeight = board.getHeight();
        this.listContent = board.getCaseContent();

        System.out.println(boardHeight + " - " + boardWidth);
        System.out.println(listContent.size());

        for (int i = 0; i < listContent.size(); i++) {
            if (listContent.get(i).containsContent(Content.PIT)) { // length == Integer.MAX_VALUE
                Edge[] tabEdges = { new Edge(i, i++, Integer.MAX_VALUE) };
                edges.add(tabEdges);
            } else if (listContent.get(i).containsContent(Content.WUMPUS)) { // length == 2
                Edge[] tabEdges = { new Edge(0, 2, 2) };
                edges.add(tabEdges);
            } else if (listContent.get(i).containsContent(Content.WALL)) {
            } else { // pour toutes les autres cases length == 2
                Edge[] tabEdges = { new Edge(0, 2, 1) };
                edges.add(tabEdges);
            }
        }

        Graph g = new Graph(edges);
        g.calculateShortestDistances();
        g.printResult();
    }
}