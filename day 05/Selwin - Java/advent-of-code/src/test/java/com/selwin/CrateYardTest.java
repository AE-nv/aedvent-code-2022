package com.selwin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

class CrateYardTest {
    @Test
    void moveCrates_givenCrateMover9000() throws URISyntaxException, IOException {
        CrateYard crateYard = new CrateYard(); 
        Path path = Path.of(CrateYardTest.class.getClassLoader().getResource("input.txt").toURI());
        crateYard.fillUpCreateYard(path);
        crateYard.moveCrates(CraneType.CRATE_MOVER_9000);
        String result = crateYard.getTheTopCratesOfStacks();
        assertEquals(result, "CMZ");
    }

    @Test
    void moveCrates_givenCrateMover9001() throws URISyntaxException, IOException {
        CrateYard crateYard = new CrateYard(); 
        Path path = Path.of(CrateYardTest.class.getClassLoader().getResource("input.txt").toURI());
        crateYard.fillUpCreateYard(path);
        crateYard.moveCrates(CraneType.CRATE_MOVER_9001);
        String result = crateYard.getTheTopCratesOfStacks();
        assertEquals(result, "MCD");
    }
}
