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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsolePainter {

    protected final ConsolePrinter consolePrinter;
    private ColorSeed seed;

    public ConsolePainter(ConsolePrinter consolePrinter) {
        this.consolePrinter = consolePrinter;
    }

    public void paint(Lol lol) {
        InputStream inputStream = new ByteArrayInputStream(lol.getText().getBytes(StandardCharsets.UTF_8));
        paint(lol, inputStream);
    }

    public void paint(Lol lol, InputStream inputStream) {
        String lastPaintedText = null;
        if (seed == null) {
            seed = new ColorSeed(lol.getSeed());
        }

        beforePainting();
        try (Scanner scanner = new Scanner(inputStream)) {
            scanner.useDelimiter("(?<=\n)|(?!\n)(?<=\r)");
            while (scanner.hasNext()) {
                lastPaintedText = scanner.next();
                output(lol, seed, consolePrinter.cleanString(lastPaintedText));
            }
        }
        afterPainting();

        if (lol.isInteractive()) {
            if (lastPaintedText != null && !lastPaintedText.endsWith("\n") && !lastPaintedText.endsWith("\r\n")) {
                System.out.print(System.lineSeparator());
            }
        }
    }

    protected void output(Lol lol, ColorSeed seed, String line) {
        seed.increment();

        for (int i = 0; i < line.length(); i++) {
            consolePrinter.printColorized(lol, seed.getValue(), i, line.charAt(i));
        }
    }

    protected void beforePainting() {
    }

    protected void afterPainting() {
    }
}
