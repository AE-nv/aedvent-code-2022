package com.selwin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {
  Folder mainFolder = new Folder("/", null);
  List<Folder> folders = Collections.singletonList(mainFolder);
  Folder currentFolder;

  public void fillUpFileSystem(Path path) throws IOException {
    List<String> input = Files.lines(path).collect(Collectors.toList());
    input.forEach(this::parseCommand);
  }

  public int findSmallestFolderToDeleteToAllowUpdateInstallation() {
    int amountNeededToBeFreedUp = 30000000 + mainFolder.getFolderSize() - 70000000;

    return getFoldersWithSizeLargerThen(amountNeededToBeFreedUp, mainFolder).stream().mapToInt(Folder::getFolderSize).min().orElse(mainFolder.getFolderSize());
  }

  public int getSizeSumOfFoldersSmallerThen(int size) {
    return getFoldersWithSizeSmallerThen(size, mainFolder).stream().mapToInt(Folder::getFolderSize).sum();
  }

  private List<Folder> getFoldersWithSizeSmallerThen(int size, Folder folder) {
    List<Folder> result = folder.getFolders().stream().filter(f -> f.getFolderSize() < size).collect(Collectors.toList());
    result.addAll(folder.getFolders().stream().map(f -> getFoldersWithSizeSmallerThen(size, f)).flatMap(List::stream).collect(Collectors.toList()));
    return result;
  }

  private List<Folder> getFoldersWithSizeLargerThen(int size, Folder folder) {
    List<Folder> result = folder.getFolders().stream().filter(f -> f.getFolderSize() > size).collect(Collectors.toList());
    result.addAll(folder.getFolders().stream().map(f -> getFoldersWithSizeLargerThen(size, f)).flatMap(List::stream).collect(Collectors.toList()));
    return result;
  }

  private void parseCommand(String command) {
    if (command.startsWith("$ cd")) {
      executeNavigateCommand(command);
    } else if (command.startsWith("$ ls")) {

    } else if (command.startsWith("dir")) {
      String[] parts = command.split(" ");
      String dirName = parts[parts.length -1];
      if(!currentFolder.containsFolder(dirName))  {
        currentFolder.addFolder(new Folder(dirName, currentFolder));
      }
    } else {
      String[] parts = command.split(" ");
      String fileName = parts[1];
      Integer fileSize = Integer.parseInt(parts[0]);
      if(!currentFolder.containsFile(fileName)) {
        currentFolder.addFile(new File(fileSize, fileName));
      }
    }
  }

  private void executeNavigateCommand(String command) {
    String[] parts = command.split(" ");
    String location = parts[parts.length -1];
    switch (location) {
      case "/":
        currentFolder = mainFolder;
        break;
      case "..":
        currentFolder = currentFolder.getParent();
        break;
      default:
        currentFolder = currentFolder.getFolder(location);
        break;
    }
  }
}
