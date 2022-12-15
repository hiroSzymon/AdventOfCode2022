package com.hiroszymon.aoc2022;

import java.util.HashMap;
import java.util.Objects;

public class CustomFile implements Comparable<CustomFile> {
    private final String name;
    private final CustomFile parent;
    private final HashMap<String, CustomFile> children;
    private final boolean isDirectory;
    private long size;
    private long depth;

    public static CustomFile newFile(String name, CustomFile parent, long size) {
        CustomFile file = new CustomFile(name, parent, false);
        file.size = size;
        file.addToParentDir();
        file.depth = parent.depth + 1;
        return file;
    }

    public static CustomFile newDir(String name, CustomFile parent) {
        CustomFile dir = new CustomFile(name, parent, true);
        if (parent != null) {
            dir.depth = parent.depth + 1;
            dir.addToParentDir();
        }
        return dir;
    }

    private CustomFile(String name, CustomFile parent, boolean isDirectory) {
        this.name = name;
        this.parent = parent;
        this.isDirectory = isDirectory;
        this.children = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public CustomFile getParent() {
        return parent;
    }

    @SuppressWarnings("unused")
    public HashMap<String, CustomFile> getChildren() {
        return children;
    }

    public CustomFile getChildDir(String name) {
        return children.get(name);
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public long getSize() {
        return size;
    }

    public String toPath() {
        if (this.name.isEmpty() || this.parent == null)
            return "/";
        return parent.toPath() + name + (isDirectory ? "/" : "");
    }

    public void addToParentDir() {
        addSize(this.size);
        parent.children.put(this.name, this);
    }

    public long getDepth() {
        return depth;
    }

    private void addSize(long size) {
        if (parent != null) {
            parent.addSize(size);
        }
        this.size += size;
    }

    @Override
    public int compareTo(CustomFile o) {
        return Long.compare(getDepth(), o.getDepth());
    }

    @SuppressWarnings("unused")
    public void addFile(CustomFile f) {
        children.put(f.name, f);
    }

    @Override
    public String toString() {
        return toPath();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomFile that = (CustomFile) o;
        return isDirectory == that.isDirectory && size == that.size && depth == that.depth && Objects.equals(name, that.name) && Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parent, isDirectory, size, depth);
    }

}
