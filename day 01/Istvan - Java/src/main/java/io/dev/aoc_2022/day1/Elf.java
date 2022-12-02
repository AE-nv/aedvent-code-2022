package io.dev.aoc_2022.day1;

import java.util.Comparator;

public class Elf {

    public static final Comparator<Elf> CALORIE_COMPARATOR =
            Comparator.comparingInt(Elf::getCarriedCalories).reversed();

    private int totalCalories;

    public int getCarriedCalories() {
        return totalCalories;
    }

    public void addCalories(int calories) {
        totalCalories += calories;
    }

}
