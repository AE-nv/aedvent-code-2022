package io.dev.aoc_2022.day5;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day5Test {

    @Test
    void partA() {
        assertThat(Day5.partA("testStack.txt", "testInstructions.txt")).isEqualTo("CMZ");
    }

    @Test
    void partB() {
        assertThat(Day5.partB("testStack.txt", "testInstructions.txt")).isEqualTo("MCD");
    }
}
