#[derive(Debug)]
pub(crate) struct Player {
    choice: Choice,
}

impl Player {
    pub fn new(value: &str) -> Player {
        let choice = Choice::from_value(value);
        Player { choice }
    }

    pub fn get_choice(&self) -> &Choice {
        &self.choice
    }

    pub fn get_score(&self) -> i32 {
        self.choice.get_score()
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
