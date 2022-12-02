package io.dev.aoc_2022.day2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.dev.aoc_2022.day2.Outcome.*;
import static io.dev.aoc_2022.day2.RockPaperScissors.*;
import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    @ParameterizedTest(name="{index} Their {0} vs my {1} results in {2}")
    @MethodSource("provideGameOptions")
    void gameVariants(RockPaperScissors opponent, RockPaperScissors mine, Outcome expectedOutcome) {
        assertThat(Game.play(opponent, mine)).isEqualTo(expectedOutcome);
    }

    static Stream<Arguments> provideGameOptions() {
        return Stream.of(
                Arguments.of(SCISSORS, ROCK, WIN),
                Arguments.of(SCISSORS, SCISSORS, DRAW),
                Arguments.of(SCISSORS, PAPER, LOOSE),

                Arguments.of(PAPER, ROCK, LOOSE),
                Arguments.of(PAPER, SCISSORS, WIN),
                Arguments.of(PAPER, PAPER, DRAW),

                Arguments.of(ROCK, ROCK, DRAW),
                Arguments.of(ROCK, SCISSORS, LOOSE),
                Arguments.of(ROCK, PAPER, WIN)
        );
    }

    @ParameterizedTest(name="{index} Played {0} and {1} gives a score of {2}")
    @MethodSource("provideScoringVarients")
    void gameVariants(RockPaperScissors played, Outcome outcome, Integer expectedScore) {
        assertThat(Game.score(played, outcome)).isEqualTo(expectedScore);
    }

    static Stream<Arguments> provideScoringVarients() {
        return Stream.of(
                Arguments.of(ROCK, WIN, 7),
                Arguments.of(ROCK, DRAW, 4),
                Arguments.of(ROCK, LOOSE, 1),

                Arguments.of(PAPER, WIN, 8),
                Arguments.of(PAPER, DRAW, 5),
                Arguments.of(PAPER, LOOSE, 2),

                Arguments.of(SCISSORS, WIN, 9),
                Arguments.of(SCISSORS, DRAW, 6),
                Arguments.of(SCISSORS, LOOSE, 3)
        );
    }
}
