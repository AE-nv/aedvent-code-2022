use std::fs;

use day03::*;

fn main() {
    let input = fs::read_to_string("input.txt").unwrap();
    let part1 = calculate_priorities_part1(&input);
    let part2 = calculate_priorities_part2(&input);
    println!("{}", part1);
    println!("{}", part2);
}
