package io.dev.aoc_2022.day7;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day7Test {
    /*
    1. find all directories
    2. calculate individual sizes
    3. sort
    4. calculate sum
     */

    @Test
    void parseLine_cd() {
        Command c = Day7.parseLine("$ cd /");
        assertThat(c)
                .isNotNull()
                .isInstanceOf(ChangeDirectory.class);
        assertThat(((ChangeDirectory)c).nextDir).isEqualTo("/");
    }

    @Test
    void parseLine_ls() {
        Command c = Day7.parseLine("$ ls");
        assertThat(c)
                .isNotNull()
                .isInstanceOf(ListFiles.class);
    }

    @Test
    void parseLine_directory() {
        Command c = Day7.parseLine("dir d");
        assertThat(c).isInstanceOf(AddDirectory.class);
        Directory d = ((AddDirectory)c).newDir;
        assertThat(d).isEqualTo(new Directory("d"));
    }

    @Test
    void scanFilesystem() {
        var fs = Day7.scanFileSystem("day7Test.txt");
        assertThat(fs.getCurrentDirectory().name).isEqualTo("d");
    }

    @Test
    void partA() {
        var fs = Day7.scanFileSystem("day7Test.txt");
        assertThat(Day7.partA(fs)).isEqualTo(95437L);
    }

    @Test
    void partB() {
        var fs = Day7.scanFileSystem("day7Test.txt");
        assertThat(Day7.partB(fs)).isEqualTo(24933642L);
    }
}
