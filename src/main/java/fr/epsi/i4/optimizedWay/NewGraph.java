package fr.epsi.i4.optimizedWay;

import fr.epsi.i4.back.model.Stack;
import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.Case;
import fr.epsi.i4.back.model.board.Direction;
import fr.epsi.i4.back.model.board.content.Content;

import java.util.HashMap;
import java.util.Map;

public class NewGraph {
	private Stack<Case> path;
	private Board board;
	private Direction direction;
	private int x;
	private int y;
	private int xGold;
	private int yGold;
	private int nbOfTour;
	private boolean gold;
	private Case[][] tabContent;

	public NewGraph(Board board) {
		this.board = board;
		this.direction = Direction.UP;
		this.path = new Stack<>();
		this.x = 1;
		this.y = 1;
		this.nbOfTour = 0;
		this.tabContent = board.getCases();
		this.gold = false;
		recupCaseGold();
		parcours(this.x, this.y);
	}

	private void parcours(int x, int y) {
		for (int i = x; i < tabContent.length - 1; i++) {
			for (int j = y; j < tabContent.length - 1; j++) {
				if (!gold) {
					Case cases = tabContent[i][j];
					HashMap<Direction, Case> caseAround = board.getCasesAround(cases.getX(), cases.getY());
					verifProcheGold(caseAround);
				}
			}
		}
		System.out.println("Nombre de tour : " + nbOfTour);
	}

	private void move(int x, int y) { // On vérifie que la case est disponible (sans PIT) et on se place sur celle-ci en lui ajoutant le content Dijstra
		if (x >= 1 && x < board.getWidth() - 1 && y >= 1 && y < board.getHeight() - 1) {
			if (getCaseDispo(x, y)) {
				board.getCase(x, y).addContent(Content.DIJKSTRA);
				countTour(x, y);
				path.push(board.getCase(x, y));
				tabContent[x][y].setVisited(true);
				// this.x = x;
				// this.y = y;
			}
		}
	}

	private boolean getCaseDispo(int x, int y) {
		boolean goodMove = false;
		if (!board.getCase(x, y).containsContent(Content.PIT) && !tabContent[x][y].isVisited()) {
			goodMove = true;
		}
		return goodMove;
	}

	private void verifProcheGold(HashMap<Direction, Case> caseAround) {
		HashList<Integer, Integer> hashList = new HashList<>();
		int coordinateX = 0;
		int coordinateY = 0;
		for (Map.Entry<Direction, Case> aroundEntry : caseAround.entrySet()) {
			Case value = aroundEntry.getValue();
			if (!value.containsContent(Content.WALL)) {
				hashList.addItem(value.getX(), value.getY());
			}
		}
		for (int i = 0; i < hashList.size(); i++) {
			int keyX = hashList.getPair(i).getKey();
			int valueY = hashList.getPair(i).getValue();
			if (keyX == xGold && valueY == yGold) {
				coordinateX = keyX;
				coordinateY = valueY;
				gold = true;
			}else if (i == 0 || ((keyX < hashList.getPair(i - 1).getKey() && valueY < hashList.getPair(i - 1).getValue()) && !tabContent[keyX][valueY].isVisited())) { // Condition pour déterminer la distance avec le gold qui permettra de sélectionner la case
				coordinateX = keyX;
				coordinateY = valueY;
			}
		}
		move(coordinateX, coordinateY);
	}

	private void recupCaseGold() {
		for (int i = 1; i < tabContent.length - 1; i++) {
			for (int j = 1; j < tabContent.length - 1; j++) {
				Case cases = tabContent[i][j];
				if (cases.containsContent(Content.GOLD)) {
					this.xGold = cases.getX();
					this.yGold = cases.getY();
				}
			}
		}
	}

	private void countTour(int x, int y) {
		if (board.getCase(x, y).containsContent(Content.WUMPUS)) {
			this.nbOfTour++;
		}
		this.nbOfTour++;
	}
}
