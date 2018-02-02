package fr.epsi.i4.nao.front;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import fr.decisiontree.Config;
import fr.decisiontree.DecisionTree;
import fr.epsi.i4.nao.back.model.Agent;
import fr.epsi.i4.nao.back.model.board.Board;
import fr.epsi.i4.nao.util.Util;

import static fr.epsi.i4.nao.back.model.board.Direction.DOWN;
import static fr.epsi.i4.nao.back.model.board.Direction.LEFT;
import static fr.epsi.i4.nao.back.model.board.Direction.RIGHT;
import static fr.epsi.i4.nao.back.model.board.Direction.UP;

public class Game extends JFrame {

	public static final int caseSize = 100;

	private static final String[] directions = { "Gauche", "Droite", "Haut", "Bas" };

	private final Board board;

	private int rounds = 0;

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
		config.addAttribut("Actuelle", "Rien", "Brise", "Odeur", "BriseEtOdeur");
		config.addAttribut("Gauche", "Rien", "Brise", "Odeur", "BriseEtOdeur", "Puit", "Wumpus");
		config.addAttribut("Droite", "Rien", "Brise", "Odeur", "BriseEtOdeur", "Puit", "Wumpus");
		config.addAttribut("Haut", "Rien", "Brise", "Odeur", "BriseEtOdeur", "Puit", "Wumpus");
		config.addAttribut("Bas", "Rien", "Brise", "Odeur", "BriseEtOdeur", "Puit", "Wumpus");
		config.addAttribut("Direction", directions);

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
				Thread.sleep(1000);
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
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//TODO: Le coton
	//TODO: Quand il n'y a rien autour, lancer aléatoire
	private void playRound() {
		// Défini les directions possibles
		List<String> directionsPossibles = new ArrayList<>();
		if (board.getAgent().getX() > 1) {
			directionsPossibles.add("Gauche");
		}
		if (board.getAgent().getY() > 1) {
			directionsPossibles.add("Bas");
		}
		if (board.getAgent().getX() < board.getWidth() - 2) {
			directionsPossibles.add("Droite");
		}
		if (board.getAgent().getY() < board.getHeight() - 2) {
			directionsPossibles.add("Haut");
		}

		// Utiliser l'arbre de décision
		String[] tmpEntry;
		String tmpDecision;
		List<String> possibleChoices = new ArrayList<>();
		int i = 0;
		while (i < directionsPossibles.size()) {
			tmpEntry = new String[] { "Rien", "Rien", "Rien", "Rien", "Rien", directionsPossibles.get(i), null };
			tmpDecision = DecisionTree.decide(tmpEntry);
			if (tmpDecision != null && tmpDecision.equals("Vivant")) {
				possibleChoices.add(directionsPossibles.get(i));
			}
			i++;
		}

		if (possibleChoices.isEmpty()) {
			possibleChoices.add(directionsPossibles.get(Util.randomInt(0, directionsPossibles.size() - 1)));
		}

		// Process result
		String choice = possibleChoices.get(Util.randomInt(0, possibleChoices.size() - 1));
		tmpEntry = new String[] { "Rien", "Rien", "Rien", "Rien", "Rien", choice, null };

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
			System.out.println("L'agent est décédé...");
			reset();
		} else if (board.getAgent().hasGold()) {
			System.out.println("L'agent a récupéré l'or!!");
			reset();
		}
	}

	public void refresh() {
		getGame().refresh();
	}

	private int processTreeResult(String treeResult) {
		Agent agent = board.getAgent();
		int toursUtilises = 0;
		switch (treeResult) {
			case "Gauche":
				toursUtilises = agent.move(LEFT);
				break;
			case "Droite":
				toursUtilises = agent.move(RIGHT);
				break;
			case "Haut":
				toursUtilises = agent.move(UP);
				break;
			case "Bas":
				toursUtilises = agent.move(DOWN);
				break;
		}
		return toursUtilises;
	}
}
