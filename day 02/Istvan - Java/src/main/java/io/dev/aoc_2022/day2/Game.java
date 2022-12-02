package io.dev.aoc_2022.day2;

import static io.dev.aoc_2022.day2.Outcome.*;
import static io.dev.aoc_2022.day2.RockPaperScissors.PAPER;

public class Game {

    public static Outcome play(RockPaperScissors opponent, RockPaperScissors mine) {
        if (opponent == mine) {
            return DRAW;
        }

        if ((mine == RockPaperScissors.ROCK && opponent == RockPaperScissors.SCISSORS) ||
                (mine == RockPaperScissors.SCISSORS && opponent == PAPER) ||
                (mine == PAPER && opponent == RockPaperScissors.ROCK)) {
            return WIN;
        }

        return LOOSE;
    }

    public static int score(RockPaperScissors mine, Outcome outcome) {
        return mine.score + outcome.score;
    }

}
