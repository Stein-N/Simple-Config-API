package net.xstopho.simpleconfig.core;

import net.xstopho.simpleconfig.platform.Services;
import net.xstopho.simpleconfig.values.SimpleConfigValue;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.*;
import java.util.function.Supplier;

public abstract class ConfigBuilder<S> {

    private final String modid, fileName;

    private final Map<String, SimpleModConfig.Entry<?, S>> entries = new LinkedHashMap<>();
    private final Map<List<String>, String> categoryComments = new LinkedHashMap<>();

    private boolean alreadyBuild = false;

    public ConfigBuilder(String modid, String fileName) {
        if (modid == null || modid.isEmpty()) throw new IllegalArgumentException("Modid can not be null!");
        this.modid = modid;
        this.fileName = fileName + ".toml";
    }

    protected abstract SimpleConfigFile<S> createConfig(File file);

    protected <T> Supplier<T> addEntry(String[] path, SimpleConfigValue<T, S> configValue) {
        if (this.entries.containsKey(join(path))) throw new IllegalStateException("Value " + join(path) + " is already defined!");

        SimpleModConfig.Entry<T, S> value = new SimpleModConfig.Entry<>(path, configValue);
        this.entries.put(join(path), value);
        return value::getValue;
    }

    protected void addCategoryComment(String[] path, String comment) {
        List<String> key = Arrays.asList(path);
        if (this.categoryComments.containsKey(key)) throw new IllegalStateException("Comment for " + join(path) + " is already defined!");

        this.categoryComments.put(key, comment);
    }

    public void build() {
        if (this.alreadyBuild) throw new IllegalStateException("Config has already been build!");
        this.alreadyBuild = true;

        File file = new File(Services.INSTANCE.getConfigDir() + "/" + this.fileName);
        SimpleConfigFile<S> configFile = this.createConfig(file);

        List<Pair<String[], String>> categoryComments = this.categoryComments.entrySet().stream()
                .map(entry -> Pair.of(entry.getKey().toArray(new String[0]), entry.getValue()))
                .toList();

        SimpleModConfig<S> config = new SimpleModConfig<>(this.modid, configFile, new ArrayList<>(this.entries.values()), categoryComments);
        config.init();
    }

    private String join(String[] path) {
        return String.join(".", path);
    }
}