package net.xstopho.simpleconfig.core;

import net.xstopho.simpleconfig.toml.TomlElement;
import net.xstopho.simpleconfig.values.BooleanConfigValue;
import net.xstopho.simpleconfig.values.DoubleConfigValue;
import net.xstopho.simpleconfig.values.IntegerConfigValue;
import net.xstopho.simpleconfig.values.StringConfigValue;

import java.io.File;
import java.util.function.Supplier;

public class SimpleConfigBuilder extends SimpleBaseConfigBuilder<TomlElement> {

    public SimpleConfigBuilder(String modid, String fileName) {
        super(modid, fileName);
    }

    @Override
    protected SimpleConfigFile<TomlElement> createConfig(File file) {
        return new ConfigFile(file);
    }

    @Override
    public Supplier<Boolean> define(String key, boolean defaultValue) {
        validateKey(key);
        BooleanConfigValue value = new BooleanConfigValue(defaultValue, this.comment);
        this.resetState();
        return this.addEntry(this.getPath(key), value);
    }

    @Override
    public Supplier<Integer> define(String key, int defaultValue) {
        validateKey(key);
        IntegerConfigValue value = new IntegerConfigValue(defaultValue, 0, 0, this.comment);
        this.resetState();
        return this.addEntry(this.getPath(key), value);
    }

    @Override
    public Supplier<Double> define(String key, double defaultValue) {
        validateKey(key);
        DoubleConfigValue value = new DoubleConfigValue(defaultValue, 0, 0, this.comment);
        this.resetState();
        return this.addEntry(this.getPath(key), value);
    }

    @Override
    public Supplier<String> define(String key, String defaultValue) {
        validateKey(key);
        StringConfigValue value = new StringConfigValue(defaultValue, 0, 0, this.comment);
        this.resetState();
        return this.addEntry(this.getPath(key), value);
    }

    @Override
    public Supplier<Integer> defineInRange(String key, int defaultValue, int min, int max) {
        validateKey(key);
        IntegerConfigValue value = new IntegerConfigValue(defaultValue, min, max, this.comment);
        this.resetState();
        return this.addEntry(this.getPath(key), value);
    }

    @Override
    public Supplier<Double> defineInRange(String key, double defaultValue, double min, double max) {
        validateKey(key);
        DoubleConfigValue value = new DoubleConfigValue(defaultValue, min, max, this.comment);
        this.resetState();
        return this.addEntry(this.getPath(key), value);
    }

    @Override
    public Supplier<String> defineInRange(String key, String defaultValue, int minLength, int maxLength) {
        validateKey(key);
        StringConfigValue value = new StringConfigValue(defaultValue, minLength, maxLength, this.comment);
        this.resetState();
        return this.addEntry(this.getPath(key), value);
    }

    @Override
    protected String[] getIllegalCharacters() {
        return new String[]{"[", "^", "\\", "\\/", ":", "*", "?", "\"", "<", ">", "|", "]"};
    }

    private void validateKey(String key) {
        if (key == null) throw new IllegalArgumentException("Key can't be null!");
        if (key.isEmpty()) throw new IllegalArgumentException("Key can't be empty!");
        for (String character : getIllegalCharacters()) {
            if (key.contains(character)) throw new IllegalArgumentException(key + " contains illegal characters, remove '" + character + "'");
        }
    }
}
