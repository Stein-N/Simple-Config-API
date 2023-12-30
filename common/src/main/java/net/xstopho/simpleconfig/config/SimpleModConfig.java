package net.xstopho.simpleconfig.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.io.ParsingException;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import net.xstopho.simpleconfig.SimpleConfigConstants;
import net.xstopho.simpleconfig.builder.*;
import net.xstopho.simpleconfig.platform.Services;
import net.xstopho.simpleconfig.values.ConfigValue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleModConfig {

    private final String modID;
    private final File file;
    private List<ConfigEntry<?>> entries = new ArrayList<>();
    private CommentedConfig config = CommentedConfig.inMemory();
    private final ISimpleConfigBuilder builder;

    public SimpleModConfig(String modID, String fileName, ISimpleConfigBuilder builder) {
        this.modID = modID;
        this.builder = builder;
        this.file = new File(Services.INSTANCE.getConfigDir() + "/" + fileName + ".toml");

        // Gets all entries defined by any ConfigBuilder that implements the ISimpleConfigBuilder interface
        this.entries.addAll(builder.getEntries().values());

        // Adds all keys and there Values from the .toml File to the empty CommentedConfig
        this.readValuesFromTomlFile();


        this.entries.forEach(this::readConfigValue);

        /* By clearing the CommentedConfig all Values/Keys that are not declared
            via the Config Builder gets deleted or corrected. */
        this.config = CommentedConfig.inMemory();

        // Write Values/Comments/Category Comments to the empty CommentedConfig
        this.entries.forEach(this::writeToConfig);

        // Write the corrected Config
        this.writeValuesToTomlFile();

        // Every entry is now "allowed" to return the config Value
        this.entries.forEach(entry -> entry.loaded = true);
    }

    /**
     * Checks if the specified config file exists and then tries to read it with the
     * TomlParser to the CommentedConfig.<br>
     * When the ParsingException occurs thw whole Config File gets dropped and
     * recreated with its default Values
     */
    private void readValuesFromTomlFile() {
        if (!this.file.exists()) return;

        try(FileReader reader = new FileReader(this.file)) {
            this.config = new TomlParser().parse(reader);
        } catch(IOException | ParsingException e) {
            SimpleConfigConstants.LOG.error("Reading '{}' from Mod '{}' failed!\nMessage: {}", this.file.getName(), this.modID, e.getMessage());
        }
    }

    /**
     * Writes the CommentedConfig to the File
     */
    private void writeValuesToTomlFile() {
        TomlWriter writer = new TomlWriter();
        writer.write(this.config, this.file, WritingMode.REPLACE);
    }

    /**
     * @param entry {@link ConfigEntry} that is defined by the {@link SimpleConfigBuilder}<br>
     *           At first, it checks if the Config contains the Key/Value,
     *           when not it sets the Value of the {@link ConfigEntry} to
     *           the default Value saved in the {@link ConfigValue}<br>
     *           When the Config contains the Key, and it is a valid Value
     *           (in the set Bounds and the correct type), it sets the Value
     *           of the {@link ConfigEntry} to the Value fetched from the .toml file,
     *           when not the Value in the .toml File gets corrected to the default Value.
     */
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

    /**
     * @param entry {@link ConfigEntry} that is defined by the {@link SimpleConfigBuilder}<br>
     *              At first all category comments gets written to the CommentedConfig<br>
     *              Then all Keys with their Values gets written.<br>
     *              And last but not Least the (Ranged-)comments gets written. <br>
     *              -> If a Value is Ranged normal Comments gets ignored.
     */
    private void writeToConfig(ConfigEntry<?> entry) {
        for (Map.Entry<String, String> categoryComment : this.builder.getCategoryComments().entrySet()) {
            this.config.setComment(categoryComment.getKey(), categoryComment.getValue());
        }

        this.config.set(entry.path, entry.value);

        if (entry.configValue.getRangedComment() == null) this.config.setComment(entry.path, entry.configValue.getComment());
        else this.config.setComment(entry.path, entry.configValue.getRangedComment());
    }
}
