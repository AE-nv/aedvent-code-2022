package com.selwin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

class RucksackSorter3000Test {
    @Test
    void getTotalPriorityOfMisarrangedItems() {
        List<String> rucksacks = Arrays.asList("vJrwpWtwJgWrhcsFMMfFFhFp",
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
        "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
        "ttgJtRGJQctTZtZT",
        "CrZsJsPPZsGzwwsLwLmpwMDw");
        int result = RucksackSorter3000.getTotalPriorityOfMisarrangedItems(rucksacks);
        assertEquals(result, 157);
    }

    @Test
    void getTotalPriorityOfElvesBagsSoWeCanApplyAuthenticationStickers() {
        List<String> rucksacks = Arrays.asList("vJrwpWtwJgWrhcsFMMfFFhFp",
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
        "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
        "ttgJtRGJQctTZtZT",
        "CrZsJsPPZsGzwwsLwLmpwMDw");
        int result = RucksackSorter3000.getTotalPriorityOfElvesBadgesSoWeCanApplyAuthenticationStickers(rucksacks, 3);
        assertEquals(result, 70);
    }
}
