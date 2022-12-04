package io.dev.aoc_2022.day3;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.dev.aoc.util.FileUtils.processLinesAsList;
import static io.dev.aoc.util.FileUtils.processLinesAsStream;

public class Day3 {
    static int determinePriority(String input) {
        char firstChar = input.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            return 27 + firstChar - 'A';
        } else {
            return 1 + firstChar - 'a';
        }
    }

    static String findMatchingItem(String base, String... others) {
        return base.chars()
                .filter(c ->
                    Arrays.stream(others)
                            .map(s -> s.indexOf(c) != -1)
                            .reduce(Boolean.TRUE, Boolean::logicalAnd)
                )
                .mapToObj(Character::toString)
                .findFirst()
                .orElseThrow();
    }

    static int calculateSumOfPrios(Stream<String> lines) {
        return lines
                .map(l -> findMatchingItem(l.substring(0, l.length()/2),l.substring(l.length()/2)))
                .map(Day3::determinePriority)
                .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        System.out.println("Part A: " + processLinesAsStream("day3Input.txt", Day3::calculateSumOfPrios));
        System.out.println("Part B: " + processLinesAsList("day3Input.txt", Day3::findBadgePrios));
    }

    public static int findBadgePrios(List<String> lines) {
        return IntStream.iterate(0, i -> i < lines.size(), i -> i+3)
                .mapToObj(i -> findMatchingItem(lines.get(i), lines.get(i+1), lines.get(i+2)))
                .map(Day3::determinePriority)
                .reduce(0, Integer::sum);
    }


}
