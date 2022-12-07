package com.selwin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public final class Day7 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Path path = Path.of(Day7.class.getClassLoader().getResource("input.txt").toURI());
        FileManager fileManager = new FileManager();
        fileManager.fillUpFileSystem(path);
        System.out.println(fileManager.getSizeSumOfFoldersSmallerThen(100000));
        FileManager fileManager2 = new FileManager();
        fileManager2.fillUpFileSystem(path);
        System.out.println(fileManager2.findSmallestFolderToDeleteToAllowUpdateInstallation());
    }
}
