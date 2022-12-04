use group::Group;
use util::read_all_lines;

mod group;
mod util;

fn main() {
    let lines = read_all_lines("./input.txt");

    let groups = lines.iter().map(|line| Group::new(line));

    let mut count = 0;
    for group in groups {
        if group.is_pair_fully_contained_in_other() {
            count += 1;
        }
    }

    println!("Total count = {count}");
}
