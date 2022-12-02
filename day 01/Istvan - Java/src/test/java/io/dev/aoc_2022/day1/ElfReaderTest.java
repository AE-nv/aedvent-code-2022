package io.dev.aoc_2022.day1;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class ElfReaderTest {

    @Test
    void getElfWithHighestCalorie() throws URISyntaxException, IOException {
        Path input = Paths.get(Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("testInputPartA.txt")).toURI());

        ElfReader reader = new ElfReader(input);
        Elf elf = reader.getElfWithHighestCalories();
        assertThat(elf.getCarriedCalories()).isEqualTo(24000);
    }

    @Test
    void getCaloriesOfTopThreeElves() throws URISyntaxException, IOException {
        Path input = Paths.get(Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("testInputPartA.txt")).toURI());

        ElfReader reader = new ElfReader(input);
        assertThat(reader.getCaloriesOfTopThreeElves()).isEqualTo(45000);
    }
}
