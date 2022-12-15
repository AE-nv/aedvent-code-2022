use std::fs;

fn main() {
    let input = fs::read_to_string("input.txt").unwrap();
    println!("{}", part1(&input));
    println!("{}", part2(&input));
}

fn part1(input: &str) -> i32 {
    let score: i32 = input
        .lines()
        .map(|line| {
            line.split(" ")
                .map(|hand| {
                    let mut num_hand: i32 = 0;
                    match hand {
                        "A" | "X" => num_hand = 1,
                        "B" | "Y" => num_hand = 2,
                        "C" | "Z" => num_hand = 3,
                        _ => {}
                    }
                    num_hand
                })
                .collect()
        })
        .map(|game: Vec<i32>| {
            let opponent = game[0];
            let player = game[1];
            let outcome = match opponent - player {
                0 => 3,
                -1 | 2 => 6,
                -2 | 1 => 0,
                _ => unreachable!(),
            };
            player + outcome
        })
        .sum();
    return score;
}

fn part2(input: &str) -> i32 {
    let total_score: i32 = input
        .lines()
        .map(|line| {
            line.split(" ")
                .map(|hand| {
                    let mut num_hand: i32 = 0;
                    match hand {
                        "A" | "X" => num_hand = 0,
                        "B" | "Y" => num_hand = 1,
                        "C" | "Z" => num_hand = 2,
                        _ => {}
                    }
                    num_hand
                })
                .collect()
        })
        .map(|game: Vec<i32>| {
            let opponent = game[0];
            let outcome = game[1];
            let player = get_player_hand(opponent, outcome);
            let game_score = (player + 1) + (outcome * 3);
            return game_score;
        })
        .sum();
    return total_score;
}

fn get_player_hand(opponent: i32, outcome: i32) -> i32 {
    match outcome {
        0 => match opponent {
            0 => 2,
            1 => 0,
            2 => 1,
            _ => unreachable!(),
        },
        1 => opponent,
        2 => match opponent {
            0 => 1,
            1 => 2,
            2 => 0,
            _ => unreachable!(),
        },
        _ => unreachable!(),
    }
}
