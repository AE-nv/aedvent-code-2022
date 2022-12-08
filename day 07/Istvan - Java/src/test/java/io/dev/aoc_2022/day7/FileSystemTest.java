package io.dev.aoc_2022.day7;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileSystemTest {

    @Test
    void applyCd_root() {
        FileSystem fs = new FileSystem();
        Command c = new ChangeDirectory("/");
        c.applyTo(fs);
        assertThat(fs.getCurrentDirectory().name).isEqualTo("/");
    }

    @Test
    void applyCd_twice() {
        FileSystem fs = new FileSystem();
        new ChangeDirectory("/").applyTo(fs);
        new AddDirectory("aDir").applyTo(fs);
        new ChangeDirectory("aDir").applyTo(fs);
        assertThat(fs.getCurrentDirectory()).isEqualTo(new Directory("aDir"));
    }

    @Test
    void applyCd_twice_and_back() {
        FileSystem fs = new FileSystem();
        new ChangeDirectory("/").applyTo(fs);
        new AddDirectory("aDir").applyTo(fs);
        new ChangeDirectory("aDir").applyTo(fs);
        new ChangeDirectory("..").applyTo(fs);
        assertThat(fs.getCurrentDirectory().name).isEqualTo("/");
    }

    @Test
    void addDirectory() {
        FileSystem fs = new FileSystem();
        new ChangeDirectory("/").applyTo(fs);
        new AddDirectory("subDir").applyTo(fs);
        assertThat(fs.getCurrentDirectory().getSubDirectories()).contains(new Directory("subDir"));
    }
}
