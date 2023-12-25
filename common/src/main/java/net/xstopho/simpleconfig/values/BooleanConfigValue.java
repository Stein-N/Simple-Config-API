package net.xstopho.simpleconfig.values;

import net.xstopho.simpleconfig.toml.TomlElement;
import net.xstopho.simpleconfig.toml.TomlPrimitive;

public class BooleanConfigValue extends BaseConfigValue<Boolean, TomlElement> {

    public BooleanConfigValue(Boolean defaultValue, String comment){
        super(defaultValue, comment);
    }

    @Override
    public String getRangedComment(){
        return "Allowed values: true, false - Default: " + this.defaultValue;
    }

    @Override
    public boolean validValue(Boolean value){
        return true;
    }

    @Override
    public TomlElement serialize(Boolean value){
        return TomlPrimitive.of(value);
    }

    @Override
    public Boolean deserialize(TomlElement serialized){
        return serialized.isBoolean() ? serialized.getAsBoolean() : null;
    }
}