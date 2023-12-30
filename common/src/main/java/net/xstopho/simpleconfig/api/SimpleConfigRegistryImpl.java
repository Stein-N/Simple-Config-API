package net.xstopho.simpleconfig.api;

import net.xstopho.simpleconfig.builder.ISimpleConfigBuilder;
import net.xstopho.simpleconfig.config.SimpleModConfig;

public class SimpleConfigRegistryImpl implements SimpleConfigRegistry {
    @Override
    public void register(String modId, String fileName, ISimpleConfigBuilder builder) {
        new SimpleModConfig(modId, fileName, builder);
    }

    @Override
    public void register(String modId, ISimpleConfigBuilder builder) {
        new SimpleModConfig(modId, modId, builder);
    }
}
