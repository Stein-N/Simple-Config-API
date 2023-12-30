package net.xstopho.simpleconfig.values;

import java.util.function.Predicate;

public class IntegerConfigValue extends ConfigValue<Integer> {

    private final int min, max;

    public IntegerConfigValue(int defaultValue, int min, int max, String comment) {
        super(defaultValue, comment);
        this.min = min;
        this.max = max;
    }

    public IntegerConfigValue(int defaultValue, String comment) {
        this(defaultValue, 0, 0, comment);
    }

    @Override
    public String getRangedComment() {
        if (isRanged()) return " Range: " + this.min + " ~ " + this.max + " - Default: " + this.defaultValue;
        else return null;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isConvertible = (o) -> o instanceof Integer;

        if (isRanged() && isConvertible.test(value)) return (int) value >= this.min && (int) value <= this.max;
        else return !isRanged() && isConvertible.test(value);
    }

    @Override
    public boolean isRanged() {
        return min != 0 && max != 0;
    }
}
