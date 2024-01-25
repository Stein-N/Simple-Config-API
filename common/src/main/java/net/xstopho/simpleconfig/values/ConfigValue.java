package net.xstopho.simpleconfig.values;

public abstract class ConfigValue<T> implements IConfigValue<T> {

    public T defaultValue;
    public final String comment;

    public ConfigValue(T defaultValue, String comment) {
        this.defaultValue = defaultValue;
        this.comment = comment;
    }

    @Override
    public T get() {
        return defaultValue;
    }

    public String getComment() {
        return comment;
    }

    public boolean hasComment() {
        return comment != null;
    }
}
