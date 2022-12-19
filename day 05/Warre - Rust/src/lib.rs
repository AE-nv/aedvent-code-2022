use std::collections::VecDeque;

pub fn compute_part1(input: &str) -> String {
    let (mut crates, commands) = process_input(input);

    commands.iter().for_each(|command| {
        for _i in 0..command.0 {
            let move_crate = crates[command.1].pop();
            match move_crate {
                Some(c) => crates[command.2].push(c),
                None => (),
            }
        }
    });

    return get_top_crates(crates);
}

pub fn compute_part2(input: &str) -> String {
    let (mut crates, commands) = process_input(input);
    commands.iter().for_each(|command| {
        let mut temp: VecDeque<char> = VecDeque::new();
        for _i in 0..command.0 {
            let move_crate = crates[command.1].pop();
            match move_crate {
                Some(c) => temp.push_front(c),
                None => (),
            }
        }
        crates[command.2].append(&mut Vec::from(temp));
    });
    return get_top_crates(crates);
}

pub fn process_input(input: &str) -> (Vec<Vec<char>>, Vec<(usize, usize, usize)>) {
    let input_split = input.split_once("\n\n").unwrap();
    let crate_input = input_split.0;
    let command_input = input_split.1;
    return (
        create_crate_representation(crate_input),
        process_commands(command_input),
    );
}

pub fn create_crate_representation(input: &str) -> Vec<Vec<char>> {
    // Get indices for crate columns
    let indices: Vec<usize> = input
        .lines()
        .last()
        .unwrap()
        .char_indices()
        .filter(|(_idx, c)| c.is_numeric())
        .map(|(idx, _c)| idx)
        .collect();

    // Get crate values (char) paired with their index in the string, to link to the correct column
    let crates_indices: Vec<(usize, char)> = input
        .lines()
        .map(|line| {
            line.char_indices()
                .filter(|(_idx, c)| c.is_alphabetic())
                .map(|(idx, c)| (idx, c))
        })
        .flatten()
        .collect();

    // Map crates to columns and return 2D Vec
    indices
        .iter()
        .map(|idx| {
            //let mut column: Vec<char> = Vec::new();
            crates_indices
                .clone()
                .into_iter()
                .filter(|(i, _c)| *i == *idx)
                .map(|(_i, c)| c)
                .rev()
                .collect()
        })
        .collect()
}

fn process_commands(input: &str) -> Vec<(usize, usize, usize)> {
    input
        .lines()
        .map(|line| {
            let mut split = line.split_whitespace();
            (
                split.nth(1).unwrap().parse::<usize>().unwrap(),
                split.nth(1).unwrap().parse::<usize>().unwrap() - 1,
                split.nth(1).unwrap().parse::<usize>().unwrap() - 1,
            )
        })
        .collect()
}

fn get_top_crates(crates: Vec<Vec<char>>) -> String {
    crates
        .into_iter()
        .filter(|column| column.len() >= 1)
        .map(|crate_stack| match crate_stack.last() {
            Some(c) => *c,
            None => unreachable!(),
        })
        .collect()
}

#[cfg(test)]
mod tests {
    use super::*;

    const INPUT: &str = "    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2";

    #[test]
    fn create_crate_representation_returns_reversed_2d_vec() {
        let result = create_crate_representation(INPUT.split_once("\n\n").unwrap().0);
        assert_eq!(result, vec![vec!['Z', 'N'], vec!['M', 'C', 'D'], vec!['P']]);
    }

    #[test]
    fn process_commands_returns_commands_vec() {
        let result = process_commands(INPUT.split_once("\n\n").unwrap().1);
        assert_eq!(result, vec![(1, 1, 0), (3, 0, 2), (2, 1, 0), (1, 0, 1)]);
    }

    #[test]
    fn part1_works() {
        let result = compute_part1(INPUT);
        assert_eq!(result, "CMZ");
    }

    #[test]
    fn part2_works() {
        let result = compute_part2(INPUT);
        assert_eq!(result, "MCD");
    }
}
