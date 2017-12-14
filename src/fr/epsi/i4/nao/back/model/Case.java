package fr.epsi.i4.nao.back.model;

/**
 * Created by tkint on 23/11/2017.
 */
public class Case {

    private int weight;
    private boolean passed;
    private String[] content;

    public Case() {
        this.weight = 0;
        this.passed = false;
        this.content = new String[4];
        for (int i = 0; i < 4; i++) {
            this.content[i] = "_";
        }
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

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }

    public String getContentAsString() {
        String str = "";
        for (int i = 0; i < 4; i++) {
            str += this.content[i];
        }
        return str;
    }

    @Override
    public String toString() {
        String str = "Case{" +
                "weight=" + weight +
                ", passed=" + passed +
                ", content=";

        for (int i = 0; i < 4; i++) {
            str += this.content[i];
        }

        str += '}';

        return str;
    }

    public String addContent(String content) {
        boolean added = false;
        int i = 0;
        while (i < this.content.length && !added) {
            if (this.content[i] == null || this.content[i].equals("_")) {
                this.content[i] = content;
                added = true;
            }
            i++;
        }
        return content;
    }

    public String setContent(String content, int index) {
        this.content[index] = content;
        return content;
    }
}
