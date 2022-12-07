package com.selwin;

public class File {
  private Integer size;
  private String name;

  public File(Integer size, String name) {
    this.size = size;
    this.name = name;
  }

  public Integer getSize() {
    return size;
  }

  public String getName() {
    return name;
  }
}
