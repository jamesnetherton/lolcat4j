package com.github.jamesnetherton.lolcat4j.internal.commandline;

public class UnknownOptionException extends Exception {
    public UnknownOptionException(String message) {
        super(message);
    }
}
