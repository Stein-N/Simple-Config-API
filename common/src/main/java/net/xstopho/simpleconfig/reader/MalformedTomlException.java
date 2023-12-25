package net.xstopho.simpleconfig.reader;

import java.io.IOException;

public class MalformedTomlException extends IOException {
    public MalformedTomlException(String message) {
        super(message);
    }
}
