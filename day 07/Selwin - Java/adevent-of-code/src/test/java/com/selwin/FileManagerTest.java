package com.selwin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

class FileManagerTest {
    @Test
    void getSizeSumOfFoldersSmallerThen() throws URISyntaxException, IOException {        
        Path path = Path.of(Day7.class.getClassLoader().getResource("input.txt").toURI());
        FileManager fileManager = new FileManager();
        fileManager.fillUpFileSystem(path);
        int result = fileManager.getSizeSumOfFoldersSmallerThen(100000);
        assertEquals(95437, result);
    }

    @Test
    void findSmallestFolderToDeleteToAllowUpdateInstallation() throws URISyntaxException, IOException {
        Path path = Path.of(Day7.class.getClassLoader().getResource("input.txt").toURI());
        FileManager fileManager = new FileManager();
        fileManager.fillUpFileSystem(path);
        int result = fileManager.findSmallestFolderToDeleteToAllowUpdateInstallation();
        assertEquals(24933642, result);
    }
}
