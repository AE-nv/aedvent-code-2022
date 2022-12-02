package com.selwin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public final class Day2 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Path path = Path.of(Day2.class.getClassLoader().getResource("input1.txt").toURI());
        List<String> input = Files.lines(path).collect(Collectors.toList());

        System.out.println(RockPaperScissorsCheatEngine.getTotalTournamentPoints(input));
        System.out.println(RockPaperScissorsCheatEngine.getTotalTournamentPointsUsingTheUltraTopSecretStrategy(input));
    }
}
