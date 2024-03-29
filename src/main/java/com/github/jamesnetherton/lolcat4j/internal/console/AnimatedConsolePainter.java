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

public class AnimatedConsolePainter extends ConsolePainter {

    public AnimatedConsolePainter(ConsolePrinter consolePrinter) {
        super(consolePrinter);
    }

    @Override
    protected void output(Lol lol, ColorSeed seed, String line) {
        if (!line.isEmpty()) {
            String lineSeparator = null;
            int animationSeed = seed.getValue();
            int lineLength = 0;

            if (line.endsWith("\n")) {
                lineSeparator = "\n";
            } else if (line.endsWith("\r\n")) {
                lineSeparator = "\r\n";
            }

            if (lineSeparator != null) {
                lineLength = line.length() - lineSeparator.length();
            }

            seed.increment();

            for (int i = 1; i <= lol.getDuration(); i++) {
                consolePrinter.moveCursor(lineLength);
                animationSeed += lol.getSpread();

                for (int j = 0; j < lineLength; j++) {
                    consolePrinter.printColorized(lol, animationSeed, j, line.charAt(j));
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
    protected void beforePainting() {
        consolePrinter.hideCursor();
    }

    @Override
    protected void afterPainting() {
        consolePrinter.showCursor();
    }
}
