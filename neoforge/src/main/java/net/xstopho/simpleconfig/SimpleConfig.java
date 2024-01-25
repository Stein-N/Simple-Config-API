package net.xstopho.simpleconfig;


import net.neoforged.fml.common.Mod;
import net.xstopho.simpleconfig.api.SimpleConfigRegistry;
import net.xstopho.simpleconfig.test.TestConfig;

@Mod(SimpleConfigConstants.MOD_ID)
public class SimpleConfig {

    public SimpleConfig() {
        //SimpleConfigRegistry.INSTANCE.register(SimpleConfigConstants.MOD_ID, TestConfig.getConfig());
    }
}