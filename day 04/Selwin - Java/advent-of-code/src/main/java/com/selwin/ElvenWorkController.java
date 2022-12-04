package com.selwin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ElvenWorkController {
  public static int findTheAmountOfRedundantElvesToFire(List<String> workOrders) {
    return (int) workOrders.stream().map(ElvenWorkController::isOneOfTheElvesRedundant).filter(p -> p== true).count();
  }

  public static int findAmountOfElvesThatCanBeGivenMoreWork(List<String> workOrders) {
    return (int) workOrders.stream().map(ElvenWorkController::isOneOfTheElvesRedoingWork).filter(p -> p == true).count();
  }

  public static boolean isOneOfTheElvesRedundant(String workOrder) {
    String[] elvenOrders = workOrder.split(",");
    List<Integer> instructionsElf1 = mapToInstructions(elvenOrders[0]);
    List<Integer> instructionsElf2 = mapToInstructions(elvenOrders[1]);

    if (instructionsElf1.get(0) <= instructionsElf2.get(0) && instructionsElf1.get(1) >= instructionsElf2.get(1))  {
      return true;
    } else if(instructionsElf1.get(0) >= instructionsElf2.get(0) && instructionsElf1.get(1) <= instructionsElf2.get(1)) {
      return true;
    }

    return false;
  }

  public static boolean isOneOfTheElvesRedoingWork(String workOrder) {
    String[] elvenOrders = workOrder.split(",");
    List<Integer> instructionsElf1 = mapToInstructions(elvenOrders[0]);
    List<Integer> instructionsElf2 = mapToInstructions(elvenOrders[1]);

    if (instructionsElf1.get(0) <= instructionsElf2.get(0) && instructionsElf2.get(0) <= instructionsElf1.get(1))  {
      return true;
    } else if(instructionsElf2.get(0) <= instructionsElf1.get(0) && instructionsElf1.get(0) <= instructionsElf2.get(1)) {
      return true;
    }

    return false;
  }

  private static List<Integer> mapToInstructions(String elvenOrders) {
    return Arrays.asList(elvenOrders.split("-")).stream().map(Integer::parseInt).collect(Collectors.toList());
  }
}
