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
