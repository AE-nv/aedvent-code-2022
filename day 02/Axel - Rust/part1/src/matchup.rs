use crate::{
    opponent::{Choice as OpponentChoice, Opponent},
    player::{Choice as PlayerChoice, Player},
};

#[derive(Debug)]
pub struct Matchup {
    player: Player,
    opponent: Opponent,
}

impl Matchup {
    pub fn new(matchup_string: &str) -> Matchup {
        let strings: Vec<&str> = matchup_string.split(" ").collect();
        let opponent = Opponent::new(strings[0]);
        let player = Player::new(strings[1]);

        Matchup { player, opponent }
    }

    pub fn get_score(&self) -> i32 {
        let outcome = self.get_outcome();
        let outcome_score = outcome.get_score();
        let choice_score = self.player.get_score();

        choice_score + outcome_score
    }

    fn get_outcome(&self) -> Outcome {
        let player_choice = self.player.get_choice();
        let opponent_choice = self.opponent.get_choice();

        match player_choice {
            PlayerChoice::ROCK => match opponent_choice {
                OpponentChoice::ROCK => Outcome::TIE,
                OpponentChoice::PAPER => Outcome::LOSS,
                OpponentChoice::SCISSORS => Outcome::WIN,
            },

            PlayerChoice::PAPER => match opponent_choice {
                OpponentChoice::ROCK => Outcome::WIN,
                OpponentChoice::PAPER => Outcome::TIE,
                OpponentChoice::SCISSORS => Outcome::LOSS,
            },

            PlayerChoice::SCISSORS => match opponent_choice {
                OpponentChoice::ROCK => Outcome::LOSS,
                OpponentChoice::PAPER => Outcome::WIN,
                OpponentChoice::SCISSORS => Outcome::TIE,
            },
        }
    }
}

enum Outcome {
    WIN,
    LOSS,
    TIE,
}

impl Outcome {
    fn get_score(&self) -> i32 {
        match *self {
            Self::WIN => 6,
            Self::LOSS => 0,
            Self::TIE => 3,
        }
    }
}

mod outcome_tests {
    #[test]
    fn get_score_when_win_6() {
        let outcome = super::Outcome::WIN;

        assert_eq!(outcome.get_score(), 6);
    }

    #[test]
    fn get_score_when_loss_0() {
        let outcome = super::Outcome::LOSS;

        assert_eq!(outcome.get_score(), 0);
    }

    #[test]
    fn get_score_when_tie_3() {
        let outcome = super::Outcome::TIE;

        assert_eq!(outcome.get_score(), 3);
    }
}

mod matchup_tests {

    #[test]
    fn creation_parses_string() {
        let input = "A X";

        let result = super::Matchup::new(input);

        assert_eq!(result.player.get_choice(), &super::PlayerChoice::ROCK);
        assert_eq!(result.opponent.get_choice(), &super::OpponentChoice::ROCK);
    }
}
