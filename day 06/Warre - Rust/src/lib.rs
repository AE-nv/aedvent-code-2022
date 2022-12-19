use std::collections::HashSet;

pub fn compute_part1(input: &str) -> usize {
    let char_slice: Vec<char> = input.chars().collect();
    let window_index = char_slice
        .windows(4)
        .enumerate()
        .find(|(_idx, slice)| {
            let set: HashSet<&char> = slice.into_iter().collect();
            set.len() == slice.len()
        })
        .unwrap()
        .0;
    // Index of LAST char of window, +1 because count starts from 1
    window_index + 3 + 1
}

pub fn compute_part2(input: &str) -> usize {
    let char_slice: Vec<char> = input.chars().collect();
    let window_index = char_slice
        .windows(14)
        .enumerate()
        .find(|(_idx, slice)| {
            let set: HashSet<&char> = slice.into_iter().collect();
            set.len() == slice.len()
        })
        .unwrap()
        .0;
    // Index of LAST char of window, +1 because count starts from 1
    window_index + 13 + 1
}

#[cfg(test)]
mod tests {
    use super::*;

    const INPUT1: &str = "bvwbjplbgvbhsrlpgdmjqwftvncz";
    const INPUT2: &str = "mjqjpqmgbljsphdztnvjfqwrcgsmlb";

    #[test]
    fn part1_works() {
        let result = compute_part1(INPUT1);
        assert_eq!(result, 5);
    }

    #[test]
    fn part2_works() {
        let result = compute_part2(INPUT2);
        assert_eq!(result, 19);
    }
}
