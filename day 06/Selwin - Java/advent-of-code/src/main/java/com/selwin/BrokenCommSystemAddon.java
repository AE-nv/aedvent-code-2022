package com.selwin;

public class BrokenCommSystemAddon {
  public static int parseElvenGibberishForStartPoint(String input, int distinctAmount) {
    String window = input.substring(0, distinctAmount);
    for (int i = distinctAmount; i <input.length(); i++) {
      if(window.chars().distinct().count() == distinctAmount) {
        return i;
      } 
      window = input.substring(i+1-distinctAmount, i+1);
    }
    return 0;
  }
}
