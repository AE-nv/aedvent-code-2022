package com.selwin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElvenSnackCalculator {
  public static int findTheCalorieTotalOfTheSnackiestElf(List<String> calories) {
    return Collections.max(createOrderedListOfcalorieTotals(calories));
  }

  public static int getCombineSnackTotalofTopElves(List<String> calories, int amountOfElves) {
    List<Integer> calorieTotals = createOrderedListOfcalorieTotals(calories);
    calorieTotals.sort(Collections.reverseOrder());
    int combinedCalories = 0;
    
    while(amountOfElves > 0) {
      combinedCalories += calorieTotals.remove(0);
      amountOfElves--;
    }

    return combinedCalories;
  }

  private static List<Integer> createOrderedListOfcalorieTotals(List<String> calories) {
    List<Integer> calorieTotals = new ArrayList<>();
    int totalCalories = 0;

    for(String calorieLine : calories) {
      if (calorieLine.equals("")) {
        calorieTotals.add(totalCalories);
        totalCalories = 0;
      } else {
        totalCalories += Integer.parseInt(calorieLine);
      }
    }
    // Add last combined value
    calorieTotals.add(totalCalories);

    return calorieTotals;
  }
}
