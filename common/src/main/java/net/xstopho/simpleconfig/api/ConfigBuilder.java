package net.xstopho.simpleconfig.api;

import net.xstopho.simpleconfig.core.SimpleConfigBuilder;

public interface ConfigBuilder {

    static ISimpleConfigBuilder create(String modID, String fileName) {
        if (fileName != null && fileName.matches("[^\\\\/:*?!\"<>|]"))
            throw new IllegalArgumentException("Filename contains illegal characters remove'" + "[^\\\\/:*?!\"<>|]" + "'");
        return new SimpleConfigBuilder(modID, fileName);
    }

    static ISimpleConfigBuilder create(String modID) {
        return create(modID, modID);
    }
}
