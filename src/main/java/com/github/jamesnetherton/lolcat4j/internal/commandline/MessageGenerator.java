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
package com.github.jamesnetherton.lolcat4j.internal.commandline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class MessageGenerator {

    public static String generateUsageMessage(OptionList optionList) {
        StringBuilder builder = new StringBuilder();
        builder.append("Usage: java -jar lolcat4j-");
        builder.append(getVersion());
        builder.append(".jar [OPTION]... [FILE]...\n\n");
        builder.append("Concatenate FILE(s), or standard input, to standard output.\n");
        builder.append("With no FILE, read standard input.\n\n");
        for (Option option : optionList.getOptions()) {
            builder.append(String.format("%-2s, --%-10s %-50s\n", option.getShortOption(), option.toString(), option.getDescription()));
        }
        return builder.toString();
    }

    public static String generateVersionMessage() {
        String version = getVersion();
        return String.format("Version: %s", version);
    }

    private static String getVersion() {
        String version = "Unknown";
        ClassLoader classLoader = MessageGenerator.class.getClassLoader();

        InputStream resource = classLoader.getResourceAsStream("META-INF/version");
        if (resource != null) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(resource));
                version = br.readLine().trim();
            } catch (IOException e) {
                // Ignore
            }
        }
        return version;
    }
}
