package io.dev.aoc_2022.day5;

import io.dev.aoc.util.FileUtils;

public class Day5 {

    public static void main(String[] args) {
        System.out.println("Part A:" +partA("stack.txt", "instructions.txt"));
        System.out.println("Part B:" +partB("stack.txt", "instructions.txt"));
    }


    public static String partA(String stackFile, String instructionsFile) {
        final CratesStack stack = CratesStack.createFromFile(stackFile);

        FileUtils.consumeLinesFromStream(instructionsFile, stack::execute);
        return stack.getTopCrates();
    }

    public static String partB(String stackFile, String instructionsFile) {
        final CratesStack stack = CratesStack.createFromFile(stackFile);
        FileUtils.consumeLinesFromStream(instructionsFile, stack::executeAdvanced);
        return stack.getTopCrates();
    }
}
