package com.github.jamesnetherton.lolcat4j.internal.commandline;

class Option {
    private String shortOption;
    private String name;
    private String description;
    private String defaultValue;
    private String value;
    private String builderMethod;
    private boolean valueRequired = true;

    private Option() {
    }

    public String getShortOption() {
        return shortOption;
    }

    public void setShortOption(String shortOption) {
        this.shortOption = shortOption;
    }

    public String getLongOption() {
        return "--" + this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return defaultValue != null ? description + " (default: " + defaultValue + ")" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return value == null ? this.defaultValue : this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getBuilderMethod() {
        return builderMethod;
    }

    public void setBuilderMethod(String builderMethod) {
        this.builderMethod = builderMethod;
    }

    public boolean isValueRequired() {
        return valueRequired;
    }

    public void setValueRequired(boolean valueRequired) {
        this.valueRequired = valueRequired;
    }

    @Override
    public String toString() {
        return this.name;
    }

    static OptionsBuilder builder() {
        return new OptionsBuilder();
    }

    static final class OptionsBuilder {
        private final Option option = new Option();

        public OptionsBuilder shortOption(String shortOption) {
            option.setShortOption(shortOption);
            return this;
        }

        public OptionsBuilder name(String name) {
            option.setName(name);
            return this;
        }

        public OptionsBuilder description(String description) {
            option.setDescription(description);
            return this;
        }

        public OptionsBuilder defaultValue(String defaultValue) {
            option.setDefaultValue(defaultValue);
            return this;
        }

        public OptionsBuilder builderMethod(String methodName) {
            option.setBuilderMethod(methodName);
            return this;
        }

        public OptionsBuilder valueRequired(boolean required) {
            option.setValueRequired(required);
            return this;
        }

        public Option build() {
            return option;
        }
    }
}
