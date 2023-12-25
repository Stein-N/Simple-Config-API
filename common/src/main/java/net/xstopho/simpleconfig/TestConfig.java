package net.xstopho.simpleconfig;

import net.xstopho.simpleconfig.api.ConfigBuilder;
import net.xstopho.simpleconfig.api.ISimpleConfigBuilder;

import java.util.function.Supplier;

public class TestConfig {

    public static final ISimpleConfigBuilder builder = ConfigBuilder.create(SimpleConfigConstants.MOD_ID);

    public static final Supplier<Integer> integerValue;
    public static final Supplier<Double> doubleValue;

    static {
        builder.push("Integer");
        integerValue = builder.define("copperDurability", 100);

        builder.pop().push("Double");
        doubleValue = builder.define("materialDustDropChance", 0.50);
    }
}
