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

    pub fn get_all_items(&self) -> Vec<String> {
        let compartiment1 = self.compartiment1.to_owned();
        let compartiment2 = self.compartiment2.to_owned();

        [compartiment1, compartiment2].concat()
    }

    pub fn get_item_priority(item: &str) -> i32 {
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
    fn get_all_items_returns_original_input_as_vector() {
        let input = "abcDEF";
        let expected = vec!["a", "b", "c", "D", "E", "F"];

        let rucksack = Rucksack::new(&input);

        assert_eq!(rucksack.get_all_items(), expected);
    }
}
