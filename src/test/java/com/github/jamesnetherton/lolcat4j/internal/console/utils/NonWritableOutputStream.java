package com.github.jamesnetherton.lolcat4j.internal.console.utils;

import java.io.IOException;
import java.io.OutputStream;

public class NonWritableOutputStream extends OutputStream {
    @Override
    public void write(int b) throws IOException {
        // Do nothing
    }
}
