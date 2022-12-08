package io.dev.aoc_2022.day8;

import io.dev.aoc.util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.function.Predicate.not;

public class Day8 {
    public static List<Integer> findVisibleTreesFromFront(String lineOfTrees) {
        List<Integer> visibleTreePositions = new ArrayList<>(lineOfTrees.length());
        char previousHighest = '0' -1;
        var characters = lineOfTrees.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            if (characters[i] > previousHighest) {
                previousHighest = characters[i];
                visibleTreePositions.add(i);
                if (previousHighest == '9') {
                    break;
                }
            }
        }
        return visibleTreePositions;
    }

    public static void markVisibleInRow(String lineOfTrees, final int rowNumber, Set<TreeLocation> visibleTrees) {
        var potentiallVisibleTrees = findVisibleTreesFromFront(lineOfTrees);
        visibleTrees.addAll(potentiallVisibleTrees.stream()
                .map(i -> new TreeLocation(rowNumber,i))
                .filter(not(visibleTrees::contains))
                .collect(Collectors.toSet()));

        potentiallVisibleTrees = findVisibleTreesFromFront(new StringBuilder(lineOfTrees).reverse().toString());
        var lastColumnIndex = lineOfTrees.length()-1;
        visibleTrees.addAll(potentiallVisibleTrees.stream()
                .map(i -> new TreeLocation(rowNumber,lastColumnIndex-i))
                .filter(not(visibleTrees::contains))
                .collect(Collectors.toSet()));
    }

    public static void markVisibleInColumn(String lineOfTrees, final int colNumber, Set<TreeLocation> visibleTrees) {
        var potentiallVisibleTrees = findVisibleTreesFromFront(lineOfTrees);
        visibleTrees.addAll(potentiallVisibleTrees.stream()
                .map(i -> new TreeLocation(i,colNumber))
                .filter(not(visibleTrees::contains))
                .collect(Collectors.toSet()));

        potentiallVisibleTrees = findVisibleTreesFromFront(new StringBuilder(lineOfTrees).reverse().toString());
        var lastRowIndex = lineOfTrees.length()-1;
        visibleTrees.addAll(potentiallVisibleTrees.stream()
                .map(i -> new TreeLocation(lastRowIndex-i,colNumber))
                .filter(not(visibleTrees::contains))
                .collect(Collectors.toSet()));
    }

    public static int partA(String filename) {
        return FileUtils.processLinesAsList(filename, lines -> {
            var visibleTrees = new HashSet<TreeLocation>();
            IntStream.range(0, lines.size())
                    .forEach(i -> markVisibleInRow(lines.get(i), i, visibleTrees));

            IntStream.range(0, lines.get(0).length())
                    .forEach(i ->
                        markVisibleInColumn(lines.stream().map(l -> l.charAt(i)).map(String::valueOf).collect(Collectors.joining())
                                , i, visibleTrees)
                    );

            return visibleTrees.size();
        });
    }

    public static void main(String[] args) {
        System.out.println("Part A: " + partA("day8.txt"));
        System.out.println("Part B: " + partB("day8.txt"));
    }

    public static long partB(String filename) {
        return FileUtils.processLinesAsList(filename, lines -> {
            List<TreeLocation> locations = new LinkedList<>();
            for (int r = 0; r < lines.size(); r++) {
                for (int c= 0; c < lines.get(0).length(); c++) {
                    locations.add(new TreeLocation(r,c));
                }
            }

            return locations.stream()
                    .map(tree -> calculateScenicScore(tree, lines))
                    .reduce(0L, Long::max);
        });
    }

    public static long calculateScoreInOneDirection(char currentTreeHeight, String viewingLine) {
        long count = 0L;
        for (char c : viewingLine.toCharArray()) {
            count++;
            if (c >= currentTreeHeight)
                break;
        }
        return count;
    }

    public static String buildViewLineRight(TreeLocation treeLocation, List<String> field) {
        if (treeLocation.column() == field.get(0).length() -1)
            return "";
        return field.get(treeLocation.row()).substring(treeLocation.column()+1);
    }

    public static String buildViewLineLeft(TreeLocation treeLocation, List<String> field) {
        if (treeLocation.column() == 0)
            return "";
        return new StringBuilder(field.get(treeLocation.row()).substring(0,treeLocation.column()))
                .reverse()
                .toString();
    }

    public static String buildViewLineUp(TreeLocation treeLocation, List<String> field) {
        if (treeLocation.row() == 0)
            return "";

        return IntStream.iterate(treeLocation.row()-1, i -> i >= 0, i -> i - 1)
                .mapToObj(i -> field.get(i).charAt(treeLocation.column()))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public static String buildViewLineDown(TreeLocation treeLocation, List<String> field) {
        int highestRowIndex = field.size();
        if (treeLocation.row() == highestRowIndex -1)
            return "";

        return IntStream.iterate(treeLocation.row()+1, i -> i < highestRowIndex, i -> i + 1)
                .mapToObj(i -> field.get(i).charAt(treeLocation.column()))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public static long calculateScenicScore(TreeLocation tree, List<String> field) {
        char currentTreeHight = field.get(tree.row()).charAt(tree.column());
        return calculateScoreInOneDirection(currentTreeHight, buildViewLineRight(tree, field))
                * calculateScoreInOneDirection(currentTreeHight, buildViewLineLeft(tree, field))
                * calculateScoreInOneDirection(currentTreeHight, buildViewLineUp(tree, field))
                * calculateScoreInOneDirection(currentTreeHight, buildViewLineDown(tree, field));
    }
}

record TreeLocation(int row, int column){}
