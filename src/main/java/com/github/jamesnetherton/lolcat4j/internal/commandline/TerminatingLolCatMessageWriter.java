package com.github.jamesnetherton.lolcat4j.internal.commandline;

import com.github.jamesnetherton.lolcat4j.Lol;
import com.github.jamesnetherton.lolcat4j.LolCatConstants;

class TerminatingLolCatMessageWriter implements MessageWriter {
    @Override
    public void write(String message) {
        Lol lol = Lol.builder()
            .seed(LolCatConstants.DEFAULT_VALUE_SEED)
            .frequency(LolCatConstants.DEFAULT_VALUE_FREQUENCY)
            .spread(LolCatConstants.DEFAULT_VALUE_SPREAD)
            .text(message)
            .build();
        lol.cat();
        System.exit(0);
    }
}
