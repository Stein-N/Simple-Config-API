package net.xstopho.simpleconfig.values;

import java.util.function.Predicate;

public class StringConfigValue extends ConfigValue<String> {

    private final int min, max;

    public StringConfigValue(String defaultValue, int min, int max, String comment) {
        super(defaultValue, comment);
        this.min = min;
        this.max = max;
    }

    public StringConfigValue(String defaultValue, String comment) {
        this(defaultValue, 0, 0, comment);
    }

    @Override
    public String getRangedComment() {
        if (isRanged()) return " Allowed Length: " + this.min + " ~ " + this.max + " chars.";
        else return null;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isConvertible = (o) -> o instanceof String;

        if (isRanged() && isConvertible.test(value))
            return value.toString().length() >= this.min && value.toString().length() <= this.max;
        else return !isRanged() && isConvertible.test(value);
    }

    @Override
    public boolean isRanged() {
        return !(min == 0 && max == 0);
    }
}
