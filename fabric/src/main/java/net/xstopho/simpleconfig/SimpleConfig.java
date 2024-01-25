package net.xstopho.simpleconfig;

import net.fabricmc.api.ModInitializer;
import net.xstopho.simpleconfig.api.SimpleConfigRegistry;
import net.xstopho.simpleconfig.test.TestConfig;

public class SimpleConfig implements ModInitializer {

    @Override
    public void onInitialize() {
        //SimpleConfigRegistry.INSTANCE.register(SimpleConfigConstants.MOD_ID, TestConfig.getConfig());
    }
}
