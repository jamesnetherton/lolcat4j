package com.github.jamesnetherton.lolcat4j.internal;

import com.github.jamesnetherton.lolcat4j.Lol;
import com.github.jamesnetherton.lolcat4j.internal.commandline.OptionParser;
import com.github.jamesnetherton.lolcat4j.internal.console.ConsoleReader;

public final class Main {

    public static void main(String... args) {
        try {
            final Lol lol = new OptionParser().parse(args);

            if (lol.getFiles().isEmpty() && System.in.available() == 0) {
                ConsoleReader consoleReader = new ConsoleReader(lol);
                consoleReader.read();
            } else {
                lol.cat();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
