use crate::matchup::Matchup;

pub struct Game {
    rounds: Vec<Matchup>,
}

impl Game {
    pub fn new(string_lines: Vec<String>) -> Game {
        let mut result = Vec::new();
        for line in string_lines {
            result.push(Matchup::new(&line));
        }

        Game { rounds: result }
    }

    pub fn get_score(&self) -> i32 {
        let mut result = 0;
        for matchup in &self.rounds {
            result += matchup.get_score();
        }
        result
    }
}
