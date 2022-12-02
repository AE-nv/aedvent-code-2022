package io.dev.aoc_2022.day1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ElfBuilderTest {

    @Test
    void addingFirstCalorieLineCreatesAnElf() {
        ElfBuilder builder = new ElfBuilder();
        builder.addLine("1000");
        List<Elf> elves = builder.build();
        assertThat(elves).hasSize(1);
        assertThat(elves.get(0).getCarriedCalories()).isEqualTo(1000);
    }

    @Test
    void addingSecondCalorieLineIncreasesCarriedCalories() {
        ElfBuilder builder = new ElfBuilder();
        builder.addLine("1000");
        builder.addLine("2000");
        List<Elf> elves = builder.build();
        assertThat(elves).hasSize(1);
        assertThat(elves.get(0).getCarriedCalories()).isEqualTo(3000);
    }

    @Test
    void addingBlankLineCreatesNewElf() {
        ElfBuilder builder = new ElfBuilder();
        builder.addLine("1000");
        builder.addLine("");
        builder.addLine("2000");
        List<Elf> elves = builder.build();
        assertThat(elves).hasSize(2);
        assertThat(elves.get(0).getCarriedCalories()).isEqualTo(1000);
        assertThat(elves.get(1).getCarriedCalories()).isEqualTo(2000);
    }
}
