package io.dev.aoc_2022.day5;


import io.dev.aoc.util.FileUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CratesStack {
    private final Map<String, Stack<String>> crates = new HashMap<>();

    public static CratesStack createFromFile(String filename) {
        final CratesStack newStack = new CratesStack();
        FileUtils.processLinesAsList(filename, lines -> {
            IntStream.iterate(lines.size()-1, i -> i >= 0, i -> i-1)
                    .skip(1) // last line are column numbers
                    .mapToObj(i -> StackReader.readCrates(lines.get(i)))
                    .forEach(newStack::addCrates);
            return null;
        });

        return newStack;
    }

    public void addCrates(String[] newCrates) {
        IntStream.iterate(0, i -> i < newCrates.length, i -> i+1)
                .filter(i -> newCrates[i] != null)
                .forEach(i -> crates.computeIfAbsent(String.valueOf(i+1),s -> new Stack<>()).push(newCrates[i]));
    }

    public String getTopCrateOn(String column) {
        return crates.containsKey(column)?crates.get(column).peek():null;
    }

    public String getTopCrates() {
        return crates.keySet()
                .stream()
                .sorted()
                .map(crates::get)
                .map(Stack::peek)
                .collect(Collectors.joining());
    }

    private Instruction parseInstruction(String instructionString) {
        String[] parts = instructionString.split(" ");
        return new Instruction(Integer.parseInt(parts[1]), parts[3], parts[5]);
    }
    public void execute(String instr) {
        var instruction = parseInstruction(instr);
        for (int i = 0; i < instruction.amount; i++) {
            crates.get(instruction.to).push(crates.get(instruction.from).pop());
        }
    }

    public void executeAdvanced(String instr) {
        var instruction = parseInstruction(instr);
        Stack<String> temp = new Stack<>();
        for (int i = 0; i < instruction.amount; i++) {
            temp.push(crates.get(instruction.from).pop());
        }

        var to = crates.get(instruction.to);
        for (int i = 0; i< instruction.amount; i++) {
            to.push(temp.pop());
        }
    }

    private record Instruction(int amount, String from, String to){}
}
class StackReader {

    boolean isCrateLine(String line) {
        var trimmed = line.trim();
        return !trimmed.isEmpty() && trimmed.charAt(0) == '[';
    }

    public static String[] readCrates(String line) {
        String[] crates = new String[line.length()/4+1];
        for (int i = 1; i < line.length(); i+=4) {
            var crateId = line.charAt(i);
            if (Character.isLetter(crateId)) {
                crates[i/4] = String.valueOf(crateId);
            } else {
                crates[i/4] = null;
            }
        }
        return crates;
    }
}
