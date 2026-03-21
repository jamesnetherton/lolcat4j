package com.github.jamesnetherton.lolcat4j.internal.commandline;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageGeneratorTest {

    @Test
    public void testUsageMessage() throws Exception {
        File file = new File(getClass().getResource("usage.txt").getFile());
        StringBuilder builder = new StringBuilder();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                builder.append(line).append(System.lineSeparator());
            }
        }

        assertEquals(builder.toString(), MessageGenerator.generateUsageMessage(new DefaultOptionList()));
    }

    @Test
    public void testVersionMessage() throws Exception {
        InputStream resource = getClass().getResourceAsStream("version");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource));
        String expectedVersion = br.readLine().trim();

        assertEquals("Version: " + expectedVersion, MessageGenerator.generateVersionMessage());
    }
}
