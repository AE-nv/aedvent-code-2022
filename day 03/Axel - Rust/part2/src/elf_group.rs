use crate::rucksack::Rucksack;

pub(crate) struct ElfGroup {
    rucksacks: [Rucksack; 3],
}

impl ElfGroup {
    pub fn new(input1: &str, input2: &str, input3: &str) -> ElfGroup {
        let rucksacks = [
            Rucksack::new(input1),
            Rucksack::new(input2),
            Rucksack::new(input3),
        ];

        ElfGroup { rucksacks }
    }

    pub fn get_badge_priority(&self) -> i32 {
        let badge = self.get_badge();

        Rucksack::get_item_priority(&badge)
    }

    fn get_badge(&self) -> String {
        let items1 = self.rucksacks[0].get_all_items();
        let items2 = self.rucksacks[1].get_all_items();
        let items3 = self.rucksacks[2].get_all_items();

        for item in items1 {
            if items2.contains(&item) && items3.contains(&item) {
                return item;
            }
        }

        panic!("No badge found");
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn get_badge_returns_common_item() {
        let group = ElfGroup::new("abc", "aBC", "aBc");

        assert_eq!(group.get_badge(), "a");
    }

    #[test]
    fn get_badge_example1() {
        let group = ElfGroup::new(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
        );

        assert_eq!(group.get_badge(), "r");
    }

    #[test]
    fn get_badge_example2() {
        let group = ElfGroup::new(
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw",
        );

        assert_eq!(group.get_badge(), "Z");
    }
}
