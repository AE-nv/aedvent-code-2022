mod rucksack;
mod util;

use rucksack::Rucksack;
use util::read_all_lines;

fn main() {
    let lines = read_all_lines("input.txt");
    let rucksacks: Vec<Rucksack> = lines.iter().map(|line| Rucksack::new(line)).collect();

    let priorities: Vec<i32> = rucksacks
        .iter()
        .map(|rucksack| rucksack.get_duplicates_priority())
        .collect();

    let result: i32 = priorities.iter().sum();

    println!("Total priority of duplicates was {result}");
}
