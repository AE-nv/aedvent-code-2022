package com.selwin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public final class Day3 {

    public static void main(String[] args)throws IOException, URISyntaxException {
            Path path = Path.of(Day3.class.getClassLoader().getResource("input1.txt").toURI());
            List<String> input = Files.lines(path).collect(Collectors.toList());
        System.out.println(RucksackSorter3000.getTotalPriorityOfMisarrangedItems(input));
        System.out.println(RucksackSorter3000.getTotalPriorityOfElvesBadgesSoWeCanApplyAuthenticationStickers(input, 3));
    }
}
