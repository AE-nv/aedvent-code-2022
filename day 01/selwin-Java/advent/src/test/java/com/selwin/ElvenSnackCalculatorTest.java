package com.selwin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

class ElvenSnackCalculatorTest {
  
    @Test
    void findTheCalorieTotalOfTheSnackiestElf() {
        List<String> input = Arrays.asList("1000","2000","3000","","4000","","5000","6000","","7000","8000","9000","","10000");
        int result = ElvenSnackCalculator.findTheCalorieTotalOfTheSnackiestElf(input);
        assertEquals(result, 24000);
    }

    @Test
    void getCombineSnackTotalofTopElves() {
        List<String> input = Arrays.asList("1000","2000","3000","","4000","","5000","6000","","7000","8000","9000","","10000");
        int result = ElvenSnackCalculator.getCombineSnackTotalofTopElves(input, 3);
        assertEquals(result, 45000);
    }
}
