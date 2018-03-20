package fr.epsi.i4.back.model.board;

import fr.epsi.i4.back.model.Agent;
import fr.epsi.i4.back.model.board.content.Content;
import fr.epsi.i4.back.model.board.content.Gold;
import fr.epsi.i4.back.model.board.content.Weight;
import fr.epsi.i4.util.Randomizer;

import java.util.HashMap;

/**
 * Created by tkint on 23/11/2017.
 */
public class Board {

	private int width;

	private int height;

	private int pitsPercentage;

	private Case[][] cases;

	private Agent agent;

	private Randomizer randomizer;

	private Gold gold;

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

	public Gold getGold(){
	    return gold;
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
		Case c = null;
		if (x > -1
				&& x < width
				&& y > -1
				&& y < height) {
			c = cases[y][x];
		}
		return c;
	}

	public HashMap<Direction, Case> getCasesAround(int x, int y) {
		HashMap<Direction, Case> cases = new HashMap<>();
		if (getCase(x - 1, y) != null) {
			cases.put(Direction.LEFT, getCase(x - 1, y));
		}
		if (getCase(x + 1, y) != null) {
			cases.put(Direction.RIGHT, getCase(x + 1, y));
		}
		if (getCase(x, y - 1) != null) {
			cases.put(Direction.DOWN, getCase(x, y - 1));
		}
		if (getCase(x, y + 1) != null) {
			cases.put(Direction.UP, getCase(x, y + 1));
		}
		return cases;
	}

	public HashMap<Direction, Case> getCasesAround(Case c) {
		return getCasesAround(c.getX(), c.getY());
	}

	public Case getAgentCase() {
		return getCase(agent.getX(), agent.getY());
	}

	public void generate() {
		cases = new Case[height][width];
		for (int y = 0; y < height; y++) {
			cases[y] = new Case[width];
			for (int x = 0; x < width; x++) {
				cases[y][x] = new Case(x, y);
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


	public ArrayList<Case> getCaseContent(Content content){
		ArrayList<Case> posContent = new ArrayList<>();
		for (int y = cases.length - 1; y > -1; y--) {
			for (int x = cases.length - 1; x < cases[y].length; x++) {
				if (doesCaseContainsContent(x, y, content)) {
					posContent.add(getCase(x,y));
				}
			}
		}
		return posContent;
	}

//	public int[] getCaseContent(Content content){
//
//		int tabContentX[] = new int[0];
//		int tabContentY[] = new int[0];
//		int a = 0;
//		int tabFinal[];
//
//		for (int y = cases.length - 1; y > -1; y--) {
//			for (int x = cases.length - 1; x < cases[y].length; x++) {
//				if (doesCaseContainsContent(x, y, content)) {
//					tabContentX[a] += x;
//					tabContentY[a] += y;
//					a++;
//				}
//			}
//		}
//		tabFinal = new int[tabContentX.length + tabContentY.length];
//		System.arraycopy(tabContentX, 0, tabFinal, 0, tabContentX.length);
//		System.arraycopy(tabContentY, 0, tabFinal, tabContentX.length, tabContentY.length);
//
//		return tabFinal;
//	}

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
                // Random entre 2 et width - 1 afin de ne pas bloquer l'agent dès le debut
		randomizer = new Randomizer(2, width - 1);
		xy[0] = randomizer.randomize();
                // Random entre 2 et height - 1 afin de ne pas bloquer l'agent dès le debut
		randomizer = new Randomizer(2, height - 1);
		xy[1] = randomizer.randomize();
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

	public boolean doesCaseContainsContent(Case c, Content content) {
		return doesCaseContainsContent(c.getX(), c.getY(), content);
	}

	public boolean doesCaseContainsContent(int x, int y, Content content) {
		return getCase(x, y).containsContent(content);
	}

	public boolean isCaseWeightEquals(int x, int y, Weight weight) {
		return getCase(x, y).getWeight() != null && getCase(x, y).getWeight().equals(weight);
	}

	public boolean isCaseAlterable(Case c) {
		return c.getWeight() == null || (c.getWeight().equalsAnyOf(Weight.DEFAULT, Weight.POSSIBLE_WUMPUS, Weight.POSSIBLE_PIT, Weight.POSSIBLE_PIT_OR_WUMPUS));
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

	public Direction getDirectionByCase(Case c) {
		Direction direction = null;

		if (c.getX() == agent.getX() + 1
				&& c.getY() == agent.getY()) {
			direction = Direction.RIGHT;
		} else if (c.getX() == agent.getX() - 1
				&& c.getY() == agent.getY()) {
			direction = Direction.LEFT;
		} else if (c.getX() == agent.getX()
				&& c.getY() == agent.getY() + 1) {
			direction = Direction.UP;
		} else if (c.getX() == agent.getX()
				&& c.getY() == agent.getY() - 1) {
			direction = Direction.DOWN;
		}

		return direction;
	}
}
