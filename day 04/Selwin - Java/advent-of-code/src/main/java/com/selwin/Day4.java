package com.selwin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public final class Day4 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Path path = Path.of(Day4.class.getClassLoader().getResource("input.txt").toURI());
        List<String> input = Files.lines(path).collect(Collectors.toList());System.out.println(ElvenWorkController.findTheAmountOfRedundantElvesToFire(input));
        System.out.println(ElvenWorkController.findAmountOfElvesThatCanBeGivenMoreWork(input));
    }
}
