package io.dev.aoc_2022.day2;

public class InputInterpreter {
    public static RockPaperScissors interpretOpponent(String a) {
        return switch(a.toUpperCase().trim()) {
            case "A" -> RockPaperScissors.ROCK;
            case "B" -> RockPaperScissors.PAPER;
            case "C" -> RockPaperScissors.SCISSORS;
            default -> throw new IllegalArgumentException("Unknown input for other");
        };
    }

    public static RockPaperScissors interpretMine(String a) {
        return switch(a.toUpperCase().trim()) {
            case "X" -> RockPaperScissors.ROCK;
            case "Y" -> RockPaperScissors.PAPER;
            case "Z" -> RockPaperScissors.SCISSORS;
            default -> throw new IllegalArgumentException("Unknown input for other");
        };
    }

    public static RockPaperScissors determineMine(RockPaperScissors opponent, String desiredOutcome) {
        return switch (desiredOutcome.toUpperCase().trim()) {
            case "X" -> opponent.winsFrom();
            case "Y" -> opponent;
            case "Z" -> opponent.loosesFrom();
            default -> throw new IllegalArgumentException("Unknown input for desired outcome");
        };
    }
}
