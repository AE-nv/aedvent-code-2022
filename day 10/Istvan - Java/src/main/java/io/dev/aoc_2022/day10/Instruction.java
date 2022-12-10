package io.dev.aoc_2022.day10;

public abstract class Instruction {

    public abstract int getCyclesNeeded();

    public long apply(long currentValue) {
        return currentValue;
    }

}
