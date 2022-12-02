package io.dev.aoc_2022.day2;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day2Test {

    @Test
    void partA() throws URISyntaxException, IOException {
        try (Stream<String> lines = Files.lines(Paths.get(Objects.requireNonNull(Day2Test.class.getClassLoader().getResource("testInput.txt")).toURI()))) {
            assertThat(Day2.template(lines, Day2::partA)).isEqualTo(15);
        }
    }

    @Test
    void partB() throws URISyntaxException, IOException {
        try (Stream<String> lines = Files.lines(Paths.get(Objects.requireNonNull(Day2Test.class.getClassLoader().getResource("testInput.txt")).toURI()))) {
            assertThat(Day2.template(lines, Day2::partB)).isEqualTo(12);
        }
    }
}
