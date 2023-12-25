package net.xstopho.simpleconfig.api;

import java.util.function.Supplier;

public interface ISimpleConfigBuilder {

    ISimpleConfigBuilder push(String category);
    ISimpleConfigBuilder pop();
    ISimpleConfigBuilder categoryComment(String comment);
    ISimpleConfigBuilder comment(String comment);

    Supplier<Boolean> define(String key, boolean defaultValue);
    Supplier<Integer> define(String key, int defaultValue);
    Supplier<Double> define(String key, double defaultValue);
    Supplier<String> define(String key, String defaultValue);

    Supplier<Integer> defineInRange(String key, int defaultValue, int min, int max);
    Supplier<Double> defineInRange(String key, double defaultValue, double min, double max);
    Supplier<String> defineInRange(String key, String defaultValue, int minLength, int maxLength);

    void build();
}
