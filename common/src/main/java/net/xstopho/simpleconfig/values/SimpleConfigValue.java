package net.xstopho.simpleconfig.values;

public interface SimpleConfigValue<T, S> {

    T defaultValue();
    String getComment();
    String getRangedComment();

    boolean validValue(T value);

    S serialize(T value);
    T deserialize(S serialized);
}
