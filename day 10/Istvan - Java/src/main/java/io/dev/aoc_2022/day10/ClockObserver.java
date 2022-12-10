package io.dev.aoc_2022.day10;

import java.util.function.BiPredicate;

public interface ClockObserver {
    void notify(State state);

    BiPredicate<Long, Integer> getSelector();
}
