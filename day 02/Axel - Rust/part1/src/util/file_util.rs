use std::fs::File;
use std::io;
use std::io::prelude::*;

pub fn read_all_lines(filename: &str) -> Vec<String> {
    let file = File::open(&filename).unwrap();

    let reader = io::BufReader::new(file);

    let mut lines = Vec::new();
    for line in reader.lines() {
        let parsed_line = line.unwrap();
        lines.push(parsed_line);
    }
    lines
}
