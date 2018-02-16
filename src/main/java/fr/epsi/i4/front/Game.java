package fr.epsi.i4.front;

import fr.decisiontree.Config;
import fr.decisiontree.DecisionTree;
import fr.decisiontree.model.Result;
import fr.epsi.i4.back.model.Agent;
import fr.epsi.i4.back.model.PossibleChoice;
import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.Case;
import fr.epsi.i4.back.model.board.Direction;
import fr.epsi.i4.back.model.board.content.Weight;
import fr.epsi.i4.util.Util;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static fr.epsi.i4.back.model.board.content.Weight.SAFE;

public class Game extends JFrame {

	public static final int caseSize = 100;

	private final Board board;

	private int rounds = 0;
        
        private int win = 0;
        
        private int death = 0;

	public Game(Board board) {
		this.board = board;
		initWindow();
		initDecisionTree();
	}

	private void initWindow() {
		setTitle("Wumpus");
		setSize(board.getWidth() * caseSize, board.getHeight() * caseSize);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setContentPane(new FrontGame(board));
	}

	private void initDecisionTree() {
		Config config = new Config("./decisionTree");
		config.addAttribut("Actuelle", Weight.getNames());
		config.addAttribut(Direction.LEFT.name(), Weight.getNames());
		config.addAttribut(Direction.RIGHT.name(), Weight.getNames());
		config.addAttribut(Direction.UP.name(), Weight.getNames());
		config.addAttribut(Direction.DOWN.name(), Weight.getNames());
		config.addAttribut("Direction", Direction.LEFT.name(), Direction.RIGHT.name(), Direction.UP.name(), Direction.DOWN.name());

		config.addDecision("Vivant");
		config.addDecision("Mort");

		DecisionTree.init(config);
	}

	public FrontGame getGame() {
		return (FrontGame) getContentPane();
	}

	public void play() {
		refresh();
		setVisible(true);
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			playRound();
		}
	}

	public void reset() {
		System.out.println("------------------------");
		System.out.println("New Game");
		System.out.println("------------------------");
		rounds = 0;
		board.regenerate();
		refresh();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//TODO: Le coton
	//TODO: Quand il n'y a rien autour, lancer aléatoire
	private void playRound() {
		// Cases autour de l'agent
		Case[] casesAround = board.getAgent().getCasesAround();

		// Défini les directions possibles
		List<Direction> directionsPossibles = new ArrayList<>();
		if (board.getAgent().getX() > 1) {
			directionsPossibles.add(Direction.LEFT);
		}
		if (board.getAgent().getX() < board.getWidth() - 2) {
			directionsPossibles.add(Direction.RIGHT);
		}
		if (board.getAgent().getY() < board.getHeight() - 2) {
			directionsPossibles.add(Direction.UP);
		}
		if (board.getAgent().getY() > 1) {
			directionsPossibles.add(Direction.DOWN);
		}

		if (board.getAgent().getBackCounter() > 0) {
			directionsPossibles.remove(board.getAgent().getDirection().getOpposite());
		}

		// Utiliser l'arbre de décision
		String[] tmpEntry;
		Result result;
		List<PossibleChoice> possibleChoices = new ArrayList<>();
		int i = 0;
		while (i < directionsPossibles.size()) {
			tmpEntry = new String[]{
					board.getAgentCase().getWeight().name(),
					casesAround[0].getWeight().name(),
					casesAround[1].getWeight().name(),
					casesAround[2].getWeight().name(),
					casesAround[3].getWeight().name(),
					directionsPossibles.get(i).name(),
					null
			};
			result = DecisionTree.decide(tmpEntry);
			if (result != null) {
				double ratio = result.getRatio();
				if (result.getValue().equals("Vivant")) {
                                    if(!explore(possibleChoices, result, directionsPossibles.get(i))){
					for (int j = 0; j < (int) (ratio * 10); j++) {
						possibleChoices.add(new PossibleChoice(result, directionsPossibles.get(i)));
					}
                                    }
				} else {
                                    if (ratio == 1){
                                        possibleChoices.add(new PossibleChoice(result, directionsPossibles.get(i)));
                                    } else {
					for (int j = 0; j < (int) ((1 - ratio) * 10); j++) {
						possibleChoices.add(new PossibleChoice(result, directionsPossibles.get(i)));
					}
                                    }
				}
			}
			i++;
		}

		// Process result
		Direction choice;
		if (possibleChoices.isEmpty()) {
			choice = directionsPossibles.get(Util.randomInt(0, directionsPossibles.size() - 1));
		} else {
			choice = possibleChoices.get(Util.randomInt(0, possibleChoices.size() - 1)).getChoice();
		}
		tmpEntry = new String[]{
				board.getAgentCase().getWeight().name(),
				casesAround[0].getWeight().name(),
				casesAround[1].getWeight().name(),
				casesAround[2].getWeight().name(),
				casesAround[3].getWeight().name(),
				choice.name(),
				null
		};

		// Incrémente les tours et process result
		rounds += processTreeResult(choice);

		// Mise à jour de l'affichage
		refresh();

		// Vérifie l'état du jeu
		System.out.println("Tour " + rounds);

		if (board.getAgent().isAlive()) {
			tmpEntry[6] = "Vivant";
		} else {
			tmpEntry[6] = "Mort";
		}
		DecisionTree.addData(tmpEntry);
		DecisionTree.regenerateTree();
		DecisionTree.print();
		DecisionTree.save();

		if (!board.getAgent().isAlive()) {
                        death++;
			System.out.println("L'agent est décédé...");
                        System.out.println("compteur de mort = " + death);
                        System.out.println("compteur de win = " + win);
			reset();
		} else if (board.getAgent().hasGold()) {
                        win++;
			System.out.println("L'agent a récupéré l'or!!");
                        System.out.println("compteur de mort = " + death);
                        System.out.println("compteur de win = " + win);
			reset();
		}
	}

	private Result processResult(List<Result> possibleChoices) {
		return null;
	}

	public void refresh() {
		getGame().refresh();
	}

	private int processTreeResult(Direction treeResult) {
		Agent agent = board.getAgent();
		return agent.move(treeResult);
	}
        
        private boolean explore(List<PossibleChoice> possibleChoices, Result result, Direction direction){
            int x = board.getAgent().getX();
            int y = board.getAgent().getY();
            boolean resultat = false;
            switch(direction){
                case UP: 
                    if (board.getCase(x, y + 1).getWeight().getWeight() > 0){
                        for (int j = 0; j < 1; j++) {
                            possibleChoices.add(new PossibleChoice(result, direction));
			}
                        resultat = true;
                    }
                break;
                case DOWN: 
                    if (board.getCase(x, y - 1).getWeight().getWeight() > 0){
                        for (int j = 0; j < 1; j++) {
                            possibleChoices.add(new PossibleChoice(result, direction));
			}
                        resultat = true;
                    }
                break;
                case LEFT: 
                    if (board.getCase(x - 1, y).getWeight().getWeight() > 0){
                        for (int j = 0; j < 1; j++) {
                            possibleChoices.add(new PossibleChoice(result, direction));
			}    
                        resultat = true;
                    }
                break;
                case RIGHT: 
                    if (board.getCase(x + 1, y).getWeight().getWeight() > 0){
                        for (int j = 0; j < 1; j++) {
                            possibleChoices.add(new PossibleChoice(result, direction));
			}
                        resultat = true;
                    }
                break;
            }
            return resultat;
        }
}
