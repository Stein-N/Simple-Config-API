package net.xstopho.simpleconfig.core;

public interface SimpleConfigFile<S> {

    S getValue(String[] path);
    void setValue(String[] path, S value);
    void setComment(String[] path, String comment);
    void setRangedComment(String[] path, String comment);
    void clearValues();
    void startTrackingFile();
    void readFile();
    void writeFile();
}
