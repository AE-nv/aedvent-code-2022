package com.selwin;

public class Instruction {
  private int amountOfCrates;
  private int originStack;
  private int destinationStack;

  public Instruction(int amountOfCrates, int originStack, int destinationStack) {
    this.amountOfCrates = amountOfCrates;
    this.originStack = originStack;
    this.destinationStack = destinationStack;
  }

  public int getAmountOfCrates() {
    return amountOfCrates;
  }
  public int getOriginStack() {
    return originStack;
  }
  public int getDestinationStack() {
    return destinationStack;
  }
}
