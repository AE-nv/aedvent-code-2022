use crate::{crate_stack::CrateStack, instruction::Instruction};

pub(crate) struct Stacks {
    crate_stacks: Vec<CrateStack>,
}

impl Stacks {
    pub fn new(input_strings: Vec<&str>) -> Stacks {
        let mut crate_stacks: Vec<CrateStack> = Vec::new();
        let nb_stacks = libm::ceil((input_strings[0].len() as f64) / (4 as f64)) as usize;

        for _ in 0..nb_stacks {
            crate_stacks.push(CrateStack::new());
        }

        let tot = input_strings[0].len() as i32 + 1;
        for line in input_strings.iter().rev() {
            for i in (1..tot).step_by(4) {
                let char = String::from(line.chars().nth(i as usize).unwrap());
                if char != " " {
                    let index = ((i - 1) / 4) as usize;
                    crate_stacks[index].add_crate(&char);
                }
            }
        }

        Stacks { crate_stacks }
    }

    pub fn process_instructions(&mut self, instructions: Vec<Instruction>) {
        for instruction in instructions {
            self.process_instruction(&instruction);
        }
    }

    fn process_instruction(&mut self, instruction: &Instruction) {
        let amount = instruction.get_amount();
        let from = instruction.get_from();
        let to = instruction.get_to();

        let to_move = self.crate_stacks[from - 1].remove_top_n_crates(*amount);

        self.crate_stacks[to - 1].add_n_crates(to_move);
    }

    pub fn get_top_crates(&self) -> Vec<String> {
        let mut tops = Vec::new();
        for stack in &self.crate_stacks {
            tops.push(stack.get_top_crate());
        }

        tops
    }
}

#[cfg(test)]
mod test {
    use super::*;

    #[test]
    fn new_parses_input() {
        let top_row = "    [D]    ";
        let bottom_row = "[N] [C]    ";

        let input = vec![bottom_row, top_row];

        let stacks = Stacks::new(input);

        assert_eq!(stacks.crate_stacks.len(), 3);
        assert_eq!(stacks.crate_stacks[0].get_crates(), &vec!["N"]);
        assert_eq!(stacks.crate_stacks[1].get_crates(), &vec!["C", "D"]);
        assert!(stacks.crate_stacks[2].get_crates().is_empty());
    }

    #[test]
    fn process_instruction_moves_crate() {
        let instruction = Instruction::new("move 1 from 2 to 3");
        let top_row = "    [D]    ";
        let bottom_row = "[N] [C]    ";

        let input = vec![bottom_row, top_row];

        let mut stacks = Stacks::new(input);
        stacks.process_instruction(&instruction);

        assert_eq!(stacks.crate_stacks.len(), 3);
        assert_eq!(stacks.crate_stacks[0].get_crates(), &vec!["N"]);
        assert_eq!(stacks.crate_stacks[1].get_crates(), &vec!["C"]);
        assert_eq!(stacks.crate_stacks[2].get_crates(), &vec!["D"]);
    }

    #[test]
    fn process_instruction_moves_multiple_crates() {
        let instruction = Instruction::new("move 2 from 2 to 3");
        let top_row = "    [D]    ";
        let bottom_row = "[N] [C]    ";

        let input = vec![bottom_row, top_row];

        let mut stacks = Stacks::new(input);
        stacks.process_instruction(&instruction);

        assert_eq!(stacks.crate_stacks.len(), 3);
        assert_eq!(stacks.crate_stacks[0].get_crates(), &vec!["N"]);
        assert!(stacks.crate_stacks[1].get_crates().is_empty());
        assert_eq!(stacks.crate_stacks[2].get_crates(), &vec!["D", "C"]);
    }
}
