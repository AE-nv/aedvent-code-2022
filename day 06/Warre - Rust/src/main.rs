use std::fs;

use day06::*;

fn main() {
    let input = fs::read_to_string("input.txt").unwrap();
    println!("{}", compute_part1(&input));
    println!("{}", compute_part2(&input));
}
