package io.dev.aoc_2022.day4;

import org.junit.jupiter.api.Test;

import static io.dev.aoc.util.FileUtils.processLinesAsStream;
import static io.dev.aoc_2022.day4.Day4.convertToSection;
import static org.assertj.core.api.Assertions.assertThat;

public class Day4Test {

    @Test
    void createSection() {
        Section s = convertToSection("2-4");
        assertThat(s.start()).isEqualTo(2);
        assertThat(s.end()).isEqualTo(4);
    }

    @Test
    void sectionFullyContains_completelyEnclosed() {
        Section outer = convertToSection("1-5");
        Section inner = convertToSection("2-3");
        assertThat(outer.fullyContains(inner)).isTrue();
    }

    @Test
    void sectionFullyContains_completelyDifferent() {
        Section outer = convertToSection("1-5");
        Section inner = convertToSection("7-9");
        assertThat(outer.fullyContains(inner)).isFalse();
    }

    @Test
    void sectionFullyContains_partialOverlap() {
        Section first = convertToSection("1-3");
        Section second = convertToSection("2-4");
        assertThat(first.fullyContains(second)).isFalse();
    }

    @Test
    void oneSectionFullyContainsOther_firstContainsSecond() {
        Section outer = convertToSection("2-7");
        Section inner = convertToSection("3-5");
        assertThat(Day4.oneSectionFullyContainsOther(outer,inner)).isTrue();
    }

    @Test
    void oneSectionFullyContainsOther_SecondContainsFirst() {
        Section outer = convertToSection("2-7");
        Section inner = convertToSection("3-5");
        assertThat(Day4.oneSectionFullyContainsOther(inner, outer)).isTrue();
    }

    @Test
    void overlaps_noOverlap() {
        assertThat(new Section(1,4).overlaps(new Section(5,6))).isFalse();
        assertThat(new Section(5,7).overlaps(new Section(1,3))).isFalse();
    }

    @Test
    void overlaps_atEnd() {
        assertThat(new Section(1,4).overlaps(new Section(4,7))).isTrue();
        assertThat(new Section(1,4).overlaps(new Section(3,7))).isTrue();
    }

    @Test
    void overlaps_atBeginning() {
        assertThat(new Section(5,7).overlaps(new Section(3,5))).isTrue();
        assertThat(new Section(5,7).overlaps(new Section(3,6))).isTrue();
    }

    @Test
    void overlaps_fistContainsSecond() {
        assertThat(new Section(3,7).overlaps(new Section(4,6))).isTrue();
        assertThat(new Section(3,7).overlaps(new Section(3,7))).isTrue();
    }

    @Test
    void overlaps_SecondContainsFirst() {
        assertThat(new Section(4,6).overlaps(new Section(3,7))).isTrue();
    }

    @Test
    void convertToLine() {
        Line l = Day4.convertToLine("2-4,6-8");
        assertThat(l.first()).isEqualTo(new Section(2,4));
        assertThat(l.second()).isEqualTo(new Section(6,8));
    }

    @Test
    void partA() {
        assertThat(processLinesAsStream("day4TestInput.txt", Day4::partA)).isEqualTo(2);
    }

    @Test
    void partB() {
        assertThat(processLinesAsStream("day4TestInput.txt", Day4::partB)).isEqualTo(4);
    }

}
