use game::Game;
use util::read_all_lines;

mod game;
mod matchup;
mod opponent;
mod player;
mod util;

fn main() {
    let lines = read_all_lines("./input.txt");

    let game = Game::new(lines);
    let score = game.get_score();

    println!("The total score was {}", score);
}
