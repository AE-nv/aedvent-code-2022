pub fn get_max_n_elements(vector: Vec<i64>, n: i64) -> Vec<i64> {
    let mut input = vector.clone();
    let mut result = Vec::new();

    for _ in 0..n {
        let max = input.iter().max().unwrap_or(&0);
        result.push(*max);
        input.remove(input.iter().position(|x| x == max).unwrap());
    }
    result
}
