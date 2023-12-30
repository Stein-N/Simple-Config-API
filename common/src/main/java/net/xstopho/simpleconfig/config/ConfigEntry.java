package net.xstopho.simpleconfig.config;

import net.xstopho.simpleconfig.values.ConfigValue;

public class ConfigEntry<T> {
     public final String path;
     public final ConfigValue<T> configValue;
     public boolean loaded = false;
     public T value;

     public ConfigEntry(String path, ConfigValue<T> configValue) {
         this.path = path;
         this.configValue = configValue;
     }

     public T getValue() {
         if (!this.loaded) throw new IllegalStateException("Config isn't loaded yet!");
         return this.value;
     }

     public ConfigEntry<T> get() {
         return this;
     }
}
