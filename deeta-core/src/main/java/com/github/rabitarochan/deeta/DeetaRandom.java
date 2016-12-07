package com.github.rabitarochan.deeta;

import java.util.Random;

public class DeetaRandom {

    private final Random random;

    public DeetaRandom() {
        this.random = new Random();
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public int nextInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    public long nextLong(long bound) {
        long bits;
        long val;
        do {
            bits = (random.nextLong() << 1) >>> 1;
            val = bits % bound;
        } while (bits - val + (bound + 1) < 0L);
        return val;
    }

    public long nextLong(long min, long max) {
        return min + nextLong(max - min + 1);
    }

}
