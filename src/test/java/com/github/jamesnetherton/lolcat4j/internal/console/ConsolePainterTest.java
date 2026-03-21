package com.github.jamesnetherton.lolcat4j.internal.console;

import com.github.jamesnetherton.lolcat4j.Lol;
import com.github.jamesnetherton.lolcat4j.internal.console.utils.LoggingPrintStream;
import com.github.jamesnetherton.lolcat4j.internal.console.utils.NonWritableOutputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        assertEquals(expectedResult, printStream.getLoggedOutput());
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

        assertEquals(expectedResult, printStream.getLoggedOutput());
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

        assertEquals(expectedResult, printStream.getLoggedOutput());
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

        assertEquals(expectedResult, printStream.getLoggedOutput());
    }


    @Test
    public void testCustomInputStream() throws Exception {
        LoggingPrintStream printStream = new LoggingPrintStream(new NonWritableOutputStream());
        String fileContent = readFile(LOL_TXT);
        String expectedResult = readFile("nonAnimatedExpected.txt");

        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
        Lol lol = Lol.builder()
            .seed(1)
            .frequency(3.0)
            .spread(3.0)
            .inputStream(inputStream)
            .build();

        ConsolePainter consolePainter = createPainter(printStream, lol);
        consolePainter.paint(lol, lol.getInputStream());

        assertEquals(expectedResult, printStream.getLoggedOutput());
    }

    private ConsolePainter createPainter(PrintStream printStream, Lol lol) {
        ConsolePrinter printer = new ConsolePrinter(printStream, true);
        if (lol.isAnimate()) {
            return new AnimatedConsolePainter(printer);
        }
        return new ConsolePainter(printer);
    }

    private String readFile(String fileName) throws Exception {
        URI uri = getClass().getResource(fileName).toURI();
        return Files.readString(Path.of(uri));
    }
}
