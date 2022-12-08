package io.dev.aoc_2022.day8;

import io.dev.aoc.util.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day8Test {

    @ParameterizedTest(name = "{index}. For row {0}, {1} trees are visible from the front")
    @MethodSource("generateLinesToScanFromFront")
    void countVisibleFromFront(String line, List<Integer> expectedVisibleTrees) {
        assertThat(Day8.findVisibleTreesFromFront(line)).containsAll(expectedVisibleTrees);
    }

    static Stream<Arguments> generateLinesToScanFromFront() {
        return Stream.of(
                Arguments.of("30373", List.of(0, 3)),
                Arguments.of("25512", List.of(0, 1)),
                Arguments.of("65332", List.of(0)),
                Arguments.of("33549", List.of(0, 2, 4)),
                Arguments.of("35390", List.of(0, 1, 3))
        );
    }

    @ParameterizedTest(name = "{index}. For row {0}, {1} trees are visible")
    @MethodSource("generateLinesToScanFromBothSides")
    void countVisible(String line, List<TreeLocation> expectedVisibleTrees) {
        Set<TreeLocation> visibleTrees = new HashSet<>();
        Day8.markVisibleInRow(line, 0, visibleTrees);
        assertThat(visibleTrees).containsAll(expectedVisibleTrees);
    }

    static Stream<Arguments> generateLinesToScanFromBothSides() {
        return Stream.of(
                Arguments.of("30373", List.of(new TreeLocation(0, 0), new TreeLocation(0, 3), new TreeLocation(0, 4))),
                Arguments.of("25512", List.of(new TreeLocation(0, 0), new TreeLocation(0, 1), new TreeLocation(0, 4), new TreeLocation(0, 2))),
                Arguments.of("65332", List.of(new TreeLocation(0, 0), new TreeLocation(0, 4), new TreeLocation(0, 3), new TreeLocation(0, 1))),
                Arguments.of("33549", List.of(new TreeLocation(0, 0), new TreeLocation(0, 2), new TreeLocation(0, 4))),
                Arguments.of("35390", List.of(new TreeLocation(0, 0), new TreeLocation(0, 1), new TreeLocation(0, 3)))
        );
    }

    @Test
    void partA() {
        assertThat(Day8.partA("day8Test.txt")).isEqualTo(21);
    }

    @Test
    void partB() {
        assertThat(Day8.partB("day8Test.txt")).isEqualTo(8);
        assertThat(Day8.partB("day8.txt")).isGreaterThan(526400);
    }

    @Test
    void scenicScoreSomeRandomTree() throws IOException {
        TreeLocation loc = new TreeLocation(20,43);
        List<String> field = Files.readAllLines(FileUtils.getNullSafePath("day8.txt"));
        String right = "4867536556382736847785686582546336527411161654600510522";
        String left = new StringBuilder("1011540554044611454745533573574352262876552").reverse().toString();
        String up = new StringBuilder("22223400644212257674").reverse().toString();
        String down = "868376385555494466688778898598856685756795888998486786867656862871161657353114";
        assertThat(field.get(loc.row()).charAt(loc.column())).isEqualTo('8');
        assertThat(Day8.buildViewLineLeft(loc, field)).isEqualTo(left);
        assertThat(Day8.buildViewLineRight(loc, field)).isEqualTo(right);
        assertThat(Day8.buildViewLineUp(loc, field)).isEqualTo(up);
        assertThat(Day8.buildViewLineDown(loc, field)).isEqualTo(down);
    }
    

    @ParameterizedTest(name = "{index}. From a tree of height {0} looking towards {1} one can see {2} tree(s)")
    @MethodSource("generateTreesAndViewLines")
    void calculateScoreInOneDirection(char currTree, String viewLine, long treesSeen) {
        assertThat(Day8.calculateScoreInOneDirection(currTree, viewLine)).isEqualTo(treesSeen);
    }

    static Stream<Arguments> generateTreesAndViewLines() {
        return Stream.of(
                Arguments.of('5', "3", 1L),
                Arguments.of('5', "52", 1L),
                Arguments.of('5', "12", 2L),
                Arguments.of('5', "353", 2L),
                Arguments.of('5', "353", 2L),
                Arguments.of('5', "33", 2L),
                Arguments.of('5', "49", 2L),
                Arguments.of('5', "3", 1L),
                Arguments.of('4', "317", 3L),
                Arguments.of('4',"533", 1L),
                Arguments.of('4',"9", 1L),
                Arguments.of('4',"", 0L)
        );
    }
}
