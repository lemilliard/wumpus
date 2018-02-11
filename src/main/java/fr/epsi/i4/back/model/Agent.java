package fr.epsi.i4.back.model;

import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.Case;
import fr.epsi.i4.back.model.board.Direction;
import fr.epsi.i4.back.model.board.content.Content;

import static fr.epsi.i4.back.model.board.content.Weight.*;

public class Agent {

	private boolean alive = true;

	private boolean hasGold = false;

	private int x;

	private int y;

	private Board board;

	private Direction direction;

	public Agent(Board board) {
		this.board = board;
		this.x = 1;
		this.y = 1;
		this.direction = Direction.UP;
	}

	public boolean isAlive() {
		return alive;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int move(Direction direction) {
		int toursUtilises = 1;
		if (!direction.equals(this.direction)) {
			toursUtilises++;
			System.out.println("L'agent se tourne");
		}
		if (direction.getOpposite().equals(this.direction)) {
			toursUtilises++;
			System.out.println("L'agent se tourne encore");
		}
		this.direction = direction;
		System.out.println("L'agent va vers " + direction);
		switch (direction) {
			case UP:
				move(x, y + 1);
				break;
			case DOWN:
				move(x, y - 1);
				break;
			case LEFT:
				move(x - 1, y);
				break;
			case RIGHT:
				move(x + 1, y);
				break;
		}
		return toursUtilises;
	}

	private void move(int x, int y) {
		if (x >= 1 && x < board.getWidth() - 1 && y >= 1 && y < board.getHeight() - 1) {
			board.getCase(this.x, this.y).removeContent(Content.AGENT);
			board.getCase(x, y).addContent(Content.AGENT);
			this.x = x;
			this.y = y;
			verifyAlive();
			verifyHasGold();
			updateWeights();
		}
	}

	private void verifyAlive() {
		if (board.getCase(x, y).containsContent(Content.PIT) || board.getCase(x, y).containsContent(Content.WUMPUS)) {
			alive = false;
		}
	}

	private void verifyHasGold() {
		if (board.getCase(x, y).containsContent(Content.GOLD)) {
			hasGold = true;
		}
	}

	public boolean hasGold() {
		return hasGold;
	}

	public void updateWeights() {
		if (board.doesCaseContainsContent(x, y, Content.BREEZE)) {
			for (Case caseAround : getCasesAround()) {
				if (board.isCaseAlterable(caseAround)) {
					board.setCaseWeight(caseAround, POSSIBLE_PIT);
				}
			}
		} else if(board.doesCaseContainsContent(x, y, Content.STENCH)) {
			for (Case caseAround : getCasesAround()) {
				if (board.isCaseAlterable(caseAround)) {
					board.setCaseWeight(caseAround, POSSIBLE_WUMPUS);
				}
			}
		} else if (board.doesCaseContainsContent(x, y, Content.BREEZE) && board.doesCaseContainsContent(x, y, Content.STENCH)) {
			for (Case caseAround : getCasesAround()) {
				if (board.isCaseAlterable(caseAround)) {
					board.setCaseWeight(caseAround, POSSIBLE_PIT_OR_WUMPUS);
				}
			}
		} else {
			board.setCaseWeight(x, y, SAFE);
		}
	}

	public Case[] getCasesAround() {
		return new Case[]{board.getCase(x - 1, y), board.getCase(x + 1, y), board.getCase(x, y - 1), board.getCase(x, y + 1)};
	}
}
