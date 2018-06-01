package fr.epsi.i4.back.model.board.content;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tkint on 17/12/2017.
 */
public enum Content {
	WALL(true), AGENT(true), GOLD(true), PIT(true), WUMPUS(true), BREEZE(false), STENCH(false), DIJKSTRA(false);

    private boolean solid;

    Content(boolean solid) {
        this.solid = solid;
    }

    public Content[] getIncompatibleElements() {
        Set<Content> contents = new HashSet<>();
        contents.add(this);
        contents.add(PIT);
		contents.add(WALL);
		for (Content content : Content.values()) {
			if (content.isSolid() && isSolid()) {
				contents.add(content);
			}
		}
		return contents.toArray(new Content[contents.size()]);
	}

    public boolean isSolid() {
        return solid;
    }
}
