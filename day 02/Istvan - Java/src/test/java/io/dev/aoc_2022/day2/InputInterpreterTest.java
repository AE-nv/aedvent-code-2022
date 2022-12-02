package io.dev.aoc_2022.day2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.dev.aoc_2022.day2.RockPaperScissors.*;
import static org.assertj.core.api.Assertions.assertThat;

public class InputInterpreterTest {


    @ParameterizedTest(name = "{index} Opponent Input {0} means {1}")
    @MethodSource("generateOppenentInput")
    void inputOpponent() {
        assertThat(InputInterpreter.interpretOpponent("A")).isEqualTo(ROCK);
    }

    static Stream<Arguments> generateOppenentInput() {
        return Stream.of(
                Arguments.of("A", ROCK),
                Arguments.of("B", PAPER),
                Arguments.of("C", SCISSORS)
        );
    }

    @ParameterizedTest(name = "{index} My Input {0} means {1}")
    @MethodSource("generateMyInput")
    void inputMine() {
        assertThat(InputInterpreter.interpretMine("Y")).isEqualTo(PAPER);
    }

    static Stream<Arguments> generateMyInput() {
        return Stream.of(
                Arguments.of("X", ROCK),
                Arguments.of("Y", PAPER),
                Arguments.of("Z", SCISSORS)
        );
    }

    @ParameterizedTest(name = "{index} With desired outcome {1}, and opponent {0}, I should play {2}")
    @MethodSource("generateDetermineMineInputs")
    void determineMine(RockPaperScissors opponent, String desiredOutcome, RockPaperScissors iPlay) {
        assertThat(InputInterpreter.determineMine(opponent, desiredOutcome)).isEqualTo(iPlay);
    }

    static Stream<Arguments> generateDetermineMineInputs() {
        return Stream.of(
                Arguments.of(ROCK, "X", SCISSORS),
                Arguments.of(ROCK, "Y", ROCK),
                Arguments.of(ROCK, "Z", PAPER),

                Arguments.of(PAPER, "X", ROCK),
                Arguments.of(PAPER, "Y", PAPER),
                Arguments.of(PAPER, "Z", SCISSORS),

                Arguments.of(SCISSORS, "X", PAPER),
                Arguments.of(SCISSORS, "Y", SCISSORS),
                Arguments.of(SCISSORS, "Z", ROCK)
        );
    }

}
