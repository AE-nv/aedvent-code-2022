package com.selwin;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RucksackSorter3000 {
  public static int getTotalPriorityOfMisarrangedItems(List<String> rucksacks) {
    String allMisarrangedItems = rucksacks.stream().map(RucksackSorter3000::findTheElfsMistakenItem).collect(Collectors.joining());
    return allMisarrangedItems.chars().map(RucksackSorter3000::getPriotityOfItem).reduce(0, Integer::sum);
  }

  public static int getTotalPriorityOfElvesBadgesSoWeCanApplyAuthenticationStickers(List<String> rucksacks, int elvenGroupSize) {
    int elvenGroups = rucksacks.size() / elvenGroupSize; 
    int counter = 0;
    String allBadgeItems = "";
    while(elvenGroups > counter) {
      List<String> rucksackGroup = rucksacks.subList(counter * elvenGroupSize, (counter * elvenGroupSize) + elvenGroupSize);
      String badgeItem = figureOutWhichItemTheElfBadgeIs(rucksackGroup);
      allBadgeItems = allBadgeItems.concat(badgeItem);
      counter++;
    }
    return allBadgeItems.chars().map(RucksackSorter3000::getPriotityOfItem).reduce(0, Integer::sum);
  }

  private static int getPriotityOfItem (int c) {
    // use ascii values 
    if (c > 96) {
      return c-96;
    } else {
      return c-64+26;
    }
  }

  private static String figureOutWhichItemTheElfBadgeIs(List<String> rucksacks) {
    String intersect = getIntersectOtItems(rucksacks.get(0), rucksacks.get(1));
    for (int i =2; i < rucksacks.size(); i++) {
      intersect = getIntersectOtItems(intersect, rucksacks.get(i));
    }
    return intersect;
  }

  private static String getIntersectOtItems (String setOfitems1, String setOfItems2) {
    Set<String> intersect = new HashSet<>();
    for(char c: setOfitems1.toCharArray()) {
      if(setOfItems2.indexOf(c) != -1) {
        intersect.add(String.valueOf(c));
      }
    }
    return intersect.stream().collect(Collectors.joining());
  }

  private static String findTheElfsMistakenItem(String rucksack) {
    int sizeCompartment = rucksack.length()/2;
    String compartment1 = rucksack.substring(0, sizeCompartment);
    String compartment2 = rucksack.substring(sizeCompartment);

    return getIntersectOtItems(compartment1, compartment2);
  }
}
