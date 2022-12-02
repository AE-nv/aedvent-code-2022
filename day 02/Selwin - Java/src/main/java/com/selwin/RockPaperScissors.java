package com.selwin;

enum RockPaperScissors {
  ROCK(1),
  PAPER(2),
  SCISSORS(3);

  private int value;

  public int getValue() {
    return value;
  }

  RockPaperScissors(int value) {
    this.value = value;
  }

  public boolean isBetterThan(RockPaperScissors other) {
    return (this == SCISSORS && SCISSORS.winsFrom()== other)
            || (this == PAPER && PAPER.winsFrom() == other)
            || (this == ROCK && ROCK.winsFrom() == other);
  }

  public RockPaperScissors winsFrom() {
      if (this== ROCK) {
        return SCISSORS;
      } else if (this == PAPER) {
        return ROCK;
      } else {
        return PAPER;
      }
  }

  public RockPaperScissors losesTo() {
    if (this == ROCK) {
      return PAPER;
    } else if (this == PAPER) {
      return SCISSORS;
    } else {
      return ROCK;
    }
  }
}