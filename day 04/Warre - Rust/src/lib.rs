use std::{ops::RangeInclusive, str::Split};

pub fn compute_part1(input: &str) -> i32 {
    input
        .lines()
        .map(|line| {
            let mut split = line.split(",");
            let (left_min, left_max) = get_bounds(&mut split);
            let (right_min, right_max) = get_bounds(&mut split);
            if (left_min >= right_min && left_max <= right_max)
                || (right_min >= left_min && right_max <= left_max)
            {
                return 1;
            }
            return 0;
        })
        .sum()
}

pub fn compute_part2(input: &str) -> i32 {
    input
        .lines()
        .map(|line| {
            let mut split = line.split(",");
            let mut left_bounds = get_bounds_range(&mut split);
            let mut right_bounds = get_bounds_range(&mut split);
            if left_bounds.any(|num| right_bounds.contains(&num))
                || right_bounds.any(|num| left_bounds.contains(&num))
            {
                return 1;
            }
            return 0;
        })
        .sum()
}

fn get_bounds(split: &mut Split<&str>) -> (u32, u32) {
    let mut bounds_split = split.next().unwrap().split("-");
    (
        bounds_split.next().unwrap().parse::<u32>().unwrap(),
        bounds_split.next().unwrap().parse::<u32>().unwrap(),
    )
}

fn get_bounds_range(split: &mut Split<&str>) -> RangeInclusive<u32> {
    let mut bounds_split = split.next().unwrap().split("-");
    bounds_split.next().unwrap().parse::<u32>().unwrap()
        ..=bounds_split.next().unwrap().parse::<u32>().unwrap()
}

#[cfg(test)]
mod tests {
    use super::*;

    const INPUT: &str = "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8";

    #[test]
    fn part1_works() {
        let result = compute_part1(INPUT);
        assert_eq!(result, 2);
    }

    #[test]
    fn part2_works() {
        let result = compute_part2(INPUT);
        assert_eq!(result, 4);
    }
}
