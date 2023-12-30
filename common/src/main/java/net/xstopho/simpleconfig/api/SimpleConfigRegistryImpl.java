package net.xstopho.simpleconfig.api;

import net.xstopho.simpleconfig.builder.ISimpleConfigBuilder;
import net.xstopho.simpleconfig.config.SimpleModConfig;

public class SimpleConfigRegistryImpl implements SimpleConfigRegistry {

    /**
     * @param modId  ModID of the Mod utilizing this API
     * @param fileName  Can be set to create more than one config File
     * @param builder  Every Builder that implements the {@link ISimpleConfigBuilder} can be set.<br>
     *                This Method get called in the Main Method of the Mod to register and create
     *                the specified config File.
     */
    @Override
    public void register(String modId, String fileName, ISimpleConfigBuilder builder) {
        new SimpleModConfig(modId, fileName, builder);
    }

    /**
     * @param modId  ModID of the Mod utilizing this API, also used as the File name for the config.
     * @param builder  Every Builder that implements the {@link ISimpleConfigBuilder} can be set.<br>
     *                This Method get called in the Main Method of the Mod to register and create
     *                the specified config File.
     */
    @Override
    public void register(String modId, ISimpleConfigBuilder builder) {
        new SimpleModConfig(modId, modId, builder);
    }
}
