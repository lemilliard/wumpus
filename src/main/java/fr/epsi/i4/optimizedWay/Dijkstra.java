package fr.epsi.i4.optimizedWay;

import fr.epsi.i4.back.model.Agent;
import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.content.Content;

public class Dijkstra {

    private final Board board;
    private final Agent agent;

    public Dijkstra(Board board) {
        this.board = board;
        this.agent = new Agent(board);
    }

    public int comparison() {
        int toursUtilises = 0;

        Agent myAgent = new Agent(board);

        int[] posWumpus = board.getCaseContent(Content.WUMPUS);
        int[] posWumpusX = new int[0];
        int[] posWumpusY = new int[0];

        for (int i = 0; i < posWumpus.length / 2; i++) {
            posWumpusX[i] += posWumpus[i];
        }

        for (int i = posWumpus.length / 2; i < posWumpus.length; i++) {
            posWumpusY[i] += posWumpus[i];
        }

//
//        if (posWumpus = ){
//
//        }else if (myAgent.getY() < myGold.getGoldY()){
//            myAgent.move(Direction.UP);
//            toursUtilises += 1;
//        }else if (myAgent.getX() < myGold.getGoldX()){
//            myAgent.move(Direction.RIGHT);
//            toursUtilises += 1;
//        }
//        return toursUtilises;
        return 0;
    }
}
