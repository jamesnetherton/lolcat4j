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
