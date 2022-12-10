package io.dev.aoc_2022.day10;

public class AddX extends Instruction{
    private final long increment;
    public AddX(long increment) {
        this.increment = increment;
    }

    @Override
    public int getCyclesNeeded() {
        return 2;
    }

    @Override
    public long apply(long currentValue) {
        return currentValue + increment;
    }
}
