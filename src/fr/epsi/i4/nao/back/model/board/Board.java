package fr.epsi.i4.nao.back.model.board;

import fr.epsi.i4.nao.back.model.Agent;
import fr.epsi.i4.nao.back.model.board.content.Content;
import fr.epsi.i4.nao.util.Util;

import static fr.epsi.i4.nao.back.model.board.content.Content.AGENT;
import static fr.epsi.i4.nao.back.model.board.content.Content.BREEZE;
import static fr.epsi.i4.nao.back.model.board.content.Content.GOLD;
import static fr.epsi.i4.nao.back.model.board.content.Content.PIT;
import static fr.epsi.i4.nao.back.model.board.content.Content.STENCH;
import static fr.epsi.i4.nao.back.model.board.content.Content.WALL;
import static fr.epsi.i4.nao.back.model.board.content.Content.WUMPUS;

/**
 * Created by tkint on 23/11/2017.
 */
public class Board {

	private int width;

	private int height;

	private Case[][] cases;

	private Agent agent;

	public Board(int width, int height, int pitsPercentage) {
		this.agent = new Agent(this);
		this.width = width + 2;
		this.height = height + 2;
		generate(pitsPercentage);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Case[][] getCases() {
		return cases;
	}

	public Agent getAgent() {
		return agent;
	}

	@Override public String toString() {
		String str = "";

		for (int y = cases.length - 1; y > -1; y--) {
			str += "\n------------------------\n";
			for (int x = 0; x < cases[y].length; x++) {
				str += "|" + cases[y][x].toString() + "|";
			}
		}

		return str;
	}

	public Case getCase(int x, int y) {
		return cases[y][x];
	}

	public void generate(int pitsPercentage) {
		cases = new Case[height][width];
		for (int y = 0; y < height; y++) {
			cases[y] = new Case[width];
			for (int x = 0; x < width; x++) {
				cases[y][x] = new Case();
				// Ajout des murs
				if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
					setCaseContent(WALL, x, y);
				}
			}
		}
		// Ajout de l'agent
		setCaseContent(AGENT, 1, 1);
		// Ajout des puits
		int count = (int) ((double) pitsPercentage / 100.0d * ((double) (width - 2) * (height - 2)));
		for (int i = 0; i < count; i++) {
			addCaseContent(PIT, BREEZE);
		}
		// Ajout du Wumpus
		addCaseContent(WUMPUS, STENCH);
		// Ajout de l'or
		addCaseContent(GOLD);
	}

	public void addCaseContent(Content content, int x, int y) {
		getCase(x, y).addContent(content);
	}

	private void addCaseContentAround(int x, int y, Content content) {
		if (x > 0 && getCase(x - 1, y).canContain(content)) {
			addCaseContent(content, x - 1, y);
		}
		if (y > 0 && getCase(x, y - 1).canContain(content)) {
			addCaseContent(content, x, y - 1);
		}
		if (x < width - 1 && getCase(x + 1, y).canContain(content)) {
			addCaseContent(content, x + 1, y);
		}
		if (y < height - 1 && getCase(x, y + 1).canContain(content)) {
			addCaseContent(content, x, y + 1);
		}
	}

	private void setCaseContent(Content content, int x, int y) {
		getCase(x, y).setContent(content);
	}

	private int[] getRandomCoordinatesForContent(Content content) {
		int[] xy = new int[2];
		xy[0] = Util.randomInt(1, width - 2);
		xy[1] = Util.randomInt(1, height - 2);
		if (!getCase(xy[0], xy[1]).canContain(content)) {
			return getRandomCoordinatesForContent(content);
		}
		return xy;
	}

	private void addCaseContent(Content content) {
		addCaseContent(content, null);
	}

	private void addCaseContent(Content content, Content around) {
		int[] xy = getRandomCoordinatesForContent(content);
		int x = xy[0];
		int y = xy[1];
		if (content.equals(PIT)) {
			setCaseContent(content, x, y);
		} else {
			addCaseContent(content, x, y);
		}
		if (around != null) {
			addCaseContentAround(x, y, around);
		}
	}
}
