package io.dev.aoc_2022.day3;

import io.dev.aoc.util.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.dev.aoc.util.FileUtils.processLinesAsStream;
import static org.assertj.core.api.Assertions.assertThat;

public class Day3Test {

    @ParameterizedTest(name = "{index} - {0} should have priority {1}")
    @MethodSource("generateLettersAndPrios")
    void determinePriority(String input, int expectedPrio) {
        assertThat(Day3.determinePriority(input)).isEqualTo(expectedPrio);
    }

    static Stream<Arguments> generateLettersAndPrios() {
        return Stream.of(
                Arguments.of("a", 1),
                Arguments.of("b", 2),
                Arguments.of("z", 26),
                Arguments.of("A", 27),
                Arguments.of("B", 28),
                Arguments.of("Z", 52)
        );
    }

    @ParameterizedTest(name = "{index} - {1} is the package error in {0}")
    @MethodSource("generateRucksackList")
    void findMatchingItem(String rucksack, String packageError) {
        assertThat(Day3.findMatchingItem(rucksack.substring(0, rucksack.length() / 2),
                rucksack.substring(rucksack.length() / 2))).isEqualTo(packageError);
    }

    static Stream<Arguments> generateRucksackList() {
        return Stream.of(
                Arguments.of("vJrwpWtwJgWrhcsFMMfFFhFp", "p"),
                Arguments.of("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "L"),
                Arguments.of("PmmdzqPrVvPwwTWBwg", "P"),
                Arguments.of("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", "v"),
                Arguments.of("ttgJtRGJQctTZtZT", "t"),
                Arguments.of("CrZsJsPPZsGzwwsLwLmpwMDw", "s")
        );
    }

    @Test
    void calculateSumOfPrios() {
        assertThat(processLinesAsStream("day3TestInput.txt", Day3::calculateSumOfPrios))
                .isEqualTo(157);
    }

    @Test
    void findBadgePrios() {
        assertThat(FileUtils.processLinesAsList("day3TestInput.txt", Day3::findBadgePrios))
                .isEqualTo(70);

    }


}
