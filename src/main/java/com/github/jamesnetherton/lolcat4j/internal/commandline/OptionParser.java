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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class OptionParser {
    private OptionList optionList = new DefaultOptionList();
    private MessageOutputter messageOutputter = new TerminatingMessageOutputter();

    public Lol parse(String... args) throws OptionParseException, UnknownOptionException {
        OptionList parsedOptionList = parseOptions(args);

        if (parsedOptionList.containsOption("-h") || parsedOptionList.containsOption("--help")) {
            messageOutputter.output(MessageGenerator.generateUsageMessage(optionList));
            return null;
        }

        if (parsedOptionList.containsOption("-v") || parsedOptionList.containsOption("--version")) {
            messageOutputter.output(MessageGenerator.generateVersionMessage());
            return null;
        }

        Lol.LolCatBuilder builder = Lol.builder();
        for (Option option : parsedOptionList.getOptions()) {
            Method method = ReflectionUtils.findMethod(Lol.LolCatBuilder.class, option.getBuilderMethod());
            if (method != null) {
                try {
                    if (option.isValueRequired()) {
                        Parameter[] parameters = method.getParameters();
                        Class<?> type = parameters[0].getType();
                        if (type == Integer.class) {
                            method.invoke(builder, parseIntegerValue(option.getName(), option.getValue()));
                        } else if (type == Double.class) {
                            method.invoke(builder, parseDoubleValue(option.getName(), option.getValue()));
                        } else if (type == File.class) {
                            method.invoke(builder, new File(option.getValue()));
                        } else {
                            method.invoke(builder, option.getValue());
                        }
                    } else {
                        method.invoke(builder);
                    }
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new IllegalStateException("Unable to handle option: " + option.toString(), e);
                }
            } else {
                throw new IllegalStateException("Unable to handle option: " + option.toString());
            }
        }

        return builder.build();
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
                    if (lastArg.length() > 0 && lastArg.startsWith("-")) {
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

    public void setMessageOutputter(MessageOutputter messageOutputter) {
        this.messageOutputter = messageOutputter;
    }

    public void setOptionList(OptionList optionList) {
        this.optionList = optionList;
    }

    private double parseDoubleValue(String optionName, String doubleValue) throws OptionParseException {
        try {
            return Double.parseDouble(doubleValue);
        } catch (NumberFormatException e) {
            throw new OptionParseException("Error: option " + optionName + " needs a double value");
        }
    }

    private int parseIntegerValue(String optionName, String integerValue) throws OptionParseException {
        try {
            return Integer.parseInt(integerValue);
        } catch (NumberFormatException e) {
            throw new OptionParseException("Error: option " + optionName + " needs a integer value");
        }
    }
}
