package fr.epsi.i4.nao.back.model;

import fr.epsi.i4.nao.back.model.board.Board;
import fr.epsi.i4.nao.back.model.board.Direction;

import static fr.epsi.i4.nao.back.model.board.Direction.UP;
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

	private Direction direction;

	public Agent(Board board) {
		this.board = board;
		this.x = 1;
		this.y = 1;
		this.direction = UP;
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
