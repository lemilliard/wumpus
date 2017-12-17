package fr.epsi.i4.nao.util;

/**
 * Created by tkint on 15/12/2017.
 */
public class Util {

    public static int randomInt(int min, int max) {
        int rand = min;
        for (int i = min; i < max; i++) {
            if (((Math.random() * 100) * Math.random()) > ((Math.random() * 100) * Math.random())) {
                rand++;
            }
        }
        return rand;
    }

    public static int doubleRand() {
        return (int) ((Math.random() * 100) * Math.random());
    }
}
