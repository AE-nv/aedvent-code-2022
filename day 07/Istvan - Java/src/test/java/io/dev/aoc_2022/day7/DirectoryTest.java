package io.dev.aoc_2022.day7;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectoryTest {

    @Test
    void addFile() {
        Directory d = new Directory("d");
        d.addFile(1234);
        assertThat(d.getSizeOfDirectFiles()).isEqualTo(1234);
    }

    @Test
    void addFile_twice() {
        Directory d = new Directory("d");
        d.addFile(1234);
        d.addFile(5678);
        assertThat(d.getSizeOfDirectFiles()).isEqualTo(6912);
    }

}
