package fr.epsi.i4.nao.front;

import javax.swing.*;

import fr.epsi.i4.nao.back.model.Agent;
import fr.epsi.i4.nao.back.model.board.Board;

import static fr.epsi.i4.nao.back.model.board.Direction.DOWN;
import static fr.epsi.i4.nao.back.model.board.Direction.LEFT;
import static fr.epsi.i4.nao.back.model.board.Direction.RIGHT;
import static fr.epsi.i4.nao.back.model.board.Direction.UP;

public class Game extends JFrame {

	private final Board board;

	public Game(Board board) {
		this.board = board;
		setTitle("Wumpus");
		setSize(800, 600);
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
			// Utiliser l'arbre de décision

			// Process result
			//			processTreeResult("UP");

			refresh();

			// Vérifie la fin du jeu
			verifyAgentAlive();
		}
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

	private void verifyAgentAlive() {
		if (!board.getAgent().isAlive()) {
			System.out.println("L'agent est décédé...");
			System.exit(0);
		}
	}
}
