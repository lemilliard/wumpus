package fr.epsi.i4.optimizedWay;

import fr.epsi.i4.back.model.Agent;
import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.content.Agent;

public class Dijkstra {

    private final Board board;
    private final fr.epsi.i4.back.model.Agent agent;

    public Dijkstra(Board board) {
        this.board = board;
        this.agent = agent;
    }

    public int comparison() {
        int toursUtilisés;
        fr.epsi.i4.back.model.Agent myAgent = new fr.epsi.i4.back.model.Agent(board);
        fr.epsi.i4.back.model.board.content.Agent myAgent2= new fr.epsi.i4.back.model.board.content.Agent(agent);

        if (myAgent2.getPosition() < myAgent2.){
            myAgent.move(up);
        }else if (myAgent < myAgent){
            move(right);
        }
        return toursUtilisés;
    }
}
