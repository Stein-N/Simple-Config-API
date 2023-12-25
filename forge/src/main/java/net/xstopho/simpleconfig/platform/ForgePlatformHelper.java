package net.xstopho.simpleconfig.platform;

import net.minecraftforge.fml.loading.FMLPaths;
import net.xstopho.simpleconfig.platform.services.IPlatformHelper;

import java.nio.file.Path;

public class ForgePlatformHelper implements IPlatformHelper {


    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}