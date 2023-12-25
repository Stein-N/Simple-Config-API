package net.xstopho.simpleconfig.values;

import net.xstopho.simpleconfig.toml.TomlElement;
import net.xstopho.simpleconfig.toml.TomlPrimitive;

public class IntegerConfigValue extends BaseConfigValue<Integer, TomlElement>{

    private final int minimum, maximum;

    public IntegerConfigValue(Integer defaultValue, int minimum, int maximum, String comment){
        super(defaultValue, comment);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public String getRangedComment(){
        if (minimum == 0 && maximum == 0) return null;
        return "Range: " + this.minimum + " ~ " + this.maximum + " - Default: " + this.defaultValue;
    }

    @Override
    public boolean validValue(Integer value){
        if (minimum == 0 && maximum == 0) return true;
        return value >= this.minimum && value <= this.maximum;
    }

    @Override
    public TomlElement serialize(Integer value){
        return TomlPrimitive.of(value);
    }

    @Override
    public Integer deserialize(TomlElement serialized){
        return serialized.isInteger() ? serialized.getAsInteger() : null;
    }
}
