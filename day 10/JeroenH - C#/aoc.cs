var input = File.ReadAllLines("input.txt");

var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));

long Part1() => (
    from c in Cycles().Select((x, i) => (x, n: i + 1))
    where new[] { 20, 60, 100, 140, 180, 220 }.Contains(c.n)
    select c.x * c.n).Sum();

IEnumerable<int> Cycles()
{
    var x = 1;
    var instructions =
        from line in input
        let instruction = ToInstruction(line)
        select instruction;
    foreach (var (add, n, line) in instructions)
    {
        for (int i = 0; i < n; i++)
        {
            yield return x;
        }

        x += add;
    }
}

string Part2() =>	(
    from cycle in Cycles().Select((x, i) => (x, i))
    let linenr = cycle.i / 40
    let position = cycle.i % 40
    let c = Range(cycle.x - 1, 3).Contains(position) ? '#' : '.'
    group c by linenr
    ).Aggregate(new StringBuilder().AppendLine(), (sb, line) => sb.AppendLine(new string(line.ToArray()))).ToString();

(int add, int n, string instruction) ToInstruction(string line) => line[0..4] switch
{
    "noop" => (0, 1, line),
    "addx" => (int.Parse(line[5..]), 2, line),
    _ => throw new NotImplementedException("unrecognized instruction")
};