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
        builder.append(".jar [OPTION]... [FILE]...");
        builder.append(System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append("Concatenate FILE(s), or standard input, to standard output.");
        builder.append(System.lineSeparator());
        builder.append("With no FILE, read standard input.");
        builder.append(System.lineSeparator());
        builder.append(System.lineSeparator());
        for (Option option : optionList.getOptions()) {
            builder.append(String.format("%-2s, --%-10s %-50s" + System.lineSeparator(), option.getShortOption(), option, option.getDescription()));
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
            try (BufferedReader br = new BufferedReader(new InputStreamReader(resource))) {
                version = br.readLine().trim();
            } catch (IOException e) {
                // Ignore
            }
        }
        return version;
    }
}
