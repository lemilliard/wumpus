package fr.epsi.i4.nao.front;

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

	//TODO: Le coton
	//TODO: Quand il n'y a rien autour, lancer aléatoire
	private void playRound() {
		// Incrémente le nombre de tour
		rounds++;

		// Utiliser l'arbre de décision
		String[] tmpEntry = new String[7];
		String choice = null;
		int i = 0;
		while (i < directions.length && choice == null) {
			tmpEntry = new String[] { "Rien", "Rien", "Rien", "Rien", "Rien", directions[i], null };
			tmpEntry[6] = DecisionTree.decide(tmpEntry);
			if (tmpEntry[6] != null && tmpEntry[6].equals("Vivant")) {
				choice = directions[i];
			}
			i++;
		}

		if (choice == null) {
			choice = directions[Util.randomInt(0, 3)];
			tmpEntry = new String[] { "Rien", "Rien", "Rien", "Rien", "Rien", choice, null };
		}

		// Process result
		processTreeResult(choice);

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
			System.exit(0);
		} else if (board.getAgent().hasGold()) {
			System.out.println("L'agent a récupéré l'or!!");
			System.exit(0);
		}
	}

	public void refresh() {
		getGame().refresh();
	}

	private void processTreeResult(String treeResult) {
		Agent agent = board.getAgent();
		switch (treeResult) {
			case "Gauche":
				agent.move(LEFT);
				break;
			case "Droite":
				agent.move(RIGHT);
				break;
			case "Haut":
				agent.move(UP);
				break;
			case "Bas":
				agent.move(DOWN);
				break;
		}
	}
}
