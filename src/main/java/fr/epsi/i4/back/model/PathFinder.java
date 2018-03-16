package fr.epsi.i4.back.model;

import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.Case;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Kint
 */
public class PathFinder {

    private Stack<Case> path;

    private Stack<Case> pathFinding;

    private List<Case> cases;

    private Board board;

    public PathFinder(Board board, Case agent) {
        this.path = new Stack();
        this.pathFinding = new Stack();
        this.cases = new ArrayList<>();
        this.board = board;
        this.pathFinding.push(agent);
    }

    public PathFinder(Stack path, Stack pathFinding, List<Case> cases, Board board, Case agent) {
        this.path = path;
        this.pathFinding = pathFinding;
        this.cases = cases;
        this.board = board;
        this.pathFinding.push(agent);
    }

    public boolean visitedPath(Case c) {
        return cases.contains(c);
    }
    
    public Case notVisitedCase(){
        for(Case caseAround : board.getCasesAround(pathFinding.getLastIn())){
            if(!visitedPath(caseAround)){
                return caseAround;
            }
        }
        return null;
    }

    public boolean caseAroudNotVisited(Case c) {
        boolean result = false;
        List<Case> casesAround = board.getCasesAround(c);
        for (Case caseAround : casesAround) {
            if (caseAround.getWeight().getWeight() < 1 && caseAround.getWeight().getWeight() > -4) {
                result = true;
            }
        }
        return result;
    }

    public boolean push(Case c) {
        boolean result = false;
        if (!cases.contains(c)) {
            pathFinding.push(c);
            cases.add(c);
            result = true;
        }
        return result;
    }

    public void findPath(){
        Case lastIn = null;
        Case notVisited = null;
        while(pathFinding.size() > 0){
            lastIn = pathFinding.getLastIn();
            if(caseAroudNotVisited(lastIn)){
                if(path.size() > pathFinding.size()){
                    path = pathFinding;
                    pathFinding.pop();
                }
            } else {
                notVisited = notVisitedCase();
                if(notVisited != null){
                    pathFinding.push(notVisited);
                } else {
                    pathFinding.pop();
                }
            }
        }
    }
    
}
