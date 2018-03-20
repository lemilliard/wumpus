package fr.epsi.i4.optimizedWay;

import fr.epsi.i4.back.model.Agent;
import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.Case;
import fr.epsi.i4.back.model.board.content.Content;
import fr.epsi.i4.back.model.board.content.Wumpus;

import java.util.ArrayList;

public class Dijkstra {

    private final Board board;
    private final Agent agent;
    int toursUtilises = 0;
    private int[] posPit;

    public Dijkstra(Board board) {
        this.board = board;
        this.agent = new Agent(board);
    }


    //a finir
    private Case posWumpus(Board board) {

        ArrayList<Case> posWumpus = board.getCaseContent(Content.WUMPUS);

        for (int i = 0; i < posWumpus.size(); i++) {
            Case caseWumpus += caseWumpus;
        }
        return ;
    }

//a changer
    private int[] posPit(Board board)
    {
        int[] posPit = board.getCaseContent(Content.PIT);
        int[] posPitX = new int[0];
        int[] posPitY = new int[0];

        for (int i = 0; i < posPit.length / 2; i++) {
            posPitX[i] += posPit[i];
        }

        for (int i = posPit.length / 2; i < posPit.length; i++) {
            posPitY[i] += posPit[i];
        }

        for (int i = 0; i < posPitX.length; i++) {
            return board.getCase(posPitX[i], posPitY[i]);
        }

    }
// a refaire
    public int comparison() {
        if (posAgentX == posWumpusX && posAgentY == posWumpusY)
        {
            toursUtilises += 1;
        }

        return toursUtilises +=1;
    }
}
