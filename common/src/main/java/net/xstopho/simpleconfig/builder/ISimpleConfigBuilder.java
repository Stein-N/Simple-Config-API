package net.xstopho.simpleconfig.builder;

import net.xstopho.simpleconfig.config.ConfigEntry;

import java.util.Map;
import java.util.function.Supplier;

public interface ISimpleConfigBuilder {

    Map<String, ConfigEntry<?>> getEntries();
    Map<String, String> getCategoryComments();

    ISimpleConfigBuilder push(String category);
    ISimpleConfigBuilder pop();
    ISimpleConfigBuilder comment(String comment);

    Supplier<Boolean> define(String key, boolean defaultValue);

    Supplier<Integer> define(String key, int defaultValue);
    Supplier<Integer> defineInRange(String key, int defaultValue, int min, int max);

    Supplier<Double> define(String key, double defaulValue);
    Supplier<Double> defineInRange(String key, double defaulValue, double min, double max);

    Supplier<String> define(String key, String defaultValue);
    Supplier<String> defineInRange(String key, String defaultValue, int minLength, int maxLength);
}
