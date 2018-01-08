package fr.epsi.i4.nao.back.model.board;

import fr.epsi.i4.nao.back.model.board.content.Content;
import fr.epsi.i4.nao.back.model.board.content.Weight;

import static fr.epsi.i4.nao.back.model.board.content.Content.*;
import static fr.epsi.i4.nao.back.model.board.content.Weight.DEFAULT;

/**
 * Created by tkint on 23/11/2017.
 */
public class Case implements ICase {

    private Weight weight;
    private Content[] contents;

    public Case() {
        this.weight = DEFAULT;
        empty();
    }

    public Weight getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < contents.length; i++) {
            if (contents[i] == null) {
                stringBuilder.append("_");
            } else {
                stringBuilder.append(contents[i].name().substring(0, 1));
            }
        }

        return stringBuilder.toString();
    }

    public void empty() {
        contents = new Content[4];
    }

    @Override
    public Content addContent(Content content) {
        boolean added = false;
        int i = 0;
        while (i < this.contents.length && !added) {
            if (this.contents[i] == null) {
                this.contents[i] = content;
                added = true;
            }
            i++;
        }
        return content;
    }

    public void setContent(Content content) {
        empty();
        addContent(content);
    }

    @Override
    public boolean containsContent(Content content) {
        boolean contains = false;
        int i = 0;
        while (i < this.contents.length && !contains) {
            if (this.contents[i] == content) {
                contains = true;
            }
            i++;
        }
        return contains;
    }

    @Override
    public boolean containsContents(Content... contents) {
        boolean contains = false;
        for (Content content : contents) {
            contains |= containsContent(content);
        }
        return contains;
    }

    @Override
    public boolean containsAnythingButAgent() {
        return contents.length > 0 && !containsContent(AGENT);
    }

    public boolean canContain(Content content) {
        boolean canContain = !containsContents(content.getIncompatibleElements());
//        switch (content) {
//            case BREEZE:
//                canContain = !containsContents(BREEZE, PIT, WUMPUS);
//                break;
//            case STENCH:
//                canContain = !containsContents(STENCH, PIT, WUMPUS);
//                break;
//            case PIT:
//            case WUMPUS:
//            case GOLD:
//                canContain = !containsContents(AGENT, PIT, WUMPUS, GOLD);
//                break;
//        }
        return canContain;
    }

    @Override
    public int calculateWeight() {
        int weight = 0;
        for (Content content : contents) {
            if (content != null) {
//                weight += content;
            }
        }
        return weight;
    }

    @Override
    public boolean hasBeenVisited() {
        return false;
    }

    @Override
    public int calculateSafety() {
        return 0;
    }
}
