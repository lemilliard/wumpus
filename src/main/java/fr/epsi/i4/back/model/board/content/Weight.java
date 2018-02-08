package fr.epsi.i4.back.model.board.content;

/**
 * Created by tkint on 17/12/2017.
 */
public enum Weight {
    GOLD(9),
    VISITED_4(4),
    VISITED_3(3),
    VISITED_2(2),
    VISITED_1(1),
    SAFE(0),
    DEFAULT(-1),
    POSSIBLE_PIT(-2),
    POSSIBLE_WUMPUS(-3),
    PIT(-4),
    POSSIBLE_PIT_OR_WUMPUS(-5),
    WUMPUS(-6),
    WALL(-9);

    private int weight;

    Weight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
