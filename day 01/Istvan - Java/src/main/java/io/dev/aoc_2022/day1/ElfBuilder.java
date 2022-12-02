package io.dev.aoc_2022.day1;

import java.util.ArrayList;
import java.util.List;

public class ElfBuilder {
    private final List<Elf> elves = new ArrayList<>();

    private Elf currentElf;

    public ElfBuilder addLine(String line) {
        if (line.isBlank()) {
            currentElf = null;
            return this;
        }
        int calories = Integer.parseInt(line);
        if (currentElf == null) {
            currentElf = new Elf();
            elves.add(currentElf);
        }
        currentElf.addCalories(calories);
        return this;
    }

    public List<Elf> build() {
        return elves;
    }
}
