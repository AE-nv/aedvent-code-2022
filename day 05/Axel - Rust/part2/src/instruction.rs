use regex::Regex;

#[derive(Debug)]
pub(crate) struct Instruction {
    from: usize,
    to: usize,
    amount: i32,
}

impl Instruction {
    pub fn new(input: &str) -> Instruction {
        let regex =
            Regex::new(r"move (?P<amount>\d+?) from (?P<from>\d+?) to (?P<to>\d+?)").unwrap();
        let captured = regex.captures(input).unwrap();
        let amount: i32 = captured
            .name("amount")
            .expect("no 'amount' found in instruction")
            .as_str()
            .parse::<i32>()
            .expect("error parsing 'amount'");

        let from: usize = captured
            .name("from")
            .expect("no from found in instruction")
            .as_str()
            .parse::<usize>()
            .expect("error parsing 'from'");

        let to: usize = captured
            .name("to")
            .expect("no 'to' found in instruction")
            .as_str()
            .parse::<usize>()
            .expect("error parsing 'to'");

        Instruction { from, to, amount }
    }

    pub fn get_amount(&self) -> &i32 {
        &self.amount
    }

    pub fn get_from(&self) -> &usize {
        &self.from
    }

    pub fn get_to(&self) -> &usize {
        &self.to
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn new_parses_instruction() {
        let input = "move 1 from 2 to 3";
        let result = Instruction::new(input);

        assert_eq!(result.amount, 1);
        assert_eq!(result.from, 2);
        assert_eq!(result.to, 3);
    }
}
