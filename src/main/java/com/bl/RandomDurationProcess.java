package com.bl;

import java.util.Random;

public class RandomDurationProcess {

    public static void simulate(int min, int max) {
        int duration = min + new Random().nextInt(max - min);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            // Ignore.
        }
    }
}
