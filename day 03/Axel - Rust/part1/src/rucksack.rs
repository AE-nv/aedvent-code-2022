use std::collections::HashSet;

use libm::floorf;

pub(crate) struct Rucksack {
    compartiment1: Vec<String>,
    compartiment2: Vec<String>,
}

impl Rucksack {
    pub fn new(item_string: &str) -> Rucksack {
        let items: Vec<String> = item_string
            .split("")
            .map(|item| String::from(item))
            .filter(|item| !String::is_empty(item))
            .collect();

        let midpoint = floorf((items.len() / 2) as f32) as usize; //Workaround since built-in floor operator is still unstable
        let compartiments = items.split_at(midpoint);

        Rucksack {
            compartiment1: compartiments.0.to_vec(),
            compartiment2: compartiments.1.to_vec(),
        }
    }

    pub fn get_duplicates_priority(&self) -> i32 {
        let duplicates = self.find_duplicates();
        let mut score = 0;
        for duplicate in duplicates {
            score += Rucksack::get_item_priority(duplicate);
        }

        score
    }

    fn find_duplicates(&self) -> HashSet<&str> {
        let mut duplicates: HashSet<&str> = HashSet::new();
        for item in &self.compartiment1 {
            if self.compartiment2.contains(&item) {
                duplicates.insert(&item);
            }
        }
        duplicates
    }

    fn get_item_priority(item: &str) -> i32 {
        let score_legend = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        let index = score_legend.find(item).expect("Invalid item.");

        (index + 1) as i32
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn new_splits_string_in_two_parts() {
        let input = "abcd".to_owned();
        let expected1 = vec!["a".to_owned(), "b".to_owned()];
        let expected2 = vec!["c".to_owned(), "d".to_owned()];

        let result = Rucksack::new(&input);

        assert_eq!(result.compartiment1, expected1);
        assert_eq!(result.compartiment2, expected2);
    }

    #[test]
    fn find_duplicates_when_none_returns_empty() {
        let input = "abcd".to_owned();
        let rucksack = Rucksack::new(&input);

        assert!(rucksack.find_duplicates().is_empty());
    }

    #[test]
    fn find_duplicates_returns_duplicates() {
        let input = "abad";
        test_find_duplicates(input, "a");
    }

    #[test]
    fn find_duplicates_when_multiple_equal_duplicates_returns_single() {
        let input = "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL";

        test_find_duplicates(input, "L");
    }

    fn test_find_duplicates(input: &str, expected_duplicate_element: &str) {
        let rucksack = Rucksack::new(&input);

        let mut expected = HashSet::new();
        expected.insert(expected_duplicate_element);

        assert_eq!(rucksack.find_duplicates(), expected);
    }

    #[test]
    fn find_duplicates_example_cases() {
        test_find_duplicates("vJrwpWtwJgWrhcsFMMfFFhFp", "p");
        test_find_duplicates("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "L");
        test_find_duplicates("PmmdzqPrVvPwwTWBwg", "P");
        test_find_duplicates("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", "v");
        test_find_duplicates("ttgJtRGJQctTZtZT", "t");
        test_find_duplicates("CrZsJsPPZsGzwwsLwLmpwMDw", "s");
    }

    #[test]
    fn get_item_priority_when_lowercase_returns_score_1_to_26() {
        assert_eq!(Rucksack::get_item_priority("a"), 1);
        assert_eq!(Rucksack::get_item_priority("p"), 16);
        assert_eq!(Rucksack::get_item_priority("v"), 22);
    }

    #[test]
    fn get_item_priority_when_uppercase_returns_score_27_to_52() {
        assert_eq!(Rucksack::get_item_priority("A"), 27);
        assert_eq!(Rucksack::get_item_priority("P"), 42);
        assert_eq!(Rucksack::get_item_priority("L"), 38);
    }

    #[test]
    fn get_item_priority_examples() {
        assert_eq!(Rucksack::get_item_priority("p"), 16);
        assert_eq!(Rucksack::get_item_priority("L"), 38);
        assert_eq!(Rucksack::get_item_priority("P"), 42);
        assert_eq!(Rucksack::get_item_priority("v"), 22);
        assert_eq!(Rucksack::get_item_priority("t"), 20);
        assert_eq!(Rucksack::get_item_priority("s"), 19);
    }

    #[test]
    fn get_duplicates_priority_when_no_duplicates_returns_0() {
        test_get_duplicates_priority("abcd", 0);
    }

    #[test]
    fn get_duplicates_priority_when_duplicates_returns_score() {
        test_get_duplicates_priority("vJrwpWtwJgWrhcsFMMfFFhFp", 16);
    }

    #[test]
    fn get_duplicates_priority_examples() {
        test_get_duplicates_priority("vJrwpWtwJgWrhcsFMMfFFhFp", 16);
        test_get_duplicates_priority("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", 38);
        test_get_duplicates_priority("PmmdzqPrVvPwwTWBwg", 42);
        test_get_duplicates_priority("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", 22);
        test_get_duplicates_priority("ttgJtRGJQctTZtZT", 20);
        test_get_duplicates_priority("CrZsJsPPZsGzwwsLwLmpwMDw", 19);
    }

    fn test_get_duplicates_priority(input: &str, expected: i32) {
        let rucksack = Rucksack::new(&input);

        assert_eq!(rucksack.get_duplicates_priority(), expected);
    }
}
