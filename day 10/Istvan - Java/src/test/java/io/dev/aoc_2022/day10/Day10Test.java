package io.dev.aoc_2022.day10;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day10Test {

    @Test
    void initialState() {
        var program = new Program();
        var state = program.getState();
        assertThat(state.value()).isEqualTo(1);
        assertThat(state.completedCycles()).isEqualTo(0);
    }

    @Test
    void executeNoOp() {
        var instruction = new NoOp();
        var program = new Program();
        var newState = program.execute(instruction);
        assertThat(newState.value()).isEqualTo(1);
        assertThat(newState.completedCycles()).isEqualTo(1);
        assertThat(program.getState()).isEqualTo(newState);
    }

    @Test
    void executeAddX() {
        var instruction = new AddX(5L);
        var program = new Program();
        var newState = program.execute(instruction);
        assertThat(newState.value()).isEqualTo(6);
        assertThat(newState.completedCycles()).isEqualTo(2);
    }


    @Test
    void notifyObserver() {
        var observer = new SignalObserver();
        var program = new Program(observer);

        IntStream.range(0,50)
                .forEach(i -> program.execute(new NoOp()));

        assertThat(observer.observedStates).isEqualTo(List.of(new State(1,20)));
    }

    @Test
    void partA() {
        assertThat(Day10.partA("day10Test.Txt")).isEqualTo(13140L);
    }
}
