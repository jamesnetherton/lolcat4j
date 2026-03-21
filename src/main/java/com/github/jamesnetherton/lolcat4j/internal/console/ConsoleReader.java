package com.github.jamesnetherton.lolcat4j.internal.console;

import com.github.jamesnetherton.lolcat4j.Lol;

import java.io.Console;

public class ConsoleReader {
    private final Lol lol;

    public ConsoleReader(Lol lol) {
        this.lol = lol;
    }

    public void read() {
        Console console = System.console();
        if (console != null) {
            while (true) {
                String text = console.readLine();
                lol.setText(text);
                lol.cat();
            }
        } else {
            System.err.println("Error: No console available");
            System.exit(1);
        }
    }
}
