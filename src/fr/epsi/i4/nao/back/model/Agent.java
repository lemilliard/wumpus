package fr.epsi.i4.nao.back.model;

import fr.epsi.i4.nao.back.model.board.Board;
import fr.epsi.i4.nao.back.model.board.Direction;

import static fr.epsi.i4.nao.back.model.board.content.Content.AGENT;
import static fr.epsi.i4.nao.back.model.board.content.Content.GOLD;
import static fr.epsi.i4.nao.back.model.board.content.Content.PIT;
import static fr.epsi.i4.nao.back.model.board.content.Content.WUMPUS;

public class Agent {

	private boolean alive = true;

	private boolean hasGold = false;

	private int x;

	private int y;

	private Board board;

	public Agent(Board board) {
		this.board = board;
		x = 0;
		y = 0;
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

	public void move(Direction direction) {
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
	}

	private void move(int x, int y) {
		if (x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight()) {
			verifyAlive(x, y);
			verifyHasGold(x, y);
			updateBoard();
			board.getCase(this.x, this.y).removeContent(AGENT);
			board.getCase(x, y).addContent(AGENT);
			this.x = x;
			this.y = y;
		}
	}

	private void verifyAlive(int x, int y) {
		if (board.getCase(x, y).containsContent(PIT) || board.getCase(x, y).containsContent(WUMPUS)) {
			alive = false;
		}
	}

	private void verifyHasGold(int x, int y) {
		if (board.getCase(x, y).containsContent(GOLD)) {
			hasGold = true;
		}
	}

	public boolean hasGold() {
		return hasGold;
	}

	private void updateBoard() {

	}
}
