package io.dev.aoc_2022.day2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day2 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        try (Stream<String> lines = Files.lines(Paths.get(Objects.requireNonNull(Day2.class.getClassLoader().getResource("Input.txt")).toURI()))) {
            System.out.println("Part A " + template(lines, Day2::partA));
        }

        try (Stream<String> lines = Files.lines(Paths.get(Objects.requireNonNull(Day2.class.getClassLoader().getResource("Input.txt")).toURI()))) {
            System.out.println("Part B " + template(lines, Day2::partB));
        }
    }


    public static int template(Stream<String> lines, Function<String, Integer> playFunction) {
        return lines.filter(Predicate.not(String::isBlank))
                .map(playFunction)
                .reduce(0, Integer::sum);
    }

    public static Integer partA(String line) {
        String[] parts = line.split(" ");
        RockPaperScissors mine = InputInterpreter.interpretMine(parts[1]);
        Outcome outcome = Game.play(InputInterpreter.interpretOpponent(parts[0]), mine);
        return Game.score(mine, outcome);
    }

    public static Integer partB(String line) {
        String[] parts = line.split(" ");
        RockPaperScissors opponent = InputInterpreter.interpretOpponent(parts[0]);
        RockPaperScissors mine = InputInterpreter.determineMine(opponent, parts[1]);
        Outcome outcome = Game.play(opponent, mine);
        return Game.score(mine, outcome);
    }
}
