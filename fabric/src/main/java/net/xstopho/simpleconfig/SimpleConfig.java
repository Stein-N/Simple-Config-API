package net.xstopho.simpleconfig;

import net.fabricmc.api.ModInitializer;
import net.xstopho.simpleconfig.api.SimpleConfigRegistry;
import net.xstopho.simpleconfig.builder.SimpleConfigBuilder;

import java.util.function.Supplier;

public class SimpleConfig implements ModInitializer {

    static SimpleConfigBuilder builder = new SimpleConfigBuilder();
    static Supplier<Integer> test;

    @Override
    public void onInitialize() {
        builder.push("Test");
        test = builder.define("normal", 100);

        SimpleConfigRegistry.INSTANCE.register(SimpleConfigConstants.MOD_ID, builder);
    }
}
