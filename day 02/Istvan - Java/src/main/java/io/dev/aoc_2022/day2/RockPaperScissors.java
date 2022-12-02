package io.dev.aoc_2022.day2;

public enum RockPaperScissors {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    final int score;

    RockPaperScissors(int score) {
        this.score = score;
    }

    public RockPaperScissors winsFrom() {
        return RockPaperScissors.values()[this.ordinal() == 0 ? RockPaperScissors.values().length - 1 : this.ordinal() - 1];
    }

    public RockPaperScissors loosesFrom() {
        return RockPaperScissors.values()[(this.ordinal() + 1) % RockPaperScissors.values().length];
    }
}
