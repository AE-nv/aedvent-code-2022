package io.dev.aoc_2022.day7;

public class ChangeDirectory extends Command{

    final String nextDir;

    public ChangeDirectory(String nextDir) {
        this.nextDir = nextDir;
    }

    @Override
    public void applyTo(FileSystem fs) {
        if (nextDir.equals("..")) {
            fs.moveUp();
        } else {
            fs.moveDown(nextDir);
        }
    }
}
