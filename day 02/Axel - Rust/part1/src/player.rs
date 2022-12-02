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

#[derive(Debug, PartialEq, Eq)]
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

mod tests {

    #[test]
    fn choice_from_value_when_x_rock() {
        assert_eq!(super::Choice::from_value("X"), super::Choice::ROCK);
    }

    #[test]
    fn choice_from_value_when_y_paper() {
        assert_eq!(super::Choice::from_value("Y"), super::Choice::PAPER);
    }

    #[test]
    fn choice_from_value_when_z_scissors() {
        assert_eq!(super::Choice::from_value("Z"), super::Choice::SCISSORS);
    }

    #[test]
    #[should_panic(expected = "Invalid enum value A")]

    fn choice_from_value_when_unknown_panic() {
        super::Choice::from_value("A");
    }

    #[test]
    fn get_score_when_rock_1() {
        let choice = super::Choice::ROCK;

        assert_eq!(choice.get_score(), 1);
    }

    #[test]
    fn get_score_when_paper_2() {
        let choice = super::Choice::PAPER;

        assert_eq!(choice.get_score(), 2);
    }

    #[test]
    fn get_score_when_scissors_3() {
        let choice = super::Choice::SCISSORS;

        assert_eq!(choice.get_score(), 3);
    }
}
