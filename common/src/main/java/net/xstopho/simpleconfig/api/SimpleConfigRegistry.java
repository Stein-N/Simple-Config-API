package net.xstopho.simpleconfig.api;

import net.xstopho.simpleconfig.builder.ISimpleConfigBuilder;

public interface SimpleConfigRegistry {

    SimpleConfigRegistry INSTANCE = new SimpleConfigRegistryImpl();

    void register(String modId, String fileName, ISimpleConfigBuilder builder);
    void register(String modId, ISimpleConfigBuilder builder);
}
