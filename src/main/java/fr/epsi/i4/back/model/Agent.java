package fr.epsi.i4.back.model;

import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.Case;
import fr.epsi.i4.back.model.board.Direction;
import fr.epsi.i4.back.model.board.content.Content;

import java.util.List;

import static fr.epsi.i4.back.model.board.content.Weight.*;

public class Agent {

	private boolean alive = true;

	private boolean hasGold = false;

	private int x;

	private int y;

	private Board board;

	private Direction direction;

	private int backCounter;

	public Agent(Board board) {
		this.board = board;
		this.x = 1;
		this.y = 1;
		this.direction = Direction.UP;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean hasGold() {
		return hasGold;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getBackCounter() {
		return backCounter;
	}

	public Board getBoard() {
		return board;
	}

	public Direction getDirection() {
		return direction;
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
		if (toursUtilises == 3) {
			backCounter++;
		} else {
			backCounter = 0;
		}
		return toursUtilises;
	}

	private void move(int x, int y) {
		if (x >= 1 && x < board.getWidth() - 1 && y >= 1 && y < board.getHeight() - 1) {
			board.getCase(this.x, this.y).removeContent(Content.AGENT);
			board.getCase(x, y).addContent(Content.AGENT);
			board.getCase(this.x, this.y).setWeight(VISITED);
			board.getCase(x, y).setWeight(SAFE);
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

	public void updateWeights() {
		if (board.doesCaseContainsContent(x, y, Content.BREEZE)) {
			for (Case caseAround : getCasesAround()) {
				if (board.isCaseAlterable(caseAround)) {
					board.setCaseWeight(caseAround, POSSIBLE_PIT);
				}
			}
		} else if (board.doesCaseContainsContent(x, y, Content.STENCH)) {
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
			for (Case caseAround : getCasesAround()) {
				if (board.isCaseAlterable(caseAround)) {
					board.setCaseWeight(caseAround, SAFE);
				}
			}
		}
		updateWeightsOfCasesAroundCase();
	}

	private void updateWeightsOfCasesAroundCase() {
		int count;
		List<Case> casesAround = getCasesAround();
		Case caseToUpdate = null;
		for (Case caseAround : casesAround) {
			count = 0;
			if (caseAround.getWeight().getWeight() < 0) {
				if (caseAround.getWeight().equals(POSSIBLE_WUMPUS)
						|| caseAround.getWeight().equals(POSSIBLE_PIT_OR_WUMPUS)) {
					for (Case caseAroundTheCaseAround : board.getCasesAround(caseAround)) {
						if (!caseAroundTheCaseAround.getWeight().equals(DEFAULT) && board.doesCaseContainsContent(caseAroundTheCaseAround, Content.STENCH)) {
							count++;
						}
					}
					if (count > 1) {
						board.setCaseWeight(caseAround, WUMPUS);
					}
				} else if (caseAround.getWeight().equals(POSSIBLE_PIT)
						|| caseAround.getWeight().equals(POSSIBLE_PIT_OR_WUMPUS)) {
					for (Case caseAroundTheCaseAround : board.getCasesAround(caseAround)) {
						if (!caseAroundTheCaseAround.getWeight().equals(DEFAULT) && board.doesCaseContainsContent(caseAroundTheCaseAround, Content.BREEZE)
								|| caseAroundTheCaseAround.getWeight().equals(WALL)
								|| caseAroundTheCaseAround.getWeight().equals(PIT)
								|| caseAroundTheCaseAround.getWeight().equals(WUMPUS)) {
							count++;
						}
					}
					if (count > 3) {
						board.setCaseWeight(caseAround, PIT);
					}
				}
			} else if (!caseAround.getWeight().equals(DEFAULT) && board.doesCaseContainsContent(caseAround, Content.STENCH)) {
				for (Case caseAroundTheCaseAround : board.getCasesAround(caseAround)) {
					if (caseAroundTheCaseAround.getWeight().equals(SAFE)
							|| caseAroundTheCaseAround.getWeight().equals(WALL)
							|| caseAroundTheCaseAround.getWeight().equals(PIT)
							|| caseAroundTheCaseAround.getWeight().equals(WUMPUS)
							|| caseAroundTheCaseAround.getWeight().equals(VISITED)) {
						count++;
					} else {
						caseToUpdate = caseAroundTheCaseAround;
					}
				}
				if (count > 2 && caseToUpdate != null) {
					board.setCaseWeight(caseToUpdate, WUMPUS);
				}
			} else if (!caseAround.getWeight().equals(DEFAULT) && board.doesCaseContainsContent(caseAround, Content.BREEZE)) {
				for (Case caseAroundTheCaseAround : board.getCasesAround(caseAround)) {
					if (caseAroundTheCaseAround.getWeight().equals(SAFE)
							|| caseAroundTheCaseAround.getWeight().equals(WALL)
							|| caseAroundTheCaseAround.getWeight().equals(PIT)
							|| caseAroundTheCaseAround.getWeight().equals(WUMPUS)
							|| caseAroundTheCaseAround.getWeight().equals(VISITED)) {
						count++;
					} else {
						caseToUpdate = caseAroundTheCaseAround;
					}
				}
				if (count > 2 && caseToUpdate != null) {
					board.setCaseWeight(caseToUpdate, PIT);
				}
			}
		}
	}

	public List<Case> getCasesAround() {
		return board.getCasesAround(x, y);
	}
}
