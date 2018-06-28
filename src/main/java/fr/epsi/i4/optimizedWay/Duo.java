package fr.epsi.i4.optimizedWay;

public class Duo {
    private int[] first;
    private int[] second;

    public Duo(int[] first, int[] second) {
        this.setFirst(first);
        this.setSecond(second);
    }

    public int[] getFirst() {
        return first;
    }

    public void setFirst(int[] first) {
        this.first = first;
    }

    public int[] getSecond() {
        return second;
    }

    public void setSecond(int[] second) {
        this.second = second;
    }
}
