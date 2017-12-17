package fr.epsi.i4.nao.back.model.board;

import fr.epsi.i4.nao.back.model.board.content.Content;

/**
 * Created by tkint on 23/11/2017.
 */
public class Case {

    private int weight;
    private boolean passed;
    private Content[] content;

    public Case() {
        this.weight = 0;
        this.passed = false;
        this.content = new Content[4];
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Content[] getContent() {
        return content;
    }

    public void setContent(Content[] content) {
        this.content = content;
    }

    public Content addContent(Content content) {
        boolean added = false;
        int i = 0;
        while (i < this.content.length && !added) {
            if (this.content[i] == null) {
                this.content[i] = content;
                added = true;
            }
            i++;
        }
        return content;
    }

    public Content setContent(Content content, int index) {
        this.content[index] = content;
        return content;
    }

    public String getContentAsString() {
        String str = "";
        for (int i = 0; i < 4; i++) {
            str += this.content[i];
        }
        return str;
    }
}
