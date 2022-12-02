package com.selwin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public final class Day1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Path path = Path.of(Day1.class.getClassLoader().getResource("input1.txt").toURI());
        List<String> input = Files.lines(path).collect(Collectors.toList());

        int result = ElvenSnackCalculator.findTheCalorieTotalOfTheSnackiestElf(input);
        System.out.println(result);
        System.out.println(ElvenSnackCalculator.getCombineSnackTotalofTopElves(input, 3));
    }
}
