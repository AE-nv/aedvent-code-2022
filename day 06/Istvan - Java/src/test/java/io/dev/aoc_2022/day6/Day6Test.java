package io.dev.aoc_2022.day6;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day6Test {

    @Test
    void isMarker_false() {
        assertThat(Day6.isMarker("mjqj")).isFalse();
    }

    @Test
    void isMarker_true() {
        assertThat(Day6.isMarker("jpqm")).isTrue();
    }

    @ParameterizedTest(name="{index} For {0} marker should be complete at {1}")
    @MethodSource("generateFeeds")
    void positionOfFirstMarker(String feed, int expectedMarker) {
        assertThat(Day6.positionOfPackageMarker(feed)).isEqualTo(expectedMarker);
    }

    static Stream<Arguments> generateFeeds() {
        return Stream.of(
                Arguments.of("mjqjpqmgbljsphdztnvjfqwrcgsmlb",7),
                Arguments.of("bvwbjplbgvbhsrlpgdmjqwftvncz",5),
                Arguments.of("nppdvjthqldpwncqszvftbrmjlhg",6),
                Arguments.of("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",10),
                Arguments.of("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw",11)
        );
    }

    @ParameterizedTest(name="{index} For {0} message marker should be complete at {1}")
    @MethodSource("generateFeedsForMessage")
    void positionOfMessageMarkerMarker(String feed, int expectedMarker) {
        assertThat(Day6.positionOfMessageMarker(feed)).isEqualTo(expectedMarker);
    }

    static Stream<Arguments> generateFeedsForMessage() {
        return Stream.of(
                Arguments.of("mjqjpqmgbljsphdztnvjfqwrcgsmlb",19),
                Arguments.of("bvwbjplbgvbhsrlpgdmjqwftvncz",23),
                Arguments.of("nppdvjthqldpwncqszvftbrmjlhg",23),
                Arguments.of("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",29),
                Arguments.of("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw",26)
        );
    }
}
