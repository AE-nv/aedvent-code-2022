use std::fs;

fn main() {
    let input = fs::read_to_string("input.txt").unwrap();
    println!("{:?}", max_calories(&input));
    println!("{:?}", top_n_calories(&input, 3));
}

fn max_calories(input: &str) -> u32 {
    get_totals(input).into_iter().max().unwrap()
}

fn top_n_calories(input: &str, n: u32) -> u32 {
    let mut totals = get_totals(input);
    let mut i = 0;
    let mut max_cals = Vec::new();
    while i < n {
        max_cals.push(
            totals.remove(
                totals
                    .iter()
                    .enumerate()
                    .max_by_key(|&(_, cals)| cals)
                    .unwrap()
                    .0,
            ),
        );
        i += 1
    }
    max_cals.into_iter().sum()
}

fn get_totals(input: &str) -> Vec<u32> {
    input
        .split("\n\n")
        .map(|elf_string| {
            elf_string
                .split("\n")
                .into_iter()
                .map(|calories| calories.parse::<u32>().unwrap_or(0))
                .sum()
        })
        .collect()
}
