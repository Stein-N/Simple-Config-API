package net.xstopho.simpleconfig.core;

import net.xstopho.simpleconfig.api.ISimpleConfigBuilder;

import java.util.Stack;

public abstract class SimpleBaseConfigBuilder<S> extends ConfigBuilder<S> implements ISimpleConfigBuilder {

    protected Stack<String> category = new Stack<>();
    protected String comment;

    public SimpleBaseConfigBuilder(String modid, String fileName) {
        super(modid, fileName);
    }

    protected abstract String[] getIllegalCharacters();

    protected String[] getPath(String key) {
        String[] path = this.category.toArray(new String[this.category.size() + 1]);
        path[path.length -1] = key;
        return path;
    }

    protected void resetState() {
        this.comment = null;
    }

    @Override
    public ISimpleConfigBuilder push(String category) {
        validateFormat(Type.CATEGORY, category);
        this.category.push(category);
        return this;
    }

    @Override
    public ISimpleConfigBuilder pop() {
        if (this.category.isEmpty()) throw new IllegalArgumentException("Category Stack is empty!");
        this.category.pop();
        return this;
    }

    @Override
    public ISimpleConfigBuilder categoryComment(String comment) {
        validateFormat(Type.COMMENT, comment);
        this.addCategoryComment(this.category.toArray(new String[0]), comment);
        return this;
    }

    @Override
    public ISimpleConfigBuilder comment(String comment) {
        validateFormat(Type.COMMENT, comment);
        this.comment = comment;
        return this;
    }

    private void validateFormat(Type type, String input) {
        if (input == null) throw new IllegalArgumentException(type + " can't be null!");
        if (input.isEmpty()) throw new IllegalArgumentException(type + " can't be empty!");
        for (String character : this.getIllegalCharacters()) {
            if (input.contains(character)) throw new IllegalArgumentException(type + " " + input + " contains illegal characters, remove '" + character + "'");
        }
    }

    private enum Type{
        CATEGORY, COMMENT;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
