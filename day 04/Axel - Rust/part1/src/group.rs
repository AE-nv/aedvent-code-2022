pub(crate) struct Group {
    section1: Vec<i32>,
    section2: Vec<i32>,
}

impl Group {
    pub fn new(input_string: &str) -> Group {
        let split_input: Vec<&str> = input_string.split(",").collect();
        if split_input.len() != 2 {
            panic!(
                "Unexpected number of elves in group: {}, expected 2 groups, but found {}",
                input_string,
                split_input.len()
            );
        }

        let mut sections: Vec<Vec<i32>> = Vec::new();
        for unparsed in split_input {
            let boundaries: Vec<i32> = unparsed
                .split("-")
                .map(|x| x.parse().expect("Not a valid number {x}"))
                .collect();
            sections.push((boundaries[0]..=boundaries[1]).into_iter().collect());
        }

        Group {
            section1: sections[0].to_owned(),
            section2: sections[1].to_owned(),
        }
    }

    pub fn is_pair_fully_contained_in_other(&self) -> bool {
        let second_overlaps_first = self
            .section2
            .iter()
            .all(|item| self.section1.contains(item));

        let first_overlaps_second = self
            .section1
            .iter()
            .all(|item| self.section2.contains(item));

        second_overlaps_first || first_overlaps_second
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn new_parses_input() {
        let input = "2-4,6-8";

        let expected1 = vec![2, 3, 4];
        let expected2 = vec![6, 7, 8];

        let result = Group::new(&input);

        assert_eq!(result.section1, expected1);
        assert_eq!(result.section2, expected2);
    }

    #[test]
    fn is_pair_fully_contained_in_other_when_no_overlap_false() {
        test_is_pair_fully_contained_in_other("2-4,6-8", false);
    }

    #[test]
    fn is_pair_fully_contained_in_other_when_partial_overlap_false() {
        test_is_pair_fully_contained_in_other("5-7,7-9", false);
    }

    #[test]
    fn is_pair_fully_contained_in_other_when_1_overlaps_2_true() {
        test_is_pair_fully_contained_in_other("2-8,3-7", true);
    }

    #[test]
    fn is_pair_fully_contained_in_other_when_2_overlaps_1_true() {
        test_is_pair_fully_contained_in_other("3-7,2-8", true);
    }

    fn test_is_pair_fully_contained_in_other(input: &str, expected: bool) {
        let group = Group::new(input);
        assert_eq!(group.is_pair_fully_contained_in_other(), expected);
    }
}
