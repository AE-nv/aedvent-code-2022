package io.dev.aoc_2022.day4;

import io.dev.aoc.util.FileUtils;

import java.util.stream.Stream;

public class Day4 {

    public static void main(String[] args) {
        System.out.println("Part A: " + FileUtils.processLinesAsStream("day4Input.txt", Day4::partA));
        System.out.println("Part B: " + FileUtils.processLinesAsStream("day4Input.txt", Day4::partB));
    }
    public static Section convertToSection(String input) {
        String[] parts = input.trim().split("-");
        return new Section(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
    }

    public static boolean oneSectionFullyContainsOther(Section first, Section second) {
        return first.fullyContains(second) || second.fullyContains(first);
    }

    public static Line convertToLine(String input) {
        String[] parts = input.split(",");
        return new Line(convertToSection(parts[0]), convertToSection(parts[1]));
    }

    public static long partA(Stream<String> lines) {
        return lines.map(Day4::convertToLine)
                .filter(l -> oneSectionFullyContainsOther(l.first(), l.second()))
                .count();
    }

    public static long partB(Stream<String> lines) {
        return lines.map(Day4::convertToLine)
                .filter(l -> l.first().overlaps(l.second()))
                .count();
    }
}


record Line(Section first, Section second){}
record Section(int start, int end){
    boolean fullyContains(Section other) {
        return this.start<= other.start && this.end >= other.end;
    }

    public boolean overlaps(Section other) {
        return (this.start >= other.start() && this.start <= other.end)
                || (this.end >= other.start && this.end <= other.end)
                || this.fullyContains(other);
    }
}
