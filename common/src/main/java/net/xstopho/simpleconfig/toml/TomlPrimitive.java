package net.xstopho.simpleconfig.toml;

public class TomlPrimitive<T> extends TomlElement {

    private final T value;

    private TomlPrimitive(T value) {
        if(value == null) throw new IllegalArgumentException("Value can not be null!");
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString(){
        return this.value.toString();
    }

    public static TomlPrimitive<?> of(int value){
        return new TomlInteger(value);
    }

    public static TomlPrimitive<?> of(double value){
        return new TomlDouble(value);
    }

    public static TomlPrimitive<?> of(boolean value){
        return new TomlBoolean(value);
    }

    public static TomlPrimitive<?> of(String value){
        return new TomlString(value);
    }

    public static class TomlInteger extends TomlPrimitive<Integer> {
        private TomlInteger(Integer value){
            super(value);
        }
    }

    public static class TomlDouble extends TomlPrimitive<Double> {
        private TomlDouble(Double value){
            super(value);
        }
    }

    public static class TomlBoolean extends TomlPrimitive<Boolean> {
        private TomlBoolean(Boolean value){
            super(value);
        }
    }

    public static class TomlString extends TomlPrimitive<String> {
        private TomlString(String value){
            super(value);
        }

        @Override
        public String toString(){
            return "'" + super.toString() + "'";
        }
    }
}
