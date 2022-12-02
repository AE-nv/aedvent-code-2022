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

#[derive(Debug, PartialEq, Eq)]
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

mod tests {

    #[test]
    fn choice_from_value_when_x_rock() {
        assert_eq!(super::Choice::from_value("A"), super::Choice::ROCK);
    }

    #[test]
    fn choice_from_value_when_y_paper() {
        assert_eq!(super::Choice::from_value("B"), super::Choice::PAPER);
    }

    #[test]
    fn choice_from_value_when_z_scissors() {
        assert_eq!(super::Choice::from_value("C"), super::Choice::SCISSORS);
    }

    #[test]
    #[should_panic(expected = "Invalid enum value X")]

    fn choice_from_value_when_unknown_panic() {
        super::Choice::from_value("X");
    }
}
