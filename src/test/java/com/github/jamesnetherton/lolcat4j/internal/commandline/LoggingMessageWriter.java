package com.github.jamesnetherton.lolcat4j.internal.commandline;

public class LoggingMessageWriter implements MessageWriter {
    private String message;

    @Override
    public void write(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
