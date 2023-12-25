package net.xstopho.simpleconfig.platform;

import net.xstopho.simpleconfig.SimpleConfigConstants;
import net.xstopho.simpleconfig.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper INSTANCE = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        SimpleConfigConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}