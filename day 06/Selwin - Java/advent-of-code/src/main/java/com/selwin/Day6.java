package com.selwin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public final class Day6 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Path path = Path.of(Day6.class.getClassLoader().getResource("input.txt").toURI());
        List<String> input = Files.lines(path).collect(Collectors.toList());System.out.println(BrokenCommSystemAddon.parseElvenGibberishForStartPoint(input.get(0),4));
        System.out.println(BrokenCommSystemAddon.parseElvenGibberishForStartPoint(input.get(0),14));
    }
}
