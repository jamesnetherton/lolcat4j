package com.github.jamesnetherton.lolcat4j.internal.console.utils;

import java.io.OutputStream;
import java.io.PrintStream;

public class LoggingPrintStream extends PrintStream {
    private final StringBuilder loggedOutput = new StringBuilder();

    public LoggingPrintStream(OutputStream out) {
        super(out);
    }

    @Override
    public void println() {
        loggedOutput.append("\n");
    }

    @Override
    public void print(String s) {
        loggedOutput.append(s);
    }

    public String getLoggedOutput() {
        return loggedOutput.toString();
    }
}
