use crate::opponent::Choice as OpponentChoice;

#[derive(Debug)]
pub(crate) struct Player {
    choice: Choice,
}

impl Player {
    pub fn new(value: &str) -> Player {
        let choice = Choice::from_value(value);
        Player { choice }
    }

    pub fn from_outcome(outcome_string: &str, opponent_choice: OpponentChoice) {
        let outcome = Outcome::from_value(outcome_string);
    }

    pub fn get_choice(&self) -> &Choice {
        &self.choice
    }

    pub fn get_score(&self) -> i32 {
        self.choice.get_score()
    }

    fn get_outcome(&self, opponent_choice: OpponentChoice) -> Outcome {
        let player_choice = self.get_choice();

        match player_choice {
            Choice::ROCK => match opponent_choice {
                OpponentChoice::ROCK => Outcome::TIE,
                OpponentChoice::PAPER => Outcome::LOSS,
                OpponentChoice::SCISSORS => Outcome::WIN,
            },

            Choice::PAPER => match opponent_choice {
                OpponentChoice::ROCK => Outcome::WIN,
                OpponentChoice::PAPER => Outcome::TIE,
                OpponentChoice::SCISSORS => Outcome::LOSS,
            },

            Choice::SCISSORS => match opponent_choice {
                OpponentChoice::ROCK => Outcome::LOSS,
                OpponentChoice::PAPER => Outcome::WIN,
                OpponentChoice::SCISSORS => Outcome::TIE,
            },
        }
    }
}

#[derive(Debug)]
pub enum Choice {
    ROCK,
    PAPER,
    SCISSORS,
}

impl Choice {
    fn from_value(value: &str) -> Choice {
        match value {
            "X" => Choice::ROCK,
            "Y" => Choice::PAPER,
            "Z" => Choice::SCISSORS,
            _ => panic!("Invalid enum value {}", value),
        }
    }

    fn get_score(&self) -> i32 {
        match *self {
            Self::ROCK => 1,
            Self::PAPER => 2,
            Self::SCISSORS => 3,
        }
    }
}

enum Outcome {
    WIN,
    LOSS,
    TIE,
}

impl Outcome {
    fn from_value(value: &str) -> Outcome {
        match value {
            "X" => Outcome::LOSS,
            "Y" => Outcome::TIE,
            "Z" => Outcome::WIN,
            _ => panic!("Invalid enum value {}", value),
        }
    }
}
