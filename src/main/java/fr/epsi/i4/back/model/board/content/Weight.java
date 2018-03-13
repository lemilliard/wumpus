package fr.epsi.i4.back.model.board.content;

/**
 * Created by tkint on 17/12/2017.
 */
public enum Weight {
    SAFE(0),
    VISITED(1),
    DEFAULT(-1),
    POSSIBLE_PIT(-2),
    POSSIBLE_WUMPUS(-3),
    PIT(-4),
    POSSIBLE_PIT_OR_WUMPUS(-5),
    WUMPUS(-6),
    WALL(8);

    private int weight;

    Weight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public static String[] getNames() {
	    String[] names = new String[values().length];
	    for (int i = 0; i < values().length; i++) {
		    names[i] = values()[i].name();
	    }
	    return names;
    }
}
