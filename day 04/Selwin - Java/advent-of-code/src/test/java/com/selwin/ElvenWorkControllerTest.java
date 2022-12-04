package com.selwin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;


class ElvenWorkControllerTest {
    @Test
    void findTheAmountOfRedundantElvesToFire() {
        int result = ElvenWorkController.findTheAmountOfRedundantElvesToFire(Arrays.asList("2-4,6-8", "2-3,4-5","5-7,7-9","2-8,3-7","6-6,4-6", "2-6,4-8"));
        assertEquals(result, 2);
    }

    @Test
    void testApp() {
        int result = ElvenWorkController.findAmountOfElvesThatCanBeGivenMoreWork(Arrays.asList("2-4,6-8", "2-3,4-5","5-7,7-9","2-8,3-7","6-6,4-6", "2-6,4-8"));
        assertEquals(result, 4);
    }
}
