package io.dev.aoc_2022.day7;

import io.dev.aoc.util.FileUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class Day7 {

    public static FileSystem scanFileSystem(String filename) {
        var fileSystem = new FileSystem();
        FileUtils.consumeLinesFromStream(filename, line -> parseLine(line).applyTo(fileSystem));
        return fileSystem;
    }

    public static Command parseLine(String line) {
        var parts = line.split(" ");
        return switch (parts[0]) {
            case "$" -> createCommand(parts);
            case "dir" -> new AddDirectory(parts[1]);
            default -> new AddFile(Long.parseLong(parts[0]), parts[1]);
        };
    }

    private static Command createCommand(String... parts) {
        return switch(parts[1]) {
            case "cd" -> new ChangeDirectory(parts[2]);
            case "ls" -> new ListFiles();
            default -> throw new IllegalArgumentException("Can't process command " + parts[1]);
        };
    }

    public static long partA(FileSystem fs) {
        Directory root = fs.getRoot();
        List<Long> candidates = new LinkedList<>();

        getTotalSize(root, candidates, l -> l <= 100000);

        return candidates.stream()
                .reduce(0L, Long::sum);
    }

    public static long getTotalSize(Directory dir, List<Long> candidates, Predicate<Long> predicate) {
        long totalSizeOfSubDirs = 0;
        for (Directory subDirectory : dir.getSubDirectories()) {
            var size = getTotalSize(subDirectory, candidates, predicate);
            if (predicate.test(size) ) {
                candidates.add(size);
            }
            totalSizeOfSubDirs += size;
        }
        return dir.getSizeOfDirectFiles() + totalSizeOfSubDirs;
    }


    public static void main(String[] args) {
        FileSystem fs = scanFileSystem("Day7.txt");
        System.out.println("Part A: " + partA(fs));

        System.out.println("Part B: " + partB(fs));
    }

    public static long partB(FileSystem fs) {
        Directory root = fs.getRoot();
        List<Long> candidates = new LinkedList<>();
        var totalSizeOfRoot = getTotalSize(root, candidates, l -> false);
        var spaceToObtain = 30000000L - (70000000L - totalSizeOfRoot);

        getTotalSize(root, candidates, l -> l >= spaceToObtain);
        return candidates
                .stream()
                .sorted()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No matching folder found"));
    }
}
