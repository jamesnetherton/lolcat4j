package com.github.jamesnetherton.lolcat4j.internal.console;

import java.util.Random;

class ColorSeed {
    private int seedValue;

    ColorSeed(int initialValue) {
        if (initialValue == 0) {
            seedValue = new Random().nextInt(256);
        } else {
            this.seedValue = initialValue + 1;
        }
    }

    public void increment() {
        seedValue += 1;
    }

    public int getValue() {
        return seedValue;
    }
}
