/*
 * #%L
 * lolcat4j
 * %%
 * Copyright (C) 2016 James Netherton
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
package com.github.jamesnetherton.lolcat4j;

import com.github.jamesnetherton.lolcat4j.internal.console.AnimatedConsolePainter;
import com.github.jamesnetherton.lolcat4j.internal.console.ConsolePainter;
import com.github.jamesnetherton.lolcat4j.internal.console.ConsolePrinter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Lol {
    private boolean animate = LolCatConstants.DEFAULT_VALUE_ANIMATE;
    private Integer duration = LolCatConstants.DEFAULT_VALUE_DURATION;
    private Double frequency = LolCatConstants.DEFAULT_VALUE_FREQUENCY;
    private Integer seed = LolCatConstants.DEFAULT_VALUE_SEED;
    private Double speed = LolCatConstants.DEFAULT_VALUE_SPEED;
    private Double spread = LolCatConstants.DEFAULT_VALUE_SPREAD;
    private String text;
    private List<File> files = new ArrayList<>();

    private Lol() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getSpread() {
        return spread;
    }

    public void setSpread(Double spread) {
        this.spread = spread;
    }

    public Double getSpeed() {
        return speed;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public boolean isAnimate() {
        return animate;
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public void addToFiles(File file) {
        files.add(file);
    }

    public List<File> getFiles() {
        return Collections.unmodifiableList(files);
    }

    public void cat() {
        ConsolePrinter consolePrinter = new ConsolePrinter(System.out);
        ConsolePainter consolePainter = isAnimate() ? new AnimatedConsolePainter(consolePrinter) : new ConsolePainter(consolePrinter);

        if (getText() != null && !getText().isEmpty()) {
            consolePainter.paint(this);
        } else {
            try {
                if (System.in.available() > 0) {
                    consolePainter.paint(this, System.in);
                }
            } catch (IOException e) {
                System.err.println("Error reading from STDIN: " + e.getMessage());
            }

            for (File file : this.getFiles()) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    consolePainter.paint(this, fileInputStream);
                } catch (FileNotFoundException e) {
                    throw new IllegalStateException(e);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    public static LolCatBuilder builder() {
        return new LolCatBuilder();
    }

    public static final class LolCatBuilder {

        private Lol lol;

        public LolCatBuilder() {
            lol = new Lol();
        }

        public LolCatBuilder animate() {
            lol.setAnimate(true);
            return this;
        }

        public LolCatBuilder text(String text) {
            lol.setText(text);
            return this;
        }

        public LolCatBuilder seed(Integer seed) {
            lol.setSeed(seed);
            return this;
        }

        public LolCatBuilder speed(Double speed) {
            lol.setSpeed(speed);
            return this;
        }

        public LolCatBuilder spread(Double spread) {
            lol.setSpread(spread);
            return this;
        }

        public LolCatBuilder duration(Integer duration) {
            lol.setDuration(duration);
            return this;
        }

        public LolCatBuilder file(File file) {
            lol.addToFiles(file);
            return this;
        }

        public LolCatBuilder frequency(Double frequency) {
            lol.setFrequency(frequency);
            return this;
        }

        public Lol build() {
            return lol;
        }
    }
}
