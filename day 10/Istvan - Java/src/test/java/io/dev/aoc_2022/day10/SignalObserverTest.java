package io.dev.aoc_2022.day10;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SignalObserverTest {
    private final SignalObserver signalObserver = new SignalObserver();
    private final int numberOfTests = 10;

    @Test
    void interestedInCycle20() {
        IntStream.range(0, numberOfTests)
                .forEach(i ->
                        assertThat(signalObserver.getSelector().test(getRandomLong(),20)).isTrue());
    }

    @Test
    void interestedInCycle60() {
        IntStream.range(0, numberOfTests)
                .forEach(i ->
                        assertThat(signalObserver.getSelector().test(getRandomLong(),60)).isTrue());
    }

    @Test
    void interestedInCycle100() {
        IntStream.range(0, numberOfTests)
                .forEach(i ->
                        assertThat(signalObserver.getSelector().test(getRandomLong(),100)).isTrue());
    }

    @Test
    void interestedInCycle140() {
        IntStream.range(0, numberOfTests)
                .forEach(i ->
                        assertThat(signalObserver.getSelector().test(getRandomLong(),140)).isTrue());
    }

    @Test
    void interestedInCycle180() {
        IntStream.range(0, numberOfTests)
                .forEach(i ->
                        assertThat(signalObserver.getSelector().test(getRandomLong(),180)).isTrue());
    }

    @Test
    void interestedInCycle220() {
        IntStream.range(0, numberOfTests)
                .forEach(i ->
                        assertThat(signalObserver.getSelector().test(getRandomLong(),220)).isTrue());
    }

    @Test
    void notInterestedInOtherCycle() {
        IntStream.range(0, numberOfTests)
                .forEach(i ->
                        assertThat(signalObserver.getSelector().test(getRandomLong(), getRandomUninterestingCycleNumber())).isFalse());
    }

    private Long getRandomLong() {
        return new Random().nextLong();
    }

    private Integer getRandomUninterestingCycleNumber() {
        var interestingCycles = List.of(20,60,100,140,180,220);
        Random random = new Random();
        int cycle = random.nextInt();
        while (interestingCycles.contains(cycle))
            cycle = random.nextInt();

        return  cycle;
    }
}
