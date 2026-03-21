package com.github.jamesnetherton.lolcat4j.internal.console;

import com.github.jamesnetherton.lolcat4j.Lol;

import java.io.PrintStream;
import java.util.Locale;

public class ConsolePrinter {
    private static final String ESCAPE_SEQUENCE_START = "\033[";
    private static final String ESCAPE_SEQUENCE_END = "\033[0m";
    private static final boolean DEFAULT_ANSI_SUPPORTED = detectAnsiSupport();

    private final PrintStream printStream;
    private final boolean ansiSupported;

    static {
        if (DEFAULT_ANSI_SUPPORTED) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.print(ESCAPE_SEQUENCE_START + "?25h")));
        }
    }

    public ConsolePrinter(PrintStream printStream) {
        this.printStream = printStream;
        this.ansiSupported = DEFAULT_ANSI_SUPPORTED;
    }

    ConsolePrinter(PrintStream printStream, boolean ansiSupported) {
        this.printStream = printStream;
        this.ansiSupported = ansiSupported;
    }

    public void moveCursor(int amount) {
        if (ansiSupported) {
            printStream.print(ESCAPE_SEQUENCE_START + amount + "D");
        }
    }

    public void showCursor() {
        if (ansiSupported) {
            printStream.print(ESCAPE_SEQUENCE_START + "?25h");
        }
    }

    public void hideCursor() {
        if (ansiSupported) {
            printStream.print(ESCAPE_SEQUENCE_START + "?25l");
        }
    }

    public void printColorized(Lol lol, int seed, int index, char charToPrint) {
        if (ansiSupported) {
            printStream.print(ESCAPE_SEQUENCE_START + getHexRgbString(lol, seed, index) + "m" + charToPrint + ESCAPE_SEQUENCE_END);
        } else {
            printStream.print(charToPrint);
        }
    }

    public void printNewLine() {
        printStream.println();
    }

    public String cleanString(String s) {
        return s.replaceAll("\\\\e\\[[\\d;]*[m|K]", "").replace("\t", "        ");
    }

    private String getHexRgbString(Lol lol, int seed, int index) {
        String rainbow = rainbow(lol.getFrequency(), seed + (index / lol.getSpread()));
        int[] rgb = hexToRgb(rainbow);
        return rgb(rgb[0], rgb[1], rgb[2]);
    }

    private String rainbow(double frequency, double index) {
        double red = Math.sin(frequency * index + 0) * 127 + 128;
        double green = Math.sin(frequency * index + 2 * Math.PI / 3) * 127 + 128;
        double blue = Math.sin(frequency * index + 4 * Math.PI / 3) * 127 + 128;

        return String.format("#%02X%02X%02X", (int) red, (int) green, (int) blue);
    }

    private int[] hexToRgb(String hexString) {
        int color = Integer.parseInt(hexString.substring(1), 16);
        return new int[]{(color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF};
    }

    private String rgb(int red, int green, int blue) {
        return "38" + rgbValue(red, green, blue);
    }

    private String rgbValue(int red, int green, int blue) {
        double sep = 42.5;
        boolean gray = false;

        while (true) {
            if (red < sep || green < sep || blue < sep) {
                gray = red < sep && green < sep && blue < sep;
                break;
            }
            sep += 42.5;
        }

        if (gray) {
            int num = 232 + Math.round((float) red + (float) green + (float) blue / 33);
            return ";5;" + num;
        } else {
            int val = (int) (6 * ((float) red / 256)) * 36
                    + (int) (6 * ((float) green / 256)) * 6
                    + (int) (6 * ((float) blue / 256));
            return ";5;" + (val + 16);
        }
    }

    private static boolean detectAnsiSupport() {
        String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        if (os.contains("windows")) {
            // Windows Terminal sets WT_SESSION; ConEmu sets ConEmuANSI; Git Bash / MSYS2 set TERM_PROGRAM
            return System.getenv("WT_SESSION") != null
                || "ON".equalsIgnoreCase(System.getenv("ConEmuANSI"))
                || System.getenv("TERM_PROGRAM") != null;
        }
        String term = System.getenv("TERM");
        return term != null && !term.equals("dumb");
    }
}
