package com.selwin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stack {
  private List<String> crates = new ArrayList<>();
  
  public void addCrateOnTop(String crate) {
    crates.add(crate);
  }

  public void addCratesOnTopWithCrateMover9000(List<String> crates) {
    Collections.reverse(crates);
    crates.forEach(this::addCrateOnTop);
  }

  public void addCratesOnTopWithCrateMover9001(List<String> crates) {
    crates.forEach(this::addCrateOnTop);
  }

  public List<String> getCratesFromTop(int amount) {
    List<String> pickedUpStack = new ArrayList<>();
    while (pickedUpStack.size() < amount ) {
      pickedUpStack.add(0,crates.remove(crates.size()-1));
    }
    return pickedUpStack;
  }

  public String getTopCrate() {
    return crates.get(crates.size()-1);
  }
}
