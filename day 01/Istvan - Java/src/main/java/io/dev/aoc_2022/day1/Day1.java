package io.dev.aoc_2022.day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

public class Day1 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        ElfReader reader = new ElfReader(Paths.get(Objects.requireNonNull(Day1.class.getClassLoader().getResource("partA.txt")).toURI()));
        partA(reader);
        partB(reader);
    }

    private static void partA(ElfReader reader) {
        System.out.println("Elf with Highest calories: " + reader.getElfWithHighestCalories().getCarriedCalories());
    }

    private static void partB(ElfReader reader)  {
        System.out.println("Calories by top 3 elves: " + reader.getCaloriesOfTopThreeElves());
    }
}
