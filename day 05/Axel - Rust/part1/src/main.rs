use util::read_all_lines;

use crate::{instruction::Instruction, stacks::Stacks};

mod crate_stack;
mod instruction;
mod stacks;
mod util;

fn main() {
    let lines = read_all_lines("./input.txt");

    let mut initial_reversed: Vec<&str> = Vec::new();
    let mut instructions: Vec<Instruction> = Vec::new();

    let mut instructions_reached: bool = false;
    for line in &lines {
        if line.starts_with(" 1") {
            instructions_reached = true;
            continue;
        }
        if !instructions_reached {
            initial_reversed.push(&line);
        } else if !line.is_empty() {
            instructions.push(Instruction::new(&line));
        }
    }

    let mut stacks = Stacks::new(initial_reversed);
    stacks.process_instructions(instructions);

    let tops = stacks.get_top_crates();
    println!("top crates: {tops:?}");
}
