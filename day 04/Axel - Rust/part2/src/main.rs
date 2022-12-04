use group::Group;
use util::read_all_lines;

mod group;
mod util;

fn main() {
    let lines = read_all_lines("./input.txt");
    let groups = lines.iter().map(|line| Group::new(line)).collect();

    let fully_contained = count_fully_contained(&groups);
    let overlaps = count_overlaps(&groups);

    println!("part 1 = {fully_contained}");
    println!("part 2 = {overlaps}");
}

fn count_fully_contained(groups: &Vec<Group>) -> i32 {
    let mut count = 0;
    for group in groups {
        if group.is_pair_fully_contained_in_other() {
            count += 1;
        }
    }
    count
}

fn count_overlaps(groups: &Vec<Group>) -> i32 {
    let mut count = 0;
    for group in groups {
        if group.has_overlap() {
            count += 1;
        }
    }
    count
}
