mod elf_group;
mod rucksack;
mod util;

use util::read_all_lines;

use crate::elf_group::ElfGroup;

fn main() {
    let lines = read_all_lines("input.txt");

    let elf_groups: Vec<ElfGroup> = get_elf_groups(&lines);

    let priorities: Vec<i32> = elf_groups
        .iter()
        .map(|rucksack| rucksack.get_badge_priority())
        .collect();

    let result: i32 = priorities.iter().sum();

    println!("Total priority of basges was {result}");
}

fn get_elf_groups(lines: &Vec<String>) -> Vec<ElfGroup> {
    let mut elf_groups: Vec<ElfGroup> = Vec::new();
    for i in (0..lines.len()).step_by(3) {
        elf_groups.push(ElfGroup::new(&lines[i], &lines[i + 1], &lines[i + 2]));
    }

    elf_groups
}
