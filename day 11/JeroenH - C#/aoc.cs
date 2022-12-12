using System.Numerics;

var input = File.ReadAllLines("input.txt");
var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));

ulong Part1() => DoRounds(20, false);

ulong Part2() => DoRounds(10000, true);

ulong DoRounds(int n, bool reduce)
{
    var monkeys = input.Chunk(7).Select(Monkey.Parse).ToImmutableArray();
    ulong? reducer = reduce ? monkeys.Aggregate(1ul, (a, m) => a * m.Test) : null;
    var reduction = monkeys.Aggregate(1ul, (a, m) => a * m.Test);
    foreach (var round in Range(1, n))
    {
        monkeys = DoRound(monkeys, reducer);
    }

    return monkeys.OrderByDescending(m => m.Inspections).Take(2).Aggregate(1ul, (a, x) => a * x.Inspections);
}

ImmutableArray<Monkey> DoRound(ImmutableArray<Monkey> monkeys, ulong? reducer = null)
{
    var builder = monkeys.ToBuilder();
    foreach (var monkey in monkeys)
    {
        ulong inspections = (ulong)monkey.Items.Count;
        foreach (var item in monkey.Items)
        {
            var newitem = monkey.Inspect(item);
            if (!reducer.HasValue)
                newitem /= 3;
            else
            {
                while (newitem > reducer.Value)
                    newitem %= reducer.Value;
            }

            var nextmonkey = newitem % monkey.Test == 0 ? monkey.IfTrue : monkey.IfFalse;
            builder[nextmonkey].Items.Add(newitem);
        }

        monkey.Items.Clear();
        builder[monkey.Id] = monkey with { Inspections = monkey.Inspections + inspections };
    }

    return builder.ToImmutable();
}

record Monkey(int Id, List<ulong> Items, Func<ulong, ulong> Inspect, ulong Test, int IfTrue, int IfFalse, ulong Inspections)
{
    public static Monkey Parse(string[] chunk)
    {
        var id = int.Parse(chunk[0][7..^1]);
        var items = chunk[1][18..].Split(',', StringSplitOptions.TrimEntries).Select(ulong.Parse).ToList();
        Func<ulong, ulong> operation = chunk[2][19..] switch
        {
            "old * old" => x => x * x,
            _ => chunk[2][23] switch
            {
                '+' => x => x + ulong.Parse(chunk[2][25..]),
                '*' => x => x * ulong.Parse(chunk[2][25..]),
                _ => throw new NotSupportedException($"unknown operation: {chunk[2][24]}")
            }
        };
        var test = ulong.Parse(chunk[3][21..]);
        var iftrue = int.Parse(chunk[4][28..]);
        var iffalse = int.Parse(chunk[5][29..]);
        return new Monkey(id, items, operation, test, iftrue, iffalse, 0);
    }

    public override string ToString() => $"id: {Id}, Items: {string.Join(",", Items)}, test: {Test}, true: {IfTrue}, false: {IfFalse}, Inspections: {Inspections}";
}

