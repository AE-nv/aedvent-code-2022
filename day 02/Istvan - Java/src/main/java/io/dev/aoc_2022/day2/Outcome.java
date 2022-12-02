package io.dev.aoc_2022.day2;

public enum Outcome {
    WIN(6),
    DRAW(3),
    LOOSE(0);

    final int score;

    Outcome(int score) {
        this.score = score;
    }
}
