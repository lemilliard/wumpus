package fr.epsi.i4.back.model;

import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.Case;
import fr.epsi.i4.back.model.board.Direction;
import fr.epsi.i4.back.model.board.content.Weight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Kint
 */
public class PathFinder {

	private Stack<Case> path;

	private Stack<Case> pathFinding;

	private List<Case> cases;

	private Board board;

	public PathFinder(Board board) {
		this.path = new Stack<>();
		this.pathFinding = new Stack<>();
		this.cases = new ArrayList<>();
		this.board = board;
	}

	public boolean visitedPath(Case c) {
		return cases.contains(c);
	}

	public Case notVisitedCase() {
		Case caseAround;
		for (Map.Entry<Direction, Case> entry : board.getCasesAround(pathFinding.getLastIn()).entrySet()) {
			caseAround = entry.getValue();
			if (!visitedPath(caseAround) && !caseAround.getWeight().equalsAnyOf(Weight.WALL, Weight.PIT, Weight.WUMPUS)) {
				return caseAround;
			}
		}
		return null;
	}

	public boolean caseAroudNotVisited(Case c) {
		boolean result = false;
		Case caseAround;
		for (Map.Entry<Direction, Case> entry : board.getCasesAround(c).entrySet()) {
			caseAround = entry.getValue();
			if (!visitedPath(caseAround) && caseAround.getWeight().equalsAnyOf(Weight.DEFAULT, Weight.POSSIBLE_PIT, Weight.POSSIBLE_WUMPUS, Weight.POSSIBLE_PIT_OR_WUMPUS, Weight.SAFE)) {
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

	public Stack<Case> findPath() {
		Case lastIn;
		Case notVisited;
		push(board.getAgentCase());
		while (pathFinding.size() > 0) {
			lastIn = pathFinding.getLastIn();
			if (caseAroudNotVisited(lastIn)) {
				if (path.size() > pathFinding.size()) {
					path = pathFinding;
				}
				pathFinding.pop();
			} else {
				notVisited = notVisitedCase();
				if (notVisited != null) {
					push(notVisited);
				} else {
					pathFinding.pop();
				}
			}
		}
		return path;
	}

}
