package com.github.rabitarochan.deeta;

import java.util.Random;

public class DeetaRandom {

    private final Random random;

    public DeetaRandom() {
        this.random = new Random();
    }

    public int nextInt() {
        return random.nextInt();
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public int nextInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

}
