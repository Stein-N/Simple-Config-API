package net.xstopho.simpleconfig.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.io.ParsingException;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import net.xstopho.simpleconfig.SimpleConfigConstants;
import net.xstopho.simpleconfig.builder.ISimpleConfigBuilder;
import net.xstopho.simpleconfig.platform.Services;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SimpleModConfig {

    private final String modID;
    private final File file;
    private CommentedConfig config = CommentedConfig.inMemory();
    private List<ConfigEntry<?>> entries;

    public SimpleModConfig(String modID, String fileName, ISimpleConfigBuilder builder) {
        this.modID = modID;
        this.file = new File(Services.INSTANCE.getConfigDir() + "/" + fileName + ".toml");

        this.entries.addAll(builder.getEntries().values());

        this.readValuesFromTomlFile();

        this.entries.forEach(this::readConfigValue);

        //TODO: Works as intended by deleting not declared values, but seems sketchy
        this.config = CommentedConfig.inMemory();

        for (Map.Entry<String, String> categoryComment : builder.getCategoryComments().entrySet()) {
            config.setComment(categoryComment.getKey(), categoryComment.getValue());
        }

        this.entries.forEach(this::writeToConfig);

        this.writeValuesToTomlFile();

        this.entries.forEach(entry -> entry.loaded = true);
    }

    private void readValuesFromTomlFile() {
        if (!this.file.exists()) return;

        try(FileReader reader = new FileReader(this.file)) {
            this.config = new TomlParser().parse(reader);
        } catch(IOException | ParsingException e) {
            SimpleConfigConstants.LOG.error("Reading '{}' from Mod '{}' failed!\nMessage: {}", this.file.getName(), this.modID, e.getMessage());
        }
    }

    private void writeValuesToTomlFile() {
        TomlWriter writer = new TomlWriter();
        writer.write(this.config, this.file, WritingMode.REPLACE);
    }

    private <T> void readConfigValue(ConfigEntry<T> entry) {
        String path = entry.path;
        if (!this.config.contains(path)) entry.value = entry.configValue.get();
        else {
            T value = this.config.get(path);
            if (value != null && entry.configValue.validate(value)) entry.value = value;
            else {
                entry.value = entry.configValue.get();
                SimpleConfigConstants.LOG.error("Config Entry for key '{}' wasn't correct and is set to its default Value '{}'!", path, entry.configValue.get());
            }
        }
    }

    private void writeToConfig(ConfigEntry<?> entry) {
        this.config.set(entry.path, entry.value);
        if (entry.configValue.getRangedComment() == null) this.config.setComment(entry.path, entry.configValue.getComment());
        else this.config.setComment(entry.path, entry.configValue.getRangedComment());
    }
}
