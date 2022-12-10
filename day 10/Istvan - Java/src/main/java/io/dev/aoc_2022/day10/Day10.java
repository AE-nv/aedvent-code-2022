package io.dev.aoc_2022.day10;

import io.dev.aoc.util.FileUtils;

public class Day10 {

    public static void main(String[] args) {
        System.out.println("Part A: " + partA("day10.txt"));
        partB();
    }



    static Long partA(String filename) {
        var signalObserver = new SignalObserver();
        execute(filename, signalObserver);

        return signalObserver.observedStates.stream()
                .map(s -> s.value() * s.completedCycles())
                .reduce(0L, Long::sum);
    }

    private static void execute(String filename, ClockObserver signalObserver) {
        var program = new Program(signalObserver);
        FileUtils.consumeLinesFromStream(filename, l -> program.execute(mapLineToInstruction(l)));
    }

    static Instruction mapLineToInstruction(String line) {
        var parts = line.split(" ");
        return switch(parts[0]) {
            case "noop" -> new NoOp();
            case "addx" -> new AddX(Long.parseLong(parts[1]));
            default -> throw new IllegalArgumentException("Could not parse line " + line);
        };
    }

    static void partB() {
        var crt = new CRT();
        execute("day10.txt", crt);
        crt.printScreen();
    }
}
