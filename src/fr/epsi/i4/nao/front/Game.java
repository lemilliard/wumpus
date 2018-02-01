package fr.epsi.i4.nao.front;

import javax.swing.*;

import fr.epsi.i4.nao.back.model.Agent;
import fr.epsi.i4.nao.back.model.board.Board;

import static fr.epsi.i4.nao.back.model.board.Direction.DOWN;
import static fr.epsi.i4.nao.back.model.board.Direction.LEFT;
import static fr.epsi.i4.nao.back.model.board.Direction.RIGHT;
import static fr.epsi.i4.nao.back.model.board.Direction.UP;

public class Game extends JFrame {

	public static final int caseSize = 100;

	private final Board board;

	private int rounds = 0;

	public Game(Board board) {
		this.board = board;
		setTitle("Wumpus");
		setSize(board.getWidth() * caseSize, board.getHeight() * caseSize);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setContentPane(new FrontGame(board));
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

	private void playRound() {
		// Incrémente le nombre de tour
		rounds++;

		// Utiliser l'arbre de décision

		// Process result
		//		processTreeResult("UP");

		// Mise à jour de l'affichage
		refresh();

		// Vérifie l'état du jeu
		verifyGameState();
	}

	public void refresh() {
		getGame().refresh();
	}

	private void processTreeResult(String treeResult) {
		Agent agent = board.getAgent();
		switch (treeResult) {
			case "UP":
				agent.move(UP);
				break;
			case "DOWN":
				agent.move(DOWN);
				break;
			case "LEFT":
				agent.move(LEFT);
				break;
			case "RIGHT":
				agent.move(RIGHT);
				break;
		}
	}

	private void verifyGameState() {
		System.out.println("Tour " + rounds);
		if (!board.getAgent().isAlive()) {
			System.out.println("L'agent est décédé...");
			System.exit(0);
		} else if (board.getAgent().hasGold()) {
			System.out.println("L'agent a récupéré l'or!!");
			System.exit(0);
		}
	}
}
