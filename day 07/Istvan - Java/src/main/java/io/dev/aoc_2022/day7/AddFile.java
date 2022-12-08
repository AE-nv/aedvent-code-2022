package io.dev.aoc_2022.day7;

public class AddFile extends Command {
    final long fileSize;

    final String filename;

    public AddFile(long fileSize, String filename) {
        this.fileSize = fileSize;
        this.filename = filename;
    }

    @Override
    public void applyTo(FileSystem fs) {
        fs.getCurrentDirectory().addFile(this.fileSize);
    }
}
