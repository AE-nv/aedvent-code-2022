package io.dev.aoc_2022.day7;

public class AddDirectory extends Command{
    final Directory newDir;

    public AddDirectory(String newDirName) {
        this.newDir = new Directory(newDirName);
    }

    @Override
    public void applyTo(FileSystem fs) {
        fs.getCurrentDirectory().addDirectory(newDir);
    }
}
