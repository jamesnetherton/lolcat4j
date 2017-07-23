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
import com.github.jamesnetherton.lolcat4j.internal.console.utils.LoggingPrintStream;
import com.github.jamesnetherton.lolcat4j.internal.console.utils.NonWritableOutputStream;
import org.junit.Assert;
import org.junit.Test;

import java.io.PrintStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConsolePainterTest {
    private static final String LOL_TXT = "lol.txt";
    private static final String LOL_ANSI_TXT = "lolAnsi.txt";
    private static final String LOL_TABS_TXT = "lolTabs.txt";

    @Test
    public void testNonAnimatedOutput() throws Exception {
        LoggingPrintStream printStream = new LoggingPrintStream(new NonWritableOutputStream());
        String fileContent = readFile(LOL_TXT);
        String expectedResult = readFile("nonAnimatedExpected.txt");

        Lol lol = Lol.builder()
            .seed(1)
            .frequency(3.0)
            .spread(3.0)
            .text(fileContent)
            .build();

        ConsolePainter consolePainter = createPainter(printStream, lol);
        consolePainter.paint(lol);

        Assert.assertEquals(expectedResult, printStream.getLoggedOutput());
    }

    @Test
    public void testAnimatedOutput() throws Exception {
        LoggingPrintStream printStream = new LoggingPrintStream(new NonWritableOutputStream());
        String fileContent = readFile(LOL_TXT);
        String expectedResult = readFile("animatedExpected.txt");

        Lol lol = Lol.builder()
            .animate()
            .duration(2)
            .speed(200.0)
            .seed(1)
            .frequency(3.0)
            .spread(3.0)
            .text(fileContent)
            .build();

        ConsolePainter consolePainter = createPainter(printStream, lol);
        consolePainter.paint(lol);

        Assert.assertEquals(expectedResult, printStream.getLoggedOutput());
    }

    @Test
    public void testStripAnsi() throws Exception {
        LoggingPrintStream printStream = new LoggingPrintStream(new NonWritableOutputStream());
        String fileContent = readFile(LOL_ANSI_TXT);
        String expectedResult = readFile("nonAnimatedExpected.txt");

        Lol lol = Lol.builder()
            .seed(1)
            .frequency(3.0)
            .spread(3.0)
            .text(fileContent)
            .build();

        ConsolePainter consolePainter = createPainter(printStream, lol);
        consolePainter.paint(lol);

        Assert.assertEquals(expectedResult, printStream.getLoggedOutput());
    }

    @Test
    public void testStripTabs() throws Exception {
        LoggingPrintStream printStream = new LoggingPrintStream(new NonWritableOutputStream());
        String fileContent = readFile(LOL_TABS_TXT);
        String expectedResult = readFile("nonAnimatedReplacedTabsExpected.txt");

        Lol lol = Lol.builder()
            .seed(1)
            .frequency(3.0)
            .spread(3.0)
            .text(fileContent)
            .build();

        ConsolePainter consolePainter = createPainter(printStream, lol);
        consolePainter.paint(lol);

        Assert.assertEquals(expectedResult, printStream.getLoggedOutput());
    }


    private ConsolePainter createPainter(PrintStream printStream, Lol lol) {
        ConsolePrinter printer = new ConsolePrinter(printStream);
        if (lol.isAnimate()) {
            return new AnimatedConsolePainter(printer);
        }
        return new ConsolePainter(printer);
    }

    private String readFile(String fileName) throws Exception {
        URI uri = getClass().getResource(fileName).toURI();
        return new String(Files.readAllBytes(Paths.get(uri)));
    }
}
