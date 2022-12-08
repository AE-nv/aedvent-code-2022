package io.dev.aoc_2022.day6;

import io.dev.aoc.util.FileUtils;

import java.util.stream.IntStream;

public class Day6 {


    public static void main(String[] args) {
        System.out.println("Part A:" + partA());
        System.out.println("Part B:" + partB());
    }

    static int partA() {
        return FileUtils.processLinesAsStream(
                "Day6Input.txt",
                s -> s.map(Day6::positionOfPackageMarker).reduce(0, Integer::sum));
    }

    static int partB() {
        return FileUtils.processLinesAsStream(
                "Day6Input.txt",
                s -> s.map(Day6::positionOfMessageMarker).reduce(0, Integer::sum));
    }

    static boolean isMarker(String buffer) {
        return buffer.chars().distinct().count() == buffer.length();
    }

    public static int positionOfPackageMarker(String feed) {
        return positionOfMarker(feed, 4);
    }

    private static int positionOfMarker(String feed, int markerLength) {
        return IntStream.range(markerLength, feed.length())
                .filter(i -> isMarker(feed.substring(i - markerLength, i)))
                .findFirst()
                .orElse(-1);
    }

    public static int positionOfMessageMarker(String feed) {
        return positionOfMarker(feed, 14);
    }
}
