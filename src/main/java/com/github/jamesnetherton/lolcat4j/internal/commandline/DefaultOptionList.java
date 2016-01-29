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

import com.github.jamesnetherton.lolcat4j.LolCatConstants;

final class DefaultOptionList extends OptionList {

    DefaultOptionList() {
        addToOptions(
            Option.builder()
                .shortOption(CommandLineConstants.OPTION_ANIMATE)
                .name("animate")
                .description("Enable psychedelics")
                .valueRequired(false)
                .builderMethod("animate")
                .build()
        );

        addToOptions(
            Option.builder()
                .shortOption(CommandLineConstants.OPTION_DURATION)
                .name("duration")
                .description("Animation duration")
                .defaultValue(String.valueOf(LolCatConstants.DEFAULT_VALUE_DURATION))
                .builderMethod("duration")
                .build()
        );

        addToOptions(
            Option.builder()
                .shortOption(CommandLineConstants.OPTION_FREQUENCY)
                .name("freq")
                .description("Rainbow frequency")
                .defaultValue(String.valueOf(LolCatConstants.DEFAULT_VALUE_FREQUENCY))
                .builderMethod("frequency")
                .build()
        );

        addToOptions(
            Option.builder()
                .shortOption(CommandLineConstants.OPTION_HELP)
                .name("help")
                .description("Show this message")
                .valueRequired(false)
                .build()
        );

        addToOptions(
            Option.builder()
                .shortOption(CommandLineConstants.OPTION_SEED)
                .name("seed")
                .description("Rainbow seed, 0 = random")
                .defaultValue(String.valueOf(LolCatConstants.DEFAULT_VALUE_SEED))
                .builderMethod("seed")
                .build()
        );

        addToOptions(
            Option.builder()
                .shortOption(CommandLineConstants.OPTION_SPEED)
                .name("speed")
                .description("Animation speed")
                .defaultValue(String.valueOf(LolCatConstants.DEFAULT_VALUE_SPEED))
                .builderMethod("speed")
                .build()
        );

        addToOptions(
            Option.builder()
                .shortOption(CommandLineConstants.OPTION_SPREAD)
                .name("spread")
                .description("Rainbow spread")
                .defaultValue(String.valueOf(LolCatConstants.DEFAULT_VALUE_SPREAD))
                .builderMethod("spread")
                .build()
        );

        addToOptions(
            Option.builder()
                .shortOption(CommandLineConstants.OPTION_VERSION)
                .name("version")
                .description("Print version and exit")
                .valueRequired(false)
                .build()
        );
    }
}
