package net.xstopho.simpleconfig.values;

import java.util.function.Predicate;

public class BooleanConfigValue extends ConfigValue<Boolean> {
    public BooleanConfigValue(Boolean defaultValue, String comment) {
        super(defaultValue, comment);
    }

    @Override
    public String getRangedComment() {
        return " Allowed: true - false   Default: " + this.defaultValue;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isConvertible = (o) -> o instanceof Boolean;
        return isConvertible.test(value);
    }

    @Override
    public boolean isRanged() {
        return false;
    }
}
