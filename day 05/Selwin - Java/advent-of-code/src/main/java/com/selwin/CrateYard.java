package com.selwin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CrateYard {
  private List<Stack> stacks = new ArrayList<>();
  private List<Instruction> movementInstructions = new ArrayList<>();

  public void fillUpCreateYard(Path path) throws IOException {
    List<String> input = Files.lines(path).collect(Collectors.toList());

    movementInstructions = input.stream().filter(line -> line.startsWith("move")).map(CrateYard::mapToInstruction).collect(Collectors.toList());
    
    List<String> stackLines = input.stream().filter(line -> line.contains("[")).collect(Collectors.toList());
    Collections.reverse(stackLines);
    for(int i=0; i<stackLines.get(0).length()/4 +1; i++) {
      stacks.add(new Stack());
    }
    for (String stackLine : stackLines) {
      for(int i = 1; i < stackLine.length(); i+=4) {
        if(!stackLine.substring(i, i+1).isBlank()) {
          stacks.get(i/4).addCrateOnTop(stackLine.substring(i, i+1));
        }
      }
    }
  }

  public void moveCrates(CraneType craneType) {
    movementInstructions.forEach(instruction -> processInstruction(instruction, craneType));
  }

  public String getTheTopCratesOfStacks() {
    return stacks.stream().map(Stack::getTopCrate).collect(Collectors.joining());
  }

  private void processInstruction(Instruction instruction, CraneType craneType) {
    List<String> crates = stacks.get(instruction.getOriginStack()-1).getCratesFromTop(instruction.getAmountOfCrates());

    switch (craneType) {
      case CRATE_MOVER_9000:
        stacks.get(instruction.getDestinationStack()-1).addCratesOnTopWithCrateMover9000(crates);
        break;
    
      case CRATE_MOVER_9001:
        stacks.get(instruction.getDestinationStack()-1).addCratesOnTopWithCrateMover9001(crates);
        break;
    }
  }
 
  private static Instruction mapToInstruction(String instruction) {
    List<Integer> values = Arrays.stream(instruction.replaceAll("[A-z]", "").split("  ")).map(String::strip).map(Integer::parseInt).collect(Collectors.toList());
    return new Instruction(values.get(0), values.get(1), values.get(2));
  }

}
