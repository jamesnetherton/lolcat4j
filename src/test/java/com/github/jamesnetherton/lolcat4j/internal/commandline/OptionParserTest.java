package com.github.jamesnetherton.lolcat4j.internal.commandline;

import com.github.jamesnetherton.lolcat4j.Lol;
import com.github.jamesnetherton.lolcat4j.LolCatConstants;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OptionParserTest {

    @Test
    public void testDefaultOptionValuesForEmptyArgs() throws Exception {
        Lol lol = new OptionParser().parse("");
        assertFalse(lol.isAnimate());
        assertEquals(LolCatConstants.DEFAULT_VALUE_DURATION, lol.getDuration());
        assertEquals(LolCatConstants.DEFAULT_VALUE_FREQUENCY, lol.getFrequency());
        assertEquals(LolCatConstants.DEFAULT_VALUE_SEED, lol.getSeed());
        assertEquals(LolCatConstants.DEFAULT_VALUE_SPEED, lol.getSpeed());
        assertEquals(LolCatConstants.DEFAULT_VALUE_SPREAD, lol.getSpread());
    }

    @Test
    public void testShortOptionArgList() throws Exception {
        String[] options = new OptionListBuilder()
            .addOption(CommandLineConstants.OPTION_SPREAD, "5.0")
            .addOption(CommandLineConstants.OPTION_FREQUENCY, "1.0")
            .addOption(CommandLineConstants.OPTION_SEED, "1")
            .addOption(CommandLineConstants.OPTION_ANIMATE)
            .addOption(CommandLineConstants.OPTION_DURATION, "20")
            .addOption(CommandLineConstants.OPTION_SPEED, "30.0")
            .build();

        Lol lol = new OptionParser().parse(options);
        assertTrue(lol.isAnimate());
        assertEquals(20, lol.getDuration());
        assertEquals(1.0, lol.getFrequency());
        assertEquals(1, lol.getSeed());
        assertEquals(30.0, lol.getSpeed());
        assertEquals(5.0, lol.getSpread());
    }

    @Test
    public void testLongOptionArgList() throws Exception {
        String[] options = new OptionListBuilder()
            .withPrefix()
            .addOption("spread", "5.0")
            .addOption("freq", "1.0")
            .addOption("seed", "1")
            .addOption("animate")
            .addOption("duration", "20")
            .addOption("speed", "30.0")
            .build();

        Lol lol = new OptionParser().parse(options);
        assertTrue(lol.isAnimate());
        assertEquals(20, lol.getDuration());
        assertEquals(1.0, lol.getFrequency());
        assertEquals(1, lol.getSeed());
        assertEquals(30.0, lol.getSpeed());
        assertEquals(5.0, lol.getSpread());
    }

    @Test
    public void testMixedOptionArgList() throws Exception {
        String[] options = new OptionListBuilder()
            .addOption(CommandLineConstants.OPTION_SPREAD, "5.0")
            .withPrefix()
            .addOption(CommandLineConstants.OPTION_SPREAD, "10.0")
            .build();

        Lol lol = new OptionParser().parse(options);
        assertEquals(10.0, lol.getSpread());
    }

    @Test
    public void testUnknownOption() throws Exception {
        String[] options = new OptionListBuilder()
            .addOption("-Z", "lolz-arg")
            .build();

        try {
            new OptionParser().parse(options);
            fail("Expected OptionParseException");
        } catch (UnknownOptionException e) {
            // Expected
        }
    }

    @Test
    public void testFileOption() throws Exception {
        String[] options = new OptionListBuilder()
            .addOption("myfile.txt")
            .build();

        Lol lol = new OptionParser().parse(options);
        assertEquals(1, lol.getFiles().size());
        assertEquals("myfile.txt", lol.getFiles().get(0).getName());
    }

    @Test
    public void testMultipleFileOptions() throws Exception {
        String[] options = new OptionListBuilder()
            .addOption("myfile.txt")
            .addOption("someOtherFile.txt")
            .addOption("lolFile.txt")
            .build();

        Lol lol = new OptionParser().parse(options);
        assertEquals(3, lol.getFiles().size());
        assertEquals("myfile.txt", lol.getFiles().get(0).getName());
    }

    @Test
    public void testHelpOption() throws Exception {
        String[] options = new OptionListBuilder()
            .addOption("-h")
            .build();

        OptionList optionList = new OptionList();
        optionList.addToOptions(Option.builder()
                .shortOption("-h")
                .valueRequired(false)
                .name("help")
                .description("help message")
                .build()
        );

        LoggingMessageWriter messageWriter = new LoggingMessageWriter();

        OptionParser parser = new OptionParser();
        parser.setOptionList(optionList);
        parser.setMessageWriter(messageWriter);
        parser.parse(options);

        assertEquals(MessageGenerator.generateUsageMessage(optionList), messageWriter.getMessage());
    }

    @Test
    public void testVersionOption() throws Exception {
        String[] options = new OptionListBuilder()
            .addOption("-v")
            .build();

        OptionList optionList = new OptionList();
        optionList.addToOptions(Option.builder()
                .shortOption("-v")
                .valueRequired(false)
                .name("version")
                .description("version message")
                .build()
        );

        LoggingMessageWriter messageWriter = new LoggingMessageWriter();

        OptionParser parser = new OptionParser();
        parser.setOptionList(optionList);
        parser.setMessageWriter(messageWriter);
        parser.parse(options);

        assertEquals(MessageGenerator.generateVersionMessage(), messageWriter.getMessage());
    }

    private static class OptionListBuilder {
        private final List<String> options = new ArrayList<>();
        private boolean prefix;

        public String[] build() {
            return options.toArray(new String[0]);
        }

        public OptionListBuilder withPrefix() {
            prefix = true;
            return this;
        }

        public OptionListBuilder addOption(String name) {
            if (prefix) {
                options.add("--" + name);
            } else {
                options.add(name);
            }
            return this;
        }

        public OptionListBuilder addOption(String name, String value) {
            if (prefix) {
                options.add("--" + name + "=" + value);
            } else {
                options.add(name);
                options.add(value);
            }
            return this;
        }
    }
}
