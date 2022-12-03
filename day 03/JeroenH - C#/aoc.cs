var input = File.ReadAllLines("input.txt");
var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));
int Part1() => (
    from line in input
    let half = line.Length / 2
    let part1 = line.Substring(0, half)
    let part2 = line.Substring(half)
    from common in part1.Intersect(part2)
    select Priority(common)).Sum();
int Part2() => (
    from chunk in input.Chunk(3) from common in chunk[0].Intersect(chunk[1]).Intersect(chunk[2]).Distinct() let priority = Priority(common) select priority).Sum();
int Priority(char c) => c switch
{
    >= 'a' and <= 'z' => c - 'a' + 1,
    >= 'A' and <= 'Z' => c - 'A' + 27,
    _ => throw new InvalidOperationException("unexpected character")
};