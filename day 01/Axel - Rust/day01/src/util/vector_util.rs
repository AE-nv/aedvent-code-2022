pub fn get_max_n_elements(vector: &Vec<i64>, n: i64) -> Vec<i64> {
    let mut input = vector.clone();
    let mut result = Vec::new();

    for _ in 0..n {
        let max = input.iter().max().unwrap_or(&0);
        result.push(*max);
        input.remove(input.iter().position(|x| x == max).unwrap());
    }
    result
}

mod tests {

    #[test]
    fn get_max_n_elements_returns_max_n() {
        let input: Vec<i64> = vec![1, 2, 3, 4, 5];

        let result = super::get_max_n_elements(&input, 3);

        assert_eq!(result.len(), 3);
        assert_eq!(result[0], 5);
        assert_eq!(result[1], 4);
        assert_eq!(result[2], 3);
    }

    #[test]
    fn get_max_n_elements_doesnt_alter_original() {
        let input: Vec<i64> = vec![1, 2, 3, 4, 5];

        super::get_max_n_elements(&input, 3);

        assert_eq!(input.len(), 5);
        assert_eq!(input[4], 5);
    }
}
