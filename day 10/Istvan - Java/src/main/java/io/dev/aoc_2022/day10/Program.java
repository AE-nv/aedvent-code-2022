package io.dev.aoc_2022.day10;

public class Program {
    private int cycles;

    private long value = 1;

    private ClockObserver observer;

    public Program(ClockObserver observer) {
        this.observer = observer;
    }

    public Program() {
    }

    public State getState() {
        return new State(value,cycles);
    }

    public State execute(Instruction instruction) {
        for (int i = 0; i < instruction.getCyclesNeeded(); i++) {
            cycles++;
            if (observer != null && observer.getSelector().test(value, cycles)) {
                observer.notify(new State(value, cycles));
            }
        }
        value = instruction.apply(value);
        return new State(value, cycles);
    }
}

record State (long value, int completedCycles){}
