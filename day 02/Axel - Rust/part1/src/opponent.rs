#[derive(Debug)]
pub(crate) struct Opponent {
    choice: Choice,
}

impl Opponent {
    pub fn new(value: &str) -> Opponent {
        let choice = Choice::from_value(value);
        Opponent { choice }
    }

    pub fn get_choice(&self) -> &Choice {
        &self.choice
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
            "A" => Choice::ROCK,
            "B" => Choice::PAPER,
            "C" => Choice::SCISSORS,
            _ => panic!("Invalid enum value {}", value),
        }
    }
}
