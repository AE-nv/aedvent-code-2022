package io.dev.aoc_2022.day10;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;

public class SignalObserver implements ClockObserver {

    final List<Integer> interestedCycles = List.of(20,60,100,140,180,220);

    final List<State> observedStates = new LinkedList<>();

    @Override
    public void notify(State state) {
        observedStates.add(state);
    }

    @Override
    public BiPredicate<Long, Integer> getSelector() {
        return (value, cycle) -> interestedCycles.contains(cycle);
    }
}
