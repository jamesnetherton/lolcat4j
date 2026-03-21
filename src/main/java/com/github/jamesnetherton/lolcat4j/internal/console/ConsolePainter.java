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
