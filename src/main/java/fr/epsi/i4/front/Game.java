package fr.epsi.i4.front;

import fr.decisiontree.Config;
import fr.decisiontree.DecisionTree;
import fr.decisiontree.model.Result;
import fr.epsi.i4.back.model.Agent;
import fr.epsi.i4.back.model.Mode;
import fr.epsi.i4.back.model.PossibleChoice;
import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.back.model.board.Case;
import fr.epsi.i4.back.model.board.Direction;
import fr.epsi.i4.back.model.board.content.Weight;
import fr.epsi.i4.util.Util;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import static fr.epsi.i4.back.model.board.Direction.*;

public class Game extends JFrame implements KeyListener {

	public static final int caseSize = 100;

	private final Board board;

	private final Mode mode;

	private int rounds = 0;

	private int win = 0;

	private int death = 0;

	public Game(Board board, Mode mode) {
		this.board = board;
		this.mode = mode;
		initWindow();
		initDecisionTree();
	}

	private void initWindow() {
		setTitle("Wumpus");
		setSize(board.getWidth() * caseSize, board.getHeight() * caseSize);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setContentPane(new FrontGame(board));
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	private void initDecisionTree() {
		Config config = new Config("./decisionTree");
		config.addAttribut("Actuelle", Weight.getNames());
		config.addAttribut(LEFT.name(), Weight.getNames());
		config.addAttribut(RIGHT.name(), Weight.getNames());
		config.addAttribut(UP.name(), Weight.getNames());
		config.addAttribut(Direction.DOWN.name(), Weight.getNames());
		config.addAttribut("Direction", LEFT.name(), RIGHT.name(), UP.name(), Direction.DOWN.name());

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
		if (mode.equals(Mode.AUTO)) {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				playRound();
			}
		}
	}

	public void reset() {
		displayResult();
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
			directionsPossibles.add(LEFT);
		}
		if (board.getAgent().getX() < board.getWidth() - 2) {
			directionsPossibles.add(RIGHT);
		}
		if (board.getAgent().getY() < board.getHeight() - 2) {
			directionsPossibles.add(UP);
		}
		if (board.getAgent().getY() > 1) {
			directionsPossibles.add(Direction.DOWN);
		}

		if (board.getAgent().getBackCounter() > 0) {
			directionsPossibles.remove(board.getAgent().getDirection().getOpposite());
		}

