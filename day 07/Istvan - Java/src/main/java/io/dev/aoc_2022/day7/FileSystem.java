package io.dev.aoc_2022.day7;

import java.util.Objects;
import java.util.Stack;

public class FileSystem {

    private final Stack<Directory> currPath = new Stack<>();
    private final Directory root;


    public FileSystem() {
        root = new Directory("/");
        currPath.push(root);
    }

    public Directory getCurrentDirectory() {
        return currPath.peek();
    }

    public Directory getRoot() {
        return root;
    }

    public void moveUp() {
        currPath.pop();
    }

    public void moveDown(String nextDir) {
        if ("/".equals(nextDir) && getCurrentDirectory().name.equals(nextDir))
            return;
        currPath.push(Objects.requireNonNull(getCurrentDirectory().subdirs.get(nextDir),
                nextDir + " is not a child dir of current directory " + getCurrentDirectory().name));
    }
}
