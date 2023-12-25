package net.xstopho.simpleconfig.api;

public interface SimpleConfigRegistry {

    SimpleConfigRegistry INSTANCE = new SimpleConfigRegistryImpl();

    void register(ISimpleConfigBuilder builder);
}
