package net.xstopho.simpleconfig.values;

import net.xstopho.simpleconfig.toml.TomlElement;
import net.xstopho.simpleconfig.toml.TomlPrimitive;

public class DoubleConfigValue extends BaseConfigValue<Double, TomlElement> {

    private final double minimum, maximum;

    public DoubleConfigValue(Double defaultValue, double minimum, double maximum, String comment){
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
    public boolean validValue(Double value){
        if (minimum == 0 && maximum == 0) return true;
        return value >= this.minimum && value <= this.maximum;
    }

    @Override
    public TomlElement serialize(Double value){
        return TomlPrimitive.of(value);
    }

    @Override
    public Double deserialize(TomlElement serialized){
        return serialized.isInteger() ? Double.valueOf(serialized.getAsInteger())
                : serialized.isDouble() ? serialized.getAsDouble() : null;
    }
}
