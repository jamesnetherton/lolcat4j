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

import java.io.PrintStream;

class DefaultConsolePrinter implements ConsolePrinter{
    private static final String ESCAPE_SEQUENCE_START = "\033[";
    private static final String ESCAPE_SEQUENCE_END = "\033[0m";
    private PrintStream printStream;

    static {
        // Make sure the console is restored to its original state if the application is interrupted
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.print(ESCAPE_SEQUENCE_START + "?25h");
            }
        });
    }

    DefaultConsolePrinter(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void cursorBack(int amount) {
        printStream.print(ESCAPE_SEQUENCE_START + amount + "D");
    }

    @Override
    public void hideCursor() {
        printStream.print(ESCAPE_SEQUENCE_START + "?25l");
    }

    @Override
    public void printColorized(String colorCode, char charToPrint) {
        printStream.print(ESCAPE_SEQUENCE_START + colorCode + "m" + charToPrint + ESCAPE_SEQUENCE_END);
    }

    @Override
    public void printNewLine() {
        printStream.println();
    }

    @Override
    public void showCursor() {
        printStream.print(ESCAPE_SEQUENCE_START + "?25h");
    }
}
