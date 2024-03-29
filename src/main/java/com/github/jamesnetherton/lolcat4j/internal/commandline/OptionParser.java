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

import com.github.jamesnetherton.lolcat4j.Lol;

import java.io.File;

public class OptionParser {
    private OptionList optionList = new DefaultOptionList();
    private MessageWriter messageWriter = new TerminatingLolCatMessageWriter();

    public Lol parse(String... args) throws OptionParseException, UnknownOptionException {
        OptionList parsedOptionList = parseOptions(args);

        if (parsedOptionList.containsOption("-h") || parsedOptionList.containsOption("--help")) {
            messageWriter.write(MessageGenerator.generateUsageMessage(optionList));
            return null;
        }

        if (parsedOptionList.containsOption("-v") || parsedOptionList.containsOption("--version")) {
            messageWriter.write(MessageGenerator.generateVersionMessage());
            return null;
        }

        Lol.LolCatBuilder builder = Lol.builder();
        for (Option option : parsedOptionList.getOptions()) {
            switch (option.getName()) {
                case "animate":
                    builder.animate();
                    break;
                case "duration":
                    builder.duration(parseIntegerValue(option));
                    break;
                case "file":
                    builder.file(new File(option.getValue()));
                    break;
                case "freq":
                    builder.frequency(parseDoubleValue(option));
                    break;
                case "seed":
                    builder.seed(parseIntegerValue(option));
                    break;
                case "speed":
                    builder.speed(parseDoubleValue(option));
                    break;
                case "spread":
                    builder.spread(parseDoubleValue(option));
                    break;
            }
        }

        Lol lol = builder.build();
        lol.setInteractive(true);
        return lol;
    }

    private OptionList parseOptions(String... args) throws UnknownOptionException {
        OptionList parsedOptions = new OptionList();
        String lastArg = "";

        for (String arg : args) {
            if (arg.contains("=")) {
                String[] segments = arg.split("=");
                String option = segments[0].replace("--", "");
                String value = segments[1].trim();

                Option options = optionList.findOption(option);
                options.setValue(value);
                parsedOptions.addToOptions(options);
            } else {
                if (arg.startsWith("-") || (arg.startsWith("--") && !arg.contains("="))) {
                    if (optionList.findOption(arg).isValueRequired()) {
                        lastArg = arg;
                    } else {
                        Option options = optionList.findOption(arg);
                        parsedOptions.addToOptions(options);
                    }
                } else {
                    if (lastArg.startsWith("-")) {
                        Option option = optionList.findOption(lastArg);
                        option.setValue(arg);
                        parsedOptions.addToOptions(option);
                        lastArg = "";
                    } else {
                        if (arg.trim().length() > 0) {
                            Option option = Option.builder()
                                .builderMethod("file")
                                .name("file")
                                .shortOption("-i")
                                .build();
                            option.setValue(arg);
                            parsedOptions.addToOptions(option);
                        }
                    }
                }
            }
        }
        return parsedOptions;
    }

    public void setMessageWriter(MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    public void setOptionList(OptionList optionList) {
        this.optionList = optionList;
    }

    private double parseDoubleValue(Option option) throws OptionParseException {
        try {
            return Double.parseDouble(option.getValue());
        } catch (NumberFormatException e) {
            throw new OptionParseException("Error: option " + option.getName() + " needs a double value");
        }
    }

    private int parseIntegerValue(Option option) throws OptionParseException {
        try {
            return Integer.parseInt(option.getValue());
        } catch (NumberFormatException e) {
            throw new OptionParseException("Error: option " + option.getName() + " needs a integer value");
        }
    }
}
