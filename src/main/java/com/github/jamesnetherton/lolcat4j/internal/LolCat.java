/*
 * #%L
 * lolcat4j
 * %%
 * Copyright (C) 2016 James Netherton
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
package com.github.jamesnetherton.lolcat4j.internal;

import com.github.jamesnetherton.lolcat4j.Lol;
import com.github.jamesnetherton.lolcat4j.internal.commandline.OptionParseException;
import com.github.jamesnetherton.lolcat4j.internal.commandline.OptionParser;
import com.github.jamesnetherton.lolcat4j.internal.commandline.UnknownOptionException;
import com.github.jamesnetherton.lolcat4j.internal.console.ConsoleReader;

public final class LolCat {

    public static void main(String... args) {
        try {
            final Lol lol = new OptionParser().parse(args);

            if (lol.getFiles().isEmpty()) {
                ConsoleReader consoleReader = new ConsoleReader(lol);
                consoleReader.read();
            } else {
                lol.cat();
            }
        } catch (OptionParseException | UnknownOptionException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
