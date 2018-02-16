package fr.epsi.i4.back.model.board;

import fr.epsi.i4.back.model.Agent;
import fr.epsi.i4.back.model.board.content.Content;
import fr.epsi.i4.back.model.board.content.Weight;
import fr.epsi.i4.util.Util;

/**
 * Created by tkint on 23/11/2017.
 */
public class Board {

	private int width;

	private int height;

	private int pitsPercentage;

	private Case[][] cases;

	private Agent agent;

	public Board(int width, int height, int pitsPercentage) {
		this.width = width + 2;
		this.height = height + 2;
		this.pitsPercentage = pitsPercentage;
		this.agent = new Agent(this);
		generate();
		this.agent.updateWeights();
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

	public Case getAgentCase() {
		return getCase(agent.getX(), agent.getY());
	}

	public void generate() {
		cases = new Case[height][width];
		for (int y = 0; y < height; y++) {
			cases[y] = new Case[width];
			for (int x = 0; x < width; x++) {
				cases[y][x] = new Case();
				// Ajout des murs
				if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
					setCaseContent(Content.WALL, x, y);
                                        setCaseWeight(x, y, Weight.WALL);
				}
			}
		}
		// Ajout de l'agent
		setCaseContent(Content.AGENT, 1, 1);
                
                // Ajout de l'or
		addCaseContent(Content.GOLD);
                
		// Ajout des puits
		int count = (int) ((double) pitsPercentage / 100.0d * ((double) (width - 2) * (height - 2)));
		for (int i = 0; i < count; i++) {
			addCaseContent(Content.PIT, Content.BREEZE);
		}
		// Ajout du Wumpus
		addCaseContent(Content.WUMPUS, Content.STENCH);
	}

	public void regenerate() {
		cases = null;
		agent = new Agent(this);
		generate();
                this.agent.updateWeights();
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
		xy[0] = Util.randomInt(2, width - 2);
		xy[1] = Util.randomInt(2, height - 2);
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
		if (content.equals(Content.PIT)) {
			setCaseContent(content, x, y);
		} else {
			addCaseContent(content, x, y);
		}
		if (around != null) {
			addCaseContentAround(x, y, around);
		}
	}

	public boolean doesCaseContainsContent(int x, int y, Content content) {
		return getCase(x, y).containsContent(content);
	}

	public boolean isCaseWeightEquals(int x, int y, Weight weight) {
		return getCase(x, y).getWeight() != null && getCase(x, y).getWeight().equals(weight);
	}

	public boolean isCaseAlterable(Case c) {
		return c.getWeight() == null || c.getWeight().getWeight() < 0;
	}

	public boolean isCaseAlterable(int x, int y) {
		return isCaseAlterable(getCase(x, y));
	}

	public void setCaseWeight(Case c, Weight weight) {
		if (isCaseAlterable(c)) {
			c.setWeight(weight);
		}
	}

	public void setCaseWeight(int x, int y, Weight weight) {
		setCaseWeight(getCase(x, y), weight);
	}
}
