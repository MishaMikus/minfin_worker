package client.rest.model;

import java.io.File;

public class FileWrapper {
    private String name;
    private File file;

    public FileWrapper(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }
}
