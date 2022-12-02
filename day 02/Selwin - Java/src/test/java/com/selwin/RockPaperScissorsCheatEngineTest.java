package com.selwin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

class RockPaperScissorsCheatEngineTest {
  
    @Test
    void getTotalTournamentPoints() {
        List<String> input = Arrays.asList("A Y","B X","C Z");
        int result = RockPaperScissorsCheatEngine.getTotalTournamentPoints(input);
        assertEquals(result, 15);
    }

    @Test
    void getTotalTournamentPoints_allCombos() {
        List<String> input = Arrays.asList("A X","A Y","A Z","B X","B Y","B Z","C X","C Y","C Z");
        int result = RockPaperScissorsCheatEngine.getTotalTournamentPoints(input);
        assertEquals(result, 45);
    }

    @Test
    void getTotalTournamentPointsUsingTheUltraTopSecretStrategy() {
        List<String> input = Arrays.asList("A Y","B X","C Z");
        int result = RockPaperScissorsCheatEngine.getTotalTournamentPointsUsingTheUltraTopSecretStrategy(input);
        assertEquals(result, 12);
    }
}
