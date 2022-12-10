package io.dev.aoc_2022.day10;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class CRT implements ClockObserver{

    long centerOfSprite = 1;

    long currentPixel = 0;

    StringBuilder currentLine = new StringBuilder();

    List<String> screen = new ArrayList<>(6);

    @Override
    public void notify(State state) {
        centerOfSprite = state.value();

        currentLine.append(isPixelVisible(currentPixel++, centerOfSprite)? "#":".");
        if (currentPixel % 40 == 0) {
            screen.add(currentLine.toString());
            currentPixel = 0;
            currentLine = new StringBuilder();
        }
    }

    @Override
    public BiPredicate<Long, Integer> getSelector() {
        // interested in every cycle
        return (l,i) -> true;
    }

    boolean isPixelVisible(long currentPixel, long centerOfSprite) {
        return currentPixel >= centerOfSprite -1 && currentPixel <= centerOfSprite+1;
    }

    void printScreen() {
        screen.forEach(System.out::println);
    }
}
