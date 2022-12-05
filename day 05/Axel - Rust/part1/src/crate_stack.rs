pub(crate) struct CrateStack {
    crates: Vec<String>,
}

impl CrateStack {
    pub fn new() -> CrateStack {
        CrateStack { crates: Vec::new() }
    }

    pub fn new_with_initial(initial_crate: &str) -> CrateStack {
        CrateStack {
            crates: vec![String::from(initial_crate)],
        }
    }

    pub fn add_crate(&mut self, crate_to_add: &str) {
        self.crates.push(String::from(crate_to_add));
    }

    pub fn remove_top_crate(&mut self) -> String {
        self.crates.pop().unwrap()
    }

    pub fn get_crates(&self) -> &Vec<String> {
        &self.crates
    }

    pub fn get_top_crate(&self) -> String {
        let default = String::from("");
        self.crates.last().unwrap_or(&default.clone()).to_owned()
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn new_with_initial_adds_initial_crate() {
        let initial = "A";
        let result = CrateStack::new_with_initial(initial);

        assert_eq!(result.crates, vec![initial]);
    }

    #[test]
    fn add_crate_adds_to_end_of_vector() {
        let initial = "A";
        let mut stack = CrateStack::new_with_initial(initial);

        let to_add = "B";
        stack.add_crate(to_add);

        assert_eq!(stack.crates, vec![initial, to_add]);
    }

    #[test]
    fn remove_top_crate_returns_last_element() {
        let initial = "A";
        let mut stack = CrateStack::new_with_initial(initial);

        let expected = "B";
        stack.add_crate(expected);

        let result = stack.remove_top_crate();

        assert_eq!(result, expected);
        assert_eq!(stack.crates, vec![initial]);
    }
}
