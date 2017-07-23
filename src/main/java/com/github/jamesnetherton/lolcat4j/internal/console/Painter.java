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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Painter {
    private ConsolePrinter consolePrinter = new ConsolePrinter(System.out);

    public void paint(Lol lol) {
        ConsolePainter consolePainter;
        if (lol.isAnimate()) {
            consolePainter = new AnimatedConsolePainter(consolePrinter);
        } else {
            consolePainter = new ConsolePainter(consolePrinter);
        }

        if (lol.getText() != null && !lol.getText().isEmpty()) {
            consolePainter.paint(lol);
        } else {
            // Read content from STDIN
            try {
                if (System.in.available() > 0) {
                    consolePainter.paint(lol, System.in);
                }
            } catch (IOException e) {
                System.err.println("Error reading from STDIN: " + e.getMessage());
            }

            // Process each file in the order specified on the command line
            for (File file : lol.getFiles()) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    consolePainter.paint(lol, fileInputStream);
                } catch (FileNotFoundException e) {
                    throw new IllegalStateException(e);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }
}
