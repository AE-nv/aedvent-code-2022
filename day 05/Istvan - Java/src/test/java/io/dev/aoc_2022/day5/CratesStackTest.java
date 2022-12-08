package io.dev.aoc_2022.day5;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CratesStackTest {

    @Test
    void isCrateLine_true() {
        StackReader reader = new StackReader();
        assertThat(reader.isCrateLine("    [D]")).isTrue();
        assertThat(reader.isCrateLine("[N] [C]")).isTrue();
        assertThat(reader.isCrateLine("[Z] [M] [P]")).isTrue();
    }

    @Test
    void isCrateLine_false() {
        StackReader reader = new StackReader();
        assertThat(reader.isCrateLine("    ")).isFalse();
        assertThat(reader.isCrateLine(" 1   2   3")).isFalse();
    }

    @Test
    void readCrates() {
        assertThat(StackReader.readCrates("    [D")).isEqualTo(new String[]{null, "D"});
        assertThat(StackReader.readCrates("[N] [C]")).isEqualTo(new String[]{"N","C"});
        assertThat(StackReader.readCrates("[Z] [M] [P]")).isEqualTo(new String[]{"Z","M","P"});
    }

    @Test
    void addCrates() {
        CratesStack stack = new CratesStack();
        stack.addCrates(new String[]{"A", "B", "C"});
        assertThat(stack.getTopCrateOn("1")).isEqualTo("A");
        assertThat(stack.getTopCrateOn("3")).isEqualTo("C");
        assertThat(stack.getTopCrateOn("2")).isEqualTo("B");
        assertThat(stack.getTopCrates()).isEqualTo("ABC");
    }

    @Test
    void addCrates_multiLine() {
        CratesStack stack = new CratesStack();
        stack.addCrates(new String[]{"A", "B", "C"});
        stack.addCrates(new String[]{"D", null, "E"});
        assertThat(stack.getTopCrateOn("1")).isEqualTo("D");
        assertThat(stack.getTopCrateOn("3")).isEqualTo("E");
        assertThat(stack.getTopCrateOn("2")).isEqualTo("B");
        assertThat(stack.getTopCrates()).isEqualTo("DBE");
    }

    @Test
    void createStackFromFile() {
        CratesStack stack = CratesStack.createFromFile("testStack.txt");
        assertThat(stack.getTopCrateOn("1")).isEqualTo("N");
        assertThat(stack.getTopCrateOn("2")).isEqualTo("D");
        assertThat(stack.getTopCrateOn("3")).isEqualTo("P");
        assertThat(stack.getTopCrates()).isEqualTo("NDP");
    }

    @Test
    void executeMove_1() {
        CratesStack stack = CratesStack.createFromFile("testStack.txt");
        stack.execute("move 1 from 2 to 1");
        assertThat(stack.getTopCrateOn("1")).isEqualTo("D");
        assertThat(stack.getTopCrateOn("2")).isEqualTo("C");
        assertThat(stack.getTopCrateOn("3")).isEqualTo("P");
        assertThat(stack.getTopCrates()).isEqualTo("DCP");
    }

    @Test
    void executeMove_2() {
        CratesStack stack = CratesStack.createFromFile("testStack.txt");
        stack.execute("move 2 from 2 to 3");
        assertThat(stack.getTopCrateOn("1")).isEqualTo("N");
        assertThat(stack.getTopCrateOn("2")).isEqualTo("M");
        assertThat(stack.getTopCrateOn("3")).isEqualTo("C");
        assertThat(stack.getTopCrates()).isEqualTo("NMC");
    }

    @Test
    void executeAdvancedMove_1() {
        CratesStack stack = CratesStack.createFromFile("testStack.txt");
        stack.executeAdvanced("move 1 from 2 to 1");
        assertThat(stack.getTopCrateOn("1")).isEqualTo("D");
        assertThat(stack.getTopCrateOn("2")).isEqualTo("C");
        assertThat(stack.getTopCrateOn("3")).isEqualTo("P");
        assertThat(stack.getTopCrates()).isEqualTo("DCP");
    }

    @Test
    void executeAdvancedMove_2() {
        CratesStack stack = CratesStack.createFromFile("testStack.txt");
        stack.executeAdvanced("move 2 from 2 to 3");
        assertThat(stack.getTopCrateOn("1")).isEqualTo("N");
        assertThat(stack.getTopCrateOn("2")).isEqualTo("M");
        assertThat(stack.getTopCrateOn("3")).isEqualTo("D");
        assertThat(stack.getTopCrates()).isEqualTo("NMD");
    }
}
