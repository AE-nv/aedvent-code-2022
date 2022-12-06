package com.selwin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public final class Day5 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Path path = Path.of(Day5.class.getClassLoader().getResource("input.txt").toURI());
        CrateYard crateYard = new CrateYard();
        crateYard.fillUpCreateYard(path);
        crateYard.moveCrates(CraneType.CRATE_MOVER_9000);
        System.out.println(crateYard.getTheTopCratesOfStacks());
        CrateYard crateYard2 = new CrateYard();
        crateYard2.fillUpCreateYard(path);
        crateYard2.moveCrates(CraneType.CRATE_MOVER_9001);
        System.out.println(crateYard2.getTheTopCratesOfStacks());
    }
}
