package fr.epsi.i4.back.model.board;

import fr.epsi.i4.back.model.board.content.Content;
import fr.epsi.i4.back.model.board.content.Weight;

/**
 * Created by tkint on 23/11/2017.
 */
public class Case {

	private Weight weight;

	private Content[] contents;

	private int x;

	private int y;

	public Case(int x, int y) {
		this.weight = Weight.DEFAULT;
		this.x = x;
		this.y = y;
		empty();
	}

	public Weight getWeight() {
		return weight;
	}

	public void setWeight(Weight weight) {
		this.weight = weight;
	}

	public Content[] getContents() {
		return contents;
	}

	@Override public String toString() {
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
		contents = new Content[5];
	}

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

	public boolean removeContent(Content content) {
		boolean removed = false;
		int i = 0;
		while (i < this.contents.length && !removed) {
			if (this.contents[i] != null && this.contents[i].equals(content)) {
				this.contents[i] = null;
				removed = true;
			}
			i++;
		}
		return removed;
	}

	public void setContent(Content content) {
		empty();
		addContent(content);
	}

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

	public boolean containsContents(Content... contents) {
		boolean contains = false;
		for (Content content : contents) {
			contains |= containsContent(content);
		}
		return contains;
	}

	public boolean containsAnythingButAgent() {
		return contents.length > 0 && !containsContent(Content.AGENT);
	}

	public boolean canContain(Content content) {
		return !containsContents(content.getIncompatibleElements());
	}

	public boolean hasBeenVisited() {
		return false;
	}

	public int calculateSafety() {
		return 0;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
