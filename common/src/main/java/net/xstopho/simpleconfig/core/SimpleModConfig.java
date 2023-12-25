package net.xstopho.simpleconfig.core;

import net.xstopho.simpleconfig.SimpleConfigConstants;
import net.xstopho.simpleconfig.values.SimpleConfigValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SimpleModConfig<S> {

    private final String modid;
    private final List<Entry<?, S>> entries;
    private final SimpleConfigFile<S> configFile;
    private final List<Pair<String[], String>> categoryComments;

    public SimpleModConfig(String modid, SimpleConfigFile<S> configFile, List<Entry<?, S>> entries, List<Pair<String[], String>> categoryComments) {
        this.modid = modid;
        this.configFile = configFile;
        this.entries = Collections.unmodifiableList(entries);
        this.categoryComments = Collections.unmodifiableList(categoryComments);

    }

    public void init() {
        this.configFile.readFile();
        this.entries.forEach(this::readConfigValue);
        this.configFile.clearValues();

        for (Map.Entry<String[], String> comment : this.categoryComments) {
            this.configFile.setComment(comment.getKey(), comment.getValue());
        }

        for (Entry<?, S> entry : this.entries) {
            this.configFile.setComment(entry.path, entry.configValue.getComment());
            this.configFile.setRangedComment(entry.path, entry.configValue.getRangedComment());
            this.writeConfigValue(entry);
        }

        this.configFile.writeFile();
        this.configFile.startTrackingFile();

        this.entries.forEach(entry -> entry.initialised = true);
    }

    private <T> void writeConfigValue(Entry<T, S> entry) {
        S serialized = entry.configValue.serialize(entry.value);
        if (serialized == null) SimpleConfigConstants.LOG.warn("Failed to serialize value '{}' for path '{}' in config from '{}'!", entry.value, String.join(".", entry.path), this.modid);
        else this.configFile.setValue(entry.path, serialized);
    }

    private <T> void readConfigValue(Entry<T, S> entry) {
        S serialized = this.configFile.getValue(entry.path);
        if (serialized == null) {
            entry.value = entry.configValue.defaultValue();
        }
        else {
            T value = entry.configValue.deserialize(serialized);
            if (value == null || !entry.configValue.validValue(value)) entry.value = entry.configValue.defaultValue();
            else entry.value = value;
        }
    }

    protected static class Entry<T, S> {
        protected final String[] path;
        protected final String combinedPath;
        protected final SimpleConfigValue<T, S> configValue;
        private boolean initialised;
        private T value;

        Entry(String[] path, SimpleConfigValue<T, S> configValue) {
            this.path = path;
            this.combinedPath = String.join(".", path);
            this.configValue = configValue;
        }

        public T getValue() {
            if (!this.initialised) throw new IllegalStateException("Config isn't loaded yet.");
            return this.value;
        }
    }
}
