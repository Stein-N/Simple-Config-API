package net.xstopho.simpleconfig.builder;

import net.xstopho.simpleconfig.config.ConfigEntry;
import net.xstopho.simpleconfig.values.ConfigValue;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class SimpleBaseBuilder implements ISimpleConfigBuilder {

    public String category = null, comment;
    public final Map<String, ConfigEntry<?>> entries = new LinkedHashMap<>();
    public final Map<String, String> categoryComments = new LinkedHashMap<>();

    @Override
    public ISimpleConfigBuilder push(String category) {
        if (this.category == null) {
            this.categoryComments.put(category, this.comment);
            this.category = category;
            this.comment = null;
        } else throw new IllegalArgumentException("Category '" + this.category + "' is already set. Use pop() to set a new Category!");
        return this;
    }

    @Override
    public ISimpleConfigBuilder pop() {
        if (this.category != null) this.category = null;
        else throw new IllegalArgumentException("There is no Category to remove!");
        return this;
    }

    @Override
    public ISimpleConfigBuilder comment(String comment) {
        this.comment = " " + comment;
        return this;
    }

    public <T> Supplier<T> addEntry(String path, ConfigValue<T> configValue) {
        if (this.entries.containsKey(path)) throw new IllegalStateException("Key '" + path + "' is already defined!");

        ConfigEntry<T> entry = new ConfigEntry<>(path, configValue);
        this.entries.put(path, entry);
        this.comment = null;
        return entry::getValue;
    }

    public String createKey(String key) {
        if (category != null) return category + "." + key;
        return key;
    }
}
