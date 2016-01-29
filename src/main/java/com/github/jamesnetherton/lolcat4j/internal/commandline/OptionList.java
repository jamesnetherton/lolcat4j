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

import java.util.ArrayList;
import java.util.List;

class OptionList {
    private List<Option> options = new ArrayList<>();

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
