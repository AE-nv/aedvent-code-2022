use crate::{opponent::Opponent, player::Player};

#[derive(Debug)]
pub struct Matchup {
    player: Player,
    opponent: Opponent,
}

impl Matchup {
    pub fn new(matchup_string: &str) -> Matchup {
        let strings: Vec<&str> = matchup_string.split(" ").collect();
        let opponent = Opponent::new(strings[0]);
        let player = Player::new(strings[1], opponent.get_choice());

        Matchup { player, opponent }
    }

    pub fn get_score(&self) -> i32 {
        self.player.get_score()
    }
}
