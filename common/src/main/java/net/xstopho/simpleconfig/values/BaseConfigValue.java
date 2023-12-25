package net.xstopho.simpleconfig.values;

public abstract class BaseConfigValue<T, S> implements SimpleConfigValue<T, S> {

    protected final T defaultValue;
    protected final String comment;

    public BaseConfigValue(T defaultValue, String comment) {
        this.defaultValue = defaultValue;
        this.comment = comment;
    }

    @Override
    public T defaultValue() {
        return defaultValue;
    }

    @Override
    public String getComment() {
        return comment;
    }
}
