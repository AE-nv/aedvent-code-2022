use std::collections::HashMap;

pub fn calculate_priorities_part1(input: &str) -> usize {
    let chars_vec: Vec<char> = ('a'..='z').chain('A'..='Z').into_iter().collect();
    // HashMap with characters and corresponding prio
    let priorities: HashMap<&char, usize> = chars_vec
        .iter()
        .enumerate()
        .map(|(i, c)| (c, i + 1))
        .collect();

    let total_priority = input
        .lines()
        .map(|backpack| {
            let half = backpack.len() / 2;
            let comp1 = &backpack[..half];
            let comp2 = &backpack[half..];

            priorities
                .get(&comp1.chars().find(|item| comp2.contains(*item)).unwrap())
                .unwrap()
        })
        .sum::<usize>();

    return total_priority;
}

pub fn calculate_priorities_part2(input: &str) -> usize {
    let priorities: HashMap<char, usize> = ('a'..='z')
        .chain('A'..='Z')
        .into_iter()
        .enumerate()
        .map(|(i, c)| (c, i + 1))
        .collect();

    let lines_vec: Vec<&str> = input.lines().collect();
    let total_priority: usize = lines_vec
        .chunks(3)
        .map(|chunk| {
            priorities
                .get(
                    &chunk[0]
                        .chars()
                        .find(|item| chunk[1].contains(*item) && chunk[2].contains(*item))
                        .unwrap(),
                )
                .unwrap()
        })
        .sum();
    return total_priority;
}
