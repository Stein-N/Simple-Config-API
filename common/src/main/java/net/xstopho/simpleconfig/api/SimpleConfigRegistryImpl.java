package net.xstopho.simpleconfig.api;

public class SimpleConfigRegistryImpl implements SimpleConfigRegistry {
    @Override
    public void register(ISimpleConfigBuilder builder) {
        builder.build();
    }
}
