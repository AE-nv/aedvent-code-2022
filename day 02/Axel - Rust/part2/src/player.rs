use crate::opponent::Choice as OpponentChoice;

#[derive(Debug)]
pub(crate) struct Player {
    choice: Choice,
    outcome: Outcome,
}

impl Player {
    pub fn new(outcome_string: &str, opponent_choice: &OpponentChoice) -> Player {
        let outcome = Outcome::from_value(outcome_string);
        let choice = Self::get_choice(opponent_choice, &outcome);

        Player { choice, outcome }
    }

    fn get_choice(opponent_choice: &OpponentChoice, outcome: &Outcome) -> Choice {
        match opponent_choice {
            OpponentChoice::ROCK => match outcome {
                Outcome::WIN => Choice::PAPER,
                Outcome::LOSS => Choice::SCISSORS,
                Outcome::TIE => Choice::ROCK,
            },
            OpponentChoice::PAPER => match outcome {
                Outcome::WIN => Choice::SCISSORS,
                Outcome::LOSS => Choice::ROCK,
                Outcome::TIE => Choice::PAPER,
            },
            OpponentChoice::SCISSORS => match outcome {
                Outcome::WIN => Choice::ROCK,
                Outcome::LOSS => Choice::PAPER,
                Outcome::TIE => Choice::SCISSORS,
            },
        }
    }

    pub fn get_score(&self) -> i32 {
        let choice_score = self.choice.get_score();
        let outcome_score = self.outcome.get_score();

        choice_score + outcome_score
    }
}

#[derive(Debug)]
pub enum Choice {
    ROCK,
    PAPER,
    SCISSORS,
}

impl Choice {
    fn get_score(&self) -> i32 {
        match *self {
            Self::ROCK => 1,
            Self::PAPER => 2,
            Self::SCISSORS => 3,
        }
    }
}

#[derive(Debug)]

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

    fn get_score(&self) -> i32 {
        match *self {
            Self::WIN => 6,
            Self::LOSS => 0,
            Self::TIE => 3,
        }
    }
}
