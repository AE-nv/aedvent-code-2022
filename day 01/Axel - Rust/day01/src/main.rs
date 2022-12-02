mod elf;
mod util;

use crate::elf::Elf;
use crate::util::{get_max_n_elements, read_all_lines};

fn main() {
    let lines = read_all_lines("./input.txt");
    let split_lines: Vec<&[String]> = lines
        .split(|line| line == "")
        .filter(|group| !group.is_empty())
        .collect::<Vec<&[String]>>();

    let elves: Vec<Elf> = split_lines.iter().map(|input| Elf::new(input)).collect();

    let total_calories: Vec<i64> = elves.iter().map(|elf| elf.get_total_calories()).collect();

    let max_calories = total_calories.iter().max().unwrap_or(&-1);

    println!("Maximum was {}", max_calories);

    let max_three = get_max_n_elements(&total_calories, 3);
    let total_max_three: i64 = max_three.iter().sum();
    println!("maximum three was {}", total_max_three);
}
