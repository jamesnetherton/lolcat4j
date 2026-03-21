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
