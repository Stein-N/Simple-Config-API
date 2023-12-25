package net.xstopho.simpleconfig.platform;

import net.fabricmc.loader.api.FabricLoader;
import net.xstopho.simpleconfig.platform.services.IPlatformHelper;

import java.nio.file.Path;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
