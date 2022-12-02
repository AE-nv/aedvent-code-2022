package io.dev.aoc_2022.day1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ElfTest {

    @Test
    void sortElvesByHighestCalorieFirst() {
        ElfBuilder builder = new ElfBuilder();
        List<Elf> elves = builder.addLine("1000")
                .addLine("2000")
                .addLine("   ")
                .addLine("500")
                .addLine("5000")
                .addLine("   ")
                .addLine("200")
                .build();
        elves.sort(Elf.CALORIE_COMPARATOR);
        assertThat(elves.get(0).getCarriedCalories()).isEqualTo(5500);
        assertThat(elves.get(2).getCarriedCalories()).isEqualTo(200);
    }
}
