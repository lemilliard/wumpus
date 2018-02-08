package fr.epsi.i4.util;

import java.security.SecureRandom;

/**
 * Created by tkint on 15/12/2017.
 */
public class Util {

    public static int randomInt(int min, int max) {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(max - min + 1) + min;
//        int rand = (max - min) / 2;
//        for (int i = min; i < max; i++) {
//            if (((Math.random() * 100) * Math.random()) > ((Math.random() * 100) * Math.random())) {
//                rand++;
//                if (rand > max) {
//                    rand = min;
//                }
//            } else {
//                rand--;
//                if (rand < min) {
//                    rand = max;
//                }
//            }
//        }
//        return rand;
    }

    public static int doubleRand() {
        return (int) ((Math.random() * 100) * Math.random());
    }
}
