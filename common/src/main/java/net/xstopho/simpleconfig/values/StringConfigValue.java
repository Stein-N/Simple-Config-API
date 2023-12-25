package net.xstopho.simpleconfig.values;

import net.xstopho.simpleconfig.toml.TomlElement;
import net.xstopho.simpleconfig.toml.TomlPrimitive;

public class StringConfigValue extends BaseConfigValue<String, TomlElement> {

    private final int minLength, maxLength;

    public StringConfigValue(String defaultValue, int minLength, int maxLength, String comment) {
        super(defaultValue, comment);
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public String getRangedComment() {
        if (minLength == 0 && maxLength == 0) return null;
        return "Allowed length: " + minLength + " ~ " + maxLength + " - Default length: " + defaultValue;
    }

    @Override
    public boolean validValue(String value) {
        if (minLength == 0 && maxLength == 0) return true;
        return value.length() >= minLength && value.length() <= maxLength;
    }

    @Override
    public TomlElement serialize(String value) {
        return TomlPrimitive.of(value);
    }

    @Override
    public String deserialize(TomlElement serialized) {
        return serialized.isString() ? serialized.getAsString() : null;
    }
}
