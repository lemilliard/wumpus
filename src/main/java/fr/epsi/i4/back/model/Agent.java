package fr.epsi.i4.back.model;

import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.Case;
import fr.epsi.i4.back.model.board.Direction;
import fr.epsi.i4.back.model.board.content.Content;
import fr.epsi.i4.back.model.board.content.Weight;

import java.util.HashMap;
import java.util.Map;

import static fr.epsi.i4.back.model.board.content.Weight.*;

public class Agent {

	private boolean alive = true;

	private boolean hasGold = false;

	private int x;

	private int y;

	private Board board;

	private Direction direction;

	private int backCounter;

	private Case casePrecedente;

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
//			System.out.println("L'agent se tourne");
		}
		if (direction.getOpposite().equals(this.direction)) {
			toursUtilises++;
//			System.out.println("L'agent se tourne encore");
		}
		this.direction = direction;
//		System.out.println("L'agent va vers " + direction);
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
			this.casePrecedente = board.getCase(this.x, this.y);

			board.getCase(this.x, this.y).removeContent(Content.AGENT);
			board.getCase(x, y).addContent(Content.AGENT);
			board.getCase(this.x, this.y).setWeight(VISITED);
//			board.getCase(x, y).setWeight(SAFE);
			board.getCase(x, y).setWeight(VISITED);
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
			setWeightCasesAround(POSSIBLE_PIT);
		} else if (board.doesCaseContainsContent(x, y, Content.STENCH)) {
			setWeightCasesAround(POSSIBLE_WUMPUS);
		} else if (board.doesCaseContainsContent(x, y, Content.BREEZE) && board.doesCaseContainsContent(x, y, Content.STENCH)) {
			setWeightCasesAround(POSSIBLE_PIT_OR_WUMPUS);
		} else {
			setWeightCasesAround(SAFE);
		}
