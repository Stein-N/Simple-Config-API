package net.xstopho.simpleconfig.core;

import net.xstopho.simpleconfig.SimpleConfigConstants;
import net.xstopho.simpleconfig.toml.TomlDeserializer;
import net.xstopho.simpleconfig.toml.TomlElement;
import net.xstopho.simpleconfig.toml.TomlSerializer;
import net.xstopho.simpleconfig.toml.TomlTable;

import java.io.*;
import java.nio.file.*;

public class ConfigFile implements SimpleConfigFile<TomlElement> {
    private final File file;
    private TomlTable table = new TomlTable();
    private boolean tracking = false;

    public ConfigFile(File file) {
        this.file = file;
    }

    public TomlElement get(String[] path) {
        if (path.length == 0) return this.table;

        TomlTable object = this.table;
        for (int i = 0; i < path.length - 1; i++) {
            TomlElement element = object.get(path[i]);
            if (element == null || !element.isTable()) return null;
            object = element.getAsTable();
        }
        return object.get(path[path.length - 1]);
    }

    public void set(String[] path, TomlElement element) {
        TomlTable object = this.table;
        for (int i = 0; i < path.length - 1; i++) {
            TomlElement member = object.get(path[i]);
            if (member == null || !member.isTable()) {
                TomlTable newTable = new TomlTable();
                if (member != null) {
                    newTable.comment = member.comment;
                    newTable.rangedComment = member.rangedComment;
                }
                object.add(path[i], newTable);
                object = newTable;
            } else object = member.getAsTable();
        }

        TomlElement oldElement = object.get(path[path.length -1]);
        String comment = oldElement == null ? null : oldElement.comment;
        String rangedComment = oldElement == null ? null : oldElement.rangedComment;

        element.comment = comment;
        element.rangedComment = rangedComment;
        object.add(path[path.length - 1], element);
    }

    @Override
    public TomlElement getValue(String[] path) {
        return this.get(path);
    }

    @Override
    public void setValue(String[] path, TomlElement value) {
        this.set(path, value);
    }

    @Override
    public void setComment(String[] path, String comment) {
        TomlElement element = this.get(path);
        if (element != null) element.comment = comment;
        else {
            TomlElement emptyElement = TomlElement.empty();
            this.set(path, emptyElement);
            emptyElement.comment = comment;
        }
    }

    @Override
    public void setRangedComment(String[] path, String comment) {
        TomlElement element = this.get(path);
        if (element != null) element.rangedComment = comment;
        else {
            TomlElement emptyElement = TomlElement.empty();
            this.set(path, emptyElement);
            emptyElement.rangedComment = comment;
        }
    }

    @Override
    public void clearValues() {
        this.table = new TomlTable();
    }

    @Override
    public void startTrackingFile() {
        if (this.tracking) throw new IllegalStateException(this.file.getName() + " is already tracked!");

        Path parentPath = this.file.getParentFile().toPath();
        WatchService watcher;
        try {
            watcher = parentPath.getFileSystem().newWatchService();
            parentPath.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        } catch(Exception e) {
            SimpleConfigConstants.LOG.error("Failed to create watcher for '{}' \nMessage: {}", this.file.getName(), e.getMessage());
            return;
        }

        this.tracking = true;
        Thread watcherThread = getThread(watcher);
        watcherThread.start();
    }

    private Thread getThread(WatchService watcher) {
        Thread watcherThread = new Thread(
            () -> {
                while(true) {
                    WatchKey watchKey;
                    try {
                        watchKey = watcher.take();
                    } catch(Exception e) {
                        this.tracking = false;
                        break;
                    }
                    for (WatchEvent<?> pollEvent : watchKey.pollEvents()) {
                        Path path = (Path) pollEvent.context();
                        if (this.file.getName().equals(path.toString())) {
                            this.readFile();
                            break;
                        }
                    }
                    watchKey.reset();
                }
            }, "Simple Config API file Watcher"
        );
        watcherThread.setDaemon(true);
        return watcherThread;
    }

    @Override
    public void readFile() {
        if (!this.file.exists() || this.file.isDirectory()) {
            this.table = new TomlTable();
            return;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
            this.table = TomlDeserializer.readTable(reader);
        } catch(IOException e) {
            SimpleConfigConstants.LOG.error("Failed to read {}!\nMessage: {}", this.file.getName(), e.getMessage());
            this.table = new TomlTable();
        }
    }

    @Override
    public void writeFile() {
        if (!this.file.getParentFile().exists()) this.file.getParentFile().mkdirs();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(this.file))) {
            TomlSerializer.writeTable(writer, this.table);
        } catch(IOException e) {
            SimpleConfigConstants.LOG.error("Failed to write {}!\nMessage: {}", this.file.getName(), e.getMessage());
        }
    }
}
