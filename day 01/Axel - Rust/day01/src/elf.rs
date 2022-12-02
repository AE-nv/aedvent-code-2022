#[derive(Debug)]
pub struct Elf {
    items: Vec<i64>,
}

impl Elf {
    pub fn new(item_strings: &[String]) -> Elf {
        let parsed = item_strings
            .iter()
            .map(|x| x.parse::<i64>().unwrap())
            .collect();

        Elf { items: parsed }
    }

    pub fn get_total_calories(&self) -> i64 {
        self.items.iter().sum()
    }
}

mod tests {

    #[test]
    fn new_parses_string() {
        let input = vec!["1".to_owned(), "2".to_owned(), "3".to_owned()];

        let result = super::Elf::new(&input);

        assert_eq!(result.items[0], 1);
        assert_eq!(result.items[1], 2);
        assert_eq!(result.items[2], 3);
    }

    #[test]
    fn get_total_calories_adds_items() {
        let input = vec!["1".to_owned(), "2".to_owned(), "3".to_owned()];
        let elf = super::Elf::new(&input);

        assert_eq!(elf.get_total_calories(), 6);
    }
}
