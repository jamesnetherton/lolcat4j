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
package com.github.jamesnetherton.lolcat4j.internal.console;

import com.github.jamesnetherton.lolcat4j.Lol;

class AnimatedConsolePainter extends ConsolePainter {

    private ConsolePrinter consolePrinter;

    public AnimatedConsolePainter(ConsolePrinter consolePrinter) {
        this.consolePrinter = consolePrinter;
    }

    @Override
    void output(Lol lol, ColorSeed seed, String line) {
        if (line.length() > 0) {
            String lineSeparator = System.lineSeparator();
            int animationSeed = seed.getValue();
            int lineLength = line.endsWith(lineSeparator) ? line.length() - lineSeparator.length() : 0;

            seed.increment();

            for (int i = 1; i <= lol.getDuration(); i++) {
                consolePrinter.cursorBack(lineLength);
                animationSeed += lol.getSpread();

                for (int j = 0; j < lineLength; j++) {
                    consolePrinter.printColorized(getHexRgbString(lol, animationSeed, j), line.charAt(j));
                }

                try {
                    Thread.sleep((long) ((1.0 / lol.getSpeed()) * 1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            consolePrinter.printNewLine();
        }
    }

    @Override
    void beforePainting() {
        consolePrinter.hideCursor();
    }

    @Override
    void afterPainting() {
        consolePrinter.showCursor();
    }
}
