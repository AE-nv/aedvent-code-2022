package io.dev.aoc_2022.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class ElfReader {

    private final List<Elf> elves;
    public ElfReader(Path input) throws IOException {
        ElfBuilder builder = new ElfBuilder();
        try(Stream<String> stream = Files.lines(input)) {
            stream.forEach(builder::addLine);
        }
        this.elves = builder.build();
    }

    public Elf getElfWithHighestCalories() {
        elves.sort(Elf.CALORIE_COMPARATOR);
        return elves.get(0);
    }


    public int getCaloriesOfTopThreeElves() {
        elves.sort(Elf.CALORIE_COMPARATOR);
        return elves.stream()
                .limit(3)
                .map(Elf::getCarriedCalories)
                .reduce(0, Integer::sum);
    }
}
