package io.dev.aoc_2022.day7;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Directory {
    final String name;

    final Map<String, Directory> subdirs = new HashMap<>();

    private long totalSizeOfDirectlyContainedFiles;

    public Directory(String name) {
        this.name = name;
    }

    public void addFile(long filesize) {
        totalSizeOfDirectlyContainedFiles += filesize;
    }

    public void addDirectory(Directory newDir) {
        subdirs.put(newDir.name, newDir);
    }

    public long getSizeOfDirectFiles() {
        return totalSizeOfDirectlyContainedFiles;
    }

    public Collection<Directory> getSubDirectories() {
        return this.subdirs.values();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Directory directory = (Directory) o;
        return Objects.equals(name, directory.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
