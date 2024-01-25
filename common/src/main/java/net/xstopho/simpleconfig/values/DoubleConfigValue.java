package net.xstopho.simpleconfig.values;

import java.util.function.Predicate;

public class DoubleConfigValue extends ConfigValue<Double> {

    private final double min, max;

    public DoubleConfigValue(double defaultValue, double min, double max, String comment) {
        super(defaultValue, comment);
        this.min = min;
        this.max = max;
    }

    public DoubleConfigValue(Double defaultValue, String comment) {
        this(defaultValue, 0.0, 0.0, comment);
    }

    @Override
    public String getRangedComment() {
        String rangedComment = "Range: " + this.min + " ~ " + this.max + " - Default: " + this.defaultValue;
        if (isRanged() && hasComment()) return getComment() + "\n " + rangedComment;
        if (isRanged()) return " " + rangedComment;
        else return null;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isConvertible = (o) -> o instanceof Double;

        if (isRanged() && isConvertible.test(value)) return (double) value >= min && (double) value <= max;
        else return !isRanged() && isConvertible.test(value);
    }

    @Override
    public boolean isRanged() {
        return !(min == 0.0 && max == 0.0);
    }
}
