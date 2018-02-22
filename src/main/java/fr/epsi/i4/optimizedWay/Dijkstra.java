package fr.epsi.i4.optimizedWay;

import fr.epsi.i4.back.model.Agent;
import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.Direction;
import fr.epsi.i4.back.model.board.content.Gold;

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
        Gold myGold = new Gold(board);

        if (myAgent.getY() < myGold.getGoldY()){
            myAgent.move(Direction.UP);
        }else if (myAgent.getX() < myGold.getGoldX()){
            myAgent.move(Direction.RIGHT);
        }
        return toursUtilises;
    }
}
