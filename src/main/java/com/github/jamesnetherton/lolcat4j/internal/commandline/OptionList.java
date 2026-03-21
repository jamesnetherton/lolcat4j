package com.github.jamesnetherton.lolcat4j.internal.commandline;

import java.util.ArrayList;
import java.util.List;

class OptionList {
    private final List<Option> options = new ArrayList<>();

    public void addToOptions(Option option) {
        options.add(option);
    }

    public Option findOption(String optionFlag) throws UnknownOptionException {
        for (Option option : options) {
            if (isValidMatch(option, optionFlag)) {
                return option;
            }
        }
        throw new UnknownOptionException("Unknown option: " + optionFlag);
    }

    public boolean containsOption(String optionFlag) {
        for (Option option : options) {
            if (isValidMatch(option, optionFlag)) {
                return true;
            }
        }
        return false;
    }

    public List<Option> getOptions() {
        return options;
    }

    private boolean isValidMatch(Option option, String optionFlag) {
        return option.getShortOption().equals(optionFlag) || option.getLongOption().equals(optionFlag) || option.getName().equals(optionFlag);
    }
}