//		updateWeightsOfCasesAroundCase();
                updateWeightsCaseAround(board.getAgentCase(), 3);
	}

	private void setWeightCasesAround(Weight weight) {
		Case caseAround;
		for (Map.Entry<Direction, Case> entry : getCasesAround().entrySet()) {
			caseAround = entry.getValue();
			board.setCaseWeight(caseAround, weight);
		}
	}
        
        /**
         * Met à jour le poids des case autour en s'apelle jusqu'a attendre la profondeur de parcours de niveau demandé
         * @param c
         * @param profondeur 
         */
        private void updateWeightsCaseAround(Case c, int profondeur){
            if(profondeur > -1){
            Case caseAround;
            int count = 0;
            Case caseToUpdate = null;
                if(c.getWeight().equalsAnyOf(POSSIBLE_PIT, POSSIBLE_PIT_OR_WUMPUS)){
                    for (Map.Entry<Direction, Case> entry : board.getCasesAround(c).entrySet()){
                        caseAround = entry.getValue();
                        if(caseAround.getWeight().equalsAnyOf(WALL, WUMPUS, PIT) || (caseAround.getWeight().equals(VISITED) && caseAround.getContents().equals(Content.BREEZE))){
                            count++;
                        }
                        updateWeightsCaseAround(caseAround, profondeur - 1);
                    }
                    if(count > 3){
                        board.setCaseWeight(c, PIT);
                    }
                } else if (c.getWeight().equalsAnyOf(POSSIBLE_WUMPUS, POSSIBLE_PIT_OR_WUMPUS)){
                    for (Map.Entry<Direction, Case> entry : board.getCasesAround(c).entrySet()){
                        caseAround = entry.getValue();
                        if(caseAround.getWeight().equalsAnyOf(WALL, WUMPUS, PIT) || (caseAround.getWeight().equals(VISITED) && caseAround.getContents().equals(Content.STENCH))){
                            count++;
                        } 
                        updateWeightsCaseAround(caseAround, profondeur - 1);
                    }
                    if(count > 1){
                        board.setCaseWeight(c, WUMPUS);
                    }
                } else if (c.getWeight().equals(VISITED) && board.doesCaseContainsContent(c, Content.BREEZE)){
                    for (Map.Entry<Direction, Case> entry : board.getCasesAround(c).entrySet()){
                        caseAround = entry.getValue();
                        if(caseAround.getWeight().equalsAnyOf(VISITED, WALL, WUMPUS)){
                            count++;
                        } else {
                            caseToUpdate = caseAround;
                        }
                        updateWeightsCaseAround(caseAround, profondeur - 1);
                        if(caseToUpdate != null && count == 3){
                            board.setCaseWeight(caseToUpdate, PIT);
                        }
                    }
                } else if (c.getWeight().equals(VISITED) && board.doesCaseContainsContent(c, Content.STENCH)){
                    for (Map.Entry<Direction, Case> entry : board.getCasesAround(c).entrySet()){
                        caseAround = entry.getValue();
                        if(caseAround.getWeight().equalsAnyOf(VISITED, WALL, PIT)){
                            count++;
                        } else {
                            caseToUpdate = caseAround;
                        }
                        updateWeightsCaseAround(caseAround, profondeur - 1);
                        if(caseToUpdate != null && count == 3){
                            board.setCaseWeight(caseToUpdate, WUMPUS);
                        }
                    }
                }
            }
        }

	private void updateWeightsOfCasesAroundCase() {
		int count;
		Case caseToUpdate = null;
		Case caseAround;
		Case caseAroundTheCaseAround;
		// Pour chaque case autour de l'agent
		for (Map.Entry<Direction, Case> entry : getCasesAround().entrySet()) {
			count = 0;
			caseAround = entry.getValue();
			// Si la case est dangereuse
			if (caseAround.getWeight().getWeight() < 0) {
				// Si le poids de cette case est possiblement un wumpus
				if (caseAround.getWeight().equalsAnyOf(POSSIBLE_WUMPUS, POSSIBLE_PIT_OR_WUMPUS)) {
					// Pour toutes les cases autour de cette case
					for (Map.Entry<Direction, Case> subEntry : board.getCasesAround(caseAround).entrySet()) {
						caseAroundTheCaseAround = subEntry.getValue();
						// Si la case est une odeur
						if (!caseAroundTheCaseAround.getWeight().equals(DEFAULT)
								&& board.doesCaseContainsContent(caseAroundTheCaseAround, Content.STENCH)) {
							// On incrémente le compteur
							count++;
						}
					}
					// Si la case est entourée de plus d'une odeur
					if (count > 1) {
						// On localise le wumpus
						board.setCaseWeight(caseAround, WUMPUS);
					}
					// Si le poids de cette case est possiblement un puit
				} else if (caseAround.getWeight().equalsAnyOf(POSSIBLE_PIT, POSSIBLE_PIT_OR_WUMPUS)) {
					// Pour toutes les cases autour de cette case
					for (Map.Entry<Direction, Case> subEntry : board.getCasesAround(caseAround).entrySet()) {
						caseAroundTheCaseAround = subEntry.getValue();
						// Si la case est une breeze ou un wall ou un puit ou un wumpus
						if (!caseAroundTheCaseAround.getWeight().equals(DEFAULT)
								&& (board.doesCaseContainsContent(caseAroundTheCaseAround, Content.BREEZE)
								|| caseAroundTheCaseAround.getWeight().equalsAnyOf(WALL, PIT, WUMPUS))) {
							// On incrémente le compteur
							count++;
						}
					}
					// Si la case est entourée d'au moins trois points bloquants
					if (count > 3) {
						// On localise un puit
						board.setCaseWeight(caseAround, PIT);
					}
				}
				// Si le poids de cette case est une odeur
			} else if (!caseAround.getWeight().equals(DEFAULT)
					&& board.doesCaseContainsContent(caseAround, Content.STENCH)) {
				// Pour toutes les cases autour de cette case
				for (Map.Entry<Direction, Case> subEntry : board.getCasesAround(caseAround).entrySet()) {
					caseAroundTheCaseAround = subEntry.getValue();
					// Si la case est safe ou un mur ou un puit ou un wumpus ou visitée
					if (caseAroundTheCaseAround.getWeight().equalsAnyOf(SAFE, WALL, PIT, VISITED)) {
						// On incrémente le compteur
						count++;
						// Sinon, on récupère la case à mettre à jour
					} else {
						caseToUpdate = caseAroundTheCaseAround;
					}
				}
				// Si la case est entourée de plus de deux bloqueurs
				if (count > 2 && caseToUpdate != null) {
					// On localise le wumpus
					board.setCaseWeight(caseToUpdate, WUMPUS);
				}
				// Si le poids de cette case est une breeze
			} else if (!caseAround.getWeight().equals(DEFAULT)
					&& board.doesCaseContainsContent(caseAround, Content.BREEZE)) {
				// Pour toutes les cases autour de cette case
				for (Map.Entry<Direction, Case> subEntry : board.getCasesAround(caseAround).entrySet()) {
					caseAroundTheCaseAround = subEntry.getValue();
					// Si la case est safe ou un mur ou un puit ou un wumpus ou visitée
					if (caseAroundTheCaseAround.getWeight().equalsAnyOf(SAFE, WALL, WUMPUS, VISITED)) {
						count++;
						// Sinon, on récupère la case à mettre à jour
					} else {
						caseToUpdate = caseAroundTheCaseAround;
					}
				}
				// Si la case est entourée de plus de deux bloqueurs
				if (count > 2 && caseToUpdate != null) {
					// On localise un puit
					board.setCaseWeight(caseToUpdate, PIT);
				}
			}
		}
	}

	public HashMap<Direction, Case> getCasesAround() {
		return board.getCasesAround(x, y);
	}

	public Case getCase() {
		return board.getCase(x, y);
	}

	public Case getCasePrecedente() {
		return casePrecedente;
	}
}
