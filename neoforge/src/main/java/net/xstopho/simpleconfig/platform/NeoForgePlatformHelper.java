package net.xstopho.simpleconfig.platform;

import net.neoforged.fml.loading.FMLPaths;
import net.xstopho.simpleconfig.platform.services.IPlatformHelper;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}