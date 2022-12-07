package com.selwin;

import java.util.ArrayList;
import java.util.List;

public class Folder {
  private List<File> files = new ArrayList<>();
  private List<Folder> folders = new ArrayList<>();
  private Folder parent;
  private String name;

  public Folder(String name, Folder parent) {
    this.name = name;
    this.parent = parent;
  }
  
  public List<Folder> getFolders() {
    return folders;
  }

  public void addFile(File file) {
    files.add(file);
  }

  public void addFolder(Folder folder) {
    folders.add(folder);
  }

  public Folder getFolder(String name){
    return folders.stream().filter(folder -> folder.getName().equals(name)).findFirst().orElseThrow();
  }

  public Folder getParent() {
    return parent;
  }

  public int getFolderSize() {
    return files.stream().mapToInt(File::getSize).sum()+ 
    folders.stream().mapToInt(Folder::getFolderSize).sum();
  }

  public String getName() {
    return name;
  }

  public boolean containsFolder(String name) {
    return folders.stream().anyMatch(folder -> folder.getName().equals(name));
  }

  public boolean containsFile(String name) {
    return files.stream().anyMatch(file -> file.getName().equals(name));
  }
}
