package io.dev.aoc_2022.day9;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day9Test {

    private final Position tail = new Position(0, 0);

    @Test
    void determineNewPosition_samePlace() {
        var head = new Position(0, 0);
        assertThat(tail.determineNewPosition(head)).isEqualTo(tail);
    }

    @ParameterizedTest(name = "{index}. Tail shouldn't move when head is only one step away")
    @MethodSource("generateHeadPositionsAround_0_0")
    void determineNewPosition_adjacent(Position head) {
        assertThat(tail.determineNewPosition(head)).isEqualTo(tail);
    }

    static Stream<Arguments> generateHeadPositionsAround_0_0() {
        return Stream.of(
                Arguments.of(new Position(0, 1)),
                Arguments.of(new Position(-1, 1)),
                Arguments.of(new Position(-1, 0)),
                Arguments.of(new Position(-1, -1)),
                Arguments.of(new Position(0, -1)),
                Arguments.of(new Position(1, -1)),
                Arguments.of(new Position(1, 0)),
                Arguments.of(new Position(1, 1)),
                Arguments.of(new Position(0,0))
        );
    }

    @Test
    void determineNewPosition_2StepsRight() {
        var head = new Position(2, 0);
        assertThat(tail.determineNewPosition(head)).isEqualTo(new Position(1, 0));
    }

    @Test
    void determineNewPosition_2StepsLeft() {
        var head = new Position(-2, 0);
        assertThat(tail.determineNewPosition(head)).isEqualTo(new Position(-1, 0));
    }

    @Test
    void determineNewPosition_2StepsUp() {
        var head = new Position(0, 2);
        assertThat(tail.determineNewPosition(head)).isEqualTo(new Position(0, 1));
    }

    @Test
    void determineNewPosition_2StepsDown() {
        var head = new Position(0, -2);
        assertThat(tail.determineNewPosition(head)).isEqualTo(new Position(0, -1));
    }

    @ParameterizedTest(name = "{index}. Diagonal jump to {1} if head moves to {0}")
    @MethodSource("generateDiagonalLeavePositions")
    void determineNewPosition_leaveDiagonalTouchPoint(Position head, Position expected) {
        assertThat(tail.determineNewPosition(head)).isEqualTo(expected);
    }

    static Stream<Arguments> generateDiagonalLeavePositions() {
        return Stream.of(
                Arguments.of(new Position(2, 1), new Position(1, 1)),
                Arguments.of(new Position(1, 2), new Position(1, 1)),
                Arguments.of(new Position(-1, 2), new Position(-1, 1)),
                Arguments.of(new Position(-2, 1), new Position(-1, 1)),
                Arguments.of(new Position(2, -1), new Position(1, -1)),
                Arguments.of(new Position(1, -2), new Position(1, -1)),
                Arguments.of(new Position(-1, -2), new Position(-1, -1)),
                Arguments.of(new Position(-2, -1), new Position(-1, -1)),
                Arguments.of(new Position(2, 2), new Position(1, 1))
        );
    }

    @Test
    void bugOnMoveDiagonal() {
        Position head = new Position(2,4);
        Position tail = new Position(4,3);
        assertThat(tail.determineNewPosition(head)).isEqualTo(new Position(3,4));
    }

    @ParameterizedTest()
    @MethodSource("generateMoves")
    void move(char direction, Position expected) {
        assertThat(Day9.move(tail, direction)).isEqualTo(expected);
    }

    static Stream<Arguments> generateMoves() {
        return Stream.of(
                Arguments.of('U', new Position(0, 1)),
                Arguments.of('L', new Position(-1, 0)),
                Arguments.of('D', new Position(0, -1)),
                Arguments.of('R', new Position(1, 0))
        );
    }

    @Test
    void partA() throws IOException {
        assertThat(Day9.partA("day9Test.txt")).isEqualTo(13);
    }

    @Test
    void partB() throws IOException {
        assertThat(Day9.partB("day9Test.txt")).isEqualTo(1);
        assertThat(Day9.partB("day9LargerTest.txt")).isEqualTo(36);
        assertThat(Day9.partB("day9.txt")).isLessThan(2626);
    }

}
