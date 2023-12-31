package net.xstopho.simpleconfig;

import net.fabricmc.api.ModInitializer;
import net.xstopho.simpleconfig.builder.SimpleConfigBuilder;

import java.util.function.Supplier;

public class SimpleConfig implements ModInitializer {

    static SimpleConfigBuilder builder = new SimpleConfigBuilder();

    @Override
    public void onInitialize() {
        Supplier<Integer> test = builder.define("test", 100);
    }
}