		// Utiliser l'arbre de décision
		String[] entry;
		Result result;
		List<PossibleChoice> possibleChoices = new ArrayList<>();
		int i = 0;
		while (i < directionsPossibles.size()) {
			entry = new String[]{
					board.getAgentCase().getWeight().name(),
					casesAround[0].getWeight().name(),
					casesAround[1].getWeight().name(),
					casesAround[2].getWeight().name(),
					casesAround[3].getWeight().name(),
					directionsPossibles.get(i).name(),
					null
			};
			result = DecisionTree.decide(entry);
			if (result != null) {
				double ratio = result.getRatio();
				if (result.getValue().equals("Vivant")) {
					if (!explore(possibleChoices, result, directionsPossibles.get(i))) {
						if (!verifierSafe(possibleChoices, result, directionsPossibles.get(i))) {
							for (int j = 0; j < (int) (ratio * 10); j++) {
								possibleChoices.add(new PossibleChoice(result, directionsPossibles.get(i)));
							}
						}
					}
				} else {
//                                    if (ratio == 1){
//                                        possibleChoices.add(new PossibleChoice(result, directionsPossibles.get(i)));
//                                    } else {
					if (!explore(possibleChoices, result, directionsPossibles.get(i))) {
						if (!verifierSafe(possibleChoices, result, directionsPossibles.get(i))) {
							for (int j = 0; j < (int) ((1 - ratio) * 10); j++) {
								possibleChoices.add(new PossibleChoice(result, directionsPossibles.get(i)));
							}
						}
					}
//                                    }
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
		entry = new String[]{
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

		// Mise à jour de l'état du jeu
		updateGameState(entry);
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

	private boolean verifierSafe(List<PossibleChoice> possibleChoices, Result result, Direction direction) {
		int x = board.getAgent().getX();
		int y = board.getAgent().getY();
		boolean resultat = false;
		switch (direction) {
			case UP:
				if (board.getCase(x, y + 1).getWeight().getWeight() == 0) {
					for (int j = 0; j < 10; j++) {
						possibleChoices.add(new PossibleChoice(result, direction));
					}
					resultat = true;
				}
				break;
			case DOWN:
				if (board.getCase(x, y - 1).getWeight().getWeight() == 0) {
					for (int j = 0; j < 10; j++) {
						possibleChoices.add(new PossibleChoice(result, direction));
					}
					resultat = true;
				}
				break;
			case LEFT:
				if (board.getCase(x - 1, y).getWeight().getWeight() == 0) {
					for (int j = 0; j < 10; j++) {
						possibleChoices.add(new PossibleChoice(result, direction));
					}
					resultat = true;
				}
				break;
			case RIGHT:
				if (board.getCase(x + 1, y).getWeight().getWeight() == 0) {
					for (int j = 0; j < 10; j++) {
						possibleChoices.add(new PossibleChoice(result, direction));
					}
					resultat = true;
				}
				break;
		}
		return resultat;
	}

	private boolean explore(List<PossibleChoice> possibleChoices, Result result, Direction direction) {
		int x = board.getAgent().getX();
		int y = board.getAgent().getY();
		boolean resultat = false;
		switch (direction) {
			case UP:
				if (board.getCase(x, y + 1).getWeight().getWeight() > 0) {
					for (int j = 0; j < 4; j++) {
						possibleChoices.add(new PossibleChoice(result, direction));
					}
					resultat = true;
				}
				break;
			case DOWN:
				if (board.getCase(x, y - 1).getWeight().getWeight() > 0) {
					for (int j = 0; j < 4; j++) {
						possibleChoices.add(new PossibleChoice(result, direction));
					}
					resultat = true;
				}
				break;
			case LEFT:
				if (board.getCase(x - 1, y).getWeight().getWeight() > 0) {
					for (int j = 0; j < 4; j++) {
						possibleChoices.add(new PossibleChoice(result, direction));
					}
					resultat = true;
				}
				break;
			case RIGHT:
				if (board.getCase(x + 1, y).getWeight().getWeight() > 0) {
					for (int j = 0; j < 4; j++) {
						possibleChoices.add(new PossibleChoice(result, direction));
					}
					resultat = true;
				}
				break;
		}
		return resultat;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (mode.equals(Mode.MANUAL)) {
			Direction direction = null;
			switch (e.getKeyCode()) {
				case 37:
					direction = LEFT;
					break;
				case 39:
					direction = RIGHT;
					break;
				case 38:
					direction = UP;
					break;
				case 40:
					direction = DOWN;
					break;
			}
			if (direction != null) {
				// Cases autour de l'agent
				Case[] casesAround = board.getAgent().getCasesAround();

				// Ajout de l'entry dans l'arbre
				String[] entry = new String[]{
						board.getAgentCase().getWeight().name(),
						casesAround[0].getWeight().name(),
						casesAround[1].getWeight().name(),
						casesAround[2].getWeight().name(),
						casesAround[3].getWeight().name(),
						direction.name(),
						null
				};

				// Déplacement
				rounds += processTreeResult(direction);

				// Mise à jour de l'affichage
				refresh();

				// Mise à jour de l'état du jeu
				updateGameState(entry);
			}
			e.consume();
		}
	}

	private void updateGameState(String[] entry) {
		// Vérifie l'état du jeu
		System.out.println("Tour " + rounds);

		if (board.getAgent().isAlive()) {
			entry[6] = "Vivant";
		} else {
			entry[6] = "Mort";
		}
		DecisionTree.addData(entry);
		DecisionTree.regenerateTree();
		DecisionTree.print();
		DecisionTree.save();

		if (!board.getAgent().isAlive()) {
			death++;
			System.out.println("L'agent est décédé...");
			reset();
		} else if (board.getAgent().hasGold()) {
			win++;
			System.out.println("L'agent a récupéré l'or!!");
			reset();
		}
	}

	private void displayResult() {
		System.out.println("Nombre de morts: " + death);
		System.out.println("Nombre de victoires: " + win);
		System.out.println("Nombre de parties: " + (win + death));
		float winRate = (float) win / (float) (win + death) * 100.0f;
		System.out.println("Pourcentage de victoire: " + winRate + "%");
	}
}
