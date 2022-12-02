package com.selwin;

import java.util.List;
import java.util.stream.Collectors;

public class RockPaperScissorsCheatEngine {
  public static int getTotalTournamentPoints(List<String> inputs) {
    List<Match> matches = inputs.stream().map(RockPaperScissorsCheatEngine::convertToMatch).collect(Collectors.toList());
    return matches.stream().map(RockPaperScissorsCheatEngine::calculateRoundPoints).reduce(0, Integer::sum);
  }

  public static int getTotalTournamentPointsUsingTheUltraTopSecretStrategy(List<String> inputs) {
    List<Match> matches = inputs.stream().map(RockPaperScissorsCheatEngine::convertToMatchUsingTheUltraTopSecretStrategy)
            .collect(Collectors.toList());
    return matches.stream().map(RockPaperScissorsCheatEngine::calculateRoundPoints).reduce(0, Integer::sum);
  }

  private static Match convertToMatch(String input) {
    Match match = new Match();
    switch (input.charAt(0)) {
      case 'A':
        match.setElfInput(RockPaperScissors.ROCK);
        break;
      case 'B':
        match.setElfInput(RockPaperScissors.PAPER);
        break;
      default:
        match.setElfInput(RockPaperScissors.SCISSORS);
    }
    switch (input.charAt(2)) {
      case 'X':
        match.setMyInput(RockPaperScissors.ROCK);
        break;
      case 'Y':
        match.setMyInput(RockPaperScissors.PAPER);
        break;
      default:
        match.setMyInput(RockPaperScissors.SCISSORS);
    }

    return match;
  }

  private static Match convertToMatchUsingTheUltraTopSecretStrategy(String input) {
    Match match = new Match();
    switch (input.charAt(0)) {
      case 'A':
        match.setElfInput(RockPaperScissors.ROCK);

        break;
      case 'B':
        match.setElfInput(RockPaperScissors.PAPER);
        break;
      default:
        match.setElfInput(RockPaperScissors.SCISSORS);
    }
    switch (input.charAt(2)) {
      case 'X':
        match.setMyInput(match.getElfInput().winsFrom());
        break;
      case 'Y':
        match.setMyInput(match.getElfInput());
        break;
      default:
        match.setMyInput(match.getElfInput().losesTo());
    }

    return match;
  }

  private static int calculateRoundPoints(Match match) {
    int points = 0;
    if (match.getElfInput() == match.getMyInput()) {
      points +=3;
    } else if (match.getMyInput().isBetterThan(match.getElfInput())) {
      points +=6;
    }
    return points + match.getMyInput().getValue();
  }
}
