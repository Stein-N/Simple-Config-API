package net.xstopho.simpleconfig.builder;

import net.xstopho.simpleconfig.config.ConfigEntry;
import net.xstopho.simpleconfig.values.BooleanConfigValue;
import net.xstopho.simpleconfig.values.DoubleConfigValue;
import net.xstopho.simpleconfig.values.IntegerConfigValue;
import net.xstopho.simpleconfig.values.StringConfigValue;

import java.util.Map;
import java.util.function.Supplier;

public class SimpleConfigBuilder extends SimpleBaseBuilder {

    @Override
    public Map<String, ConfigEntry<?>> getEntries() {
        return this.entries;
    }

    @Override
    public Map<String, String> getCategoryComments() {
        return this.categoryComments;
    }

    @Override
    public Supplier<Boolean> define(String key, boolean defaultValue) {
        return addEntry(createKey(key), new BooleanConfigValue(true, this.comment));
    }

    @Override
    public Supplier<Integer> define(String key, int defaultValue) {
        return addEntry(createKey(key), new IntegerConfigValue(defaultValue, this.comment));
    }

    @Override
    public Supplier<Integer> defineInRange(String key, int defaultValue, int min, int max) {
        return addEntry(createKey(key), new IntegerConfigValue(defaultValue, min, max, this.comment));
    }

    @Override
    public Supplier<Double> define(String key, double defaultValue) {
        return addEntry(createKey(key), new DoubleConfigValue(defaultValue, this.comment));
    }

    @Override
    public Supplier<Double> defineInRange(String key, double defaultValue, double min, double max) {
        return addEntry(createKey(key), new DoubleConfigValue(defaultValue, min, max, this.comment));
    }

    @Override
    public Supplier<String> define(String key, String defaultValue) {
        return addEntry(createKey(key), new StringConfigValue(defaultValue, this.comment));
    }

    @Override
    public Supplier<String> defineInRange(String key, String defaultValue, int minLength, int maxLength) {
        return addEntry(createKey(key), new StringConfigValue(defaultValue, minLength, maxLength, this.comment));
    }
}
