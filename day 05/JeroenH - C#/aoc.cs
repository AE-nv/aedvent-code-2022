var input = File.ReadAllLines("input.txt");
var layout = input.TakeWhile(line => !string.IsNullOrWhiteSpace(line)).Reverse().Skip(1).ToArray();
var moves = input.SkipWhile(line => !string.IsNullOrWhiteSpace(line)).Skip(1).Select(Move.Parse).ToArray();

var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));

string Part1() => Yard.Parse(layout).Apply(moves).Top();
string Part2() => Yard.Parse(layout).Apply2(moves).Top();

record Yard(ImmutableArray<ImmutableStack<char>> stacks)
{
    internal static Yard Parse(string[] layout)
    {
        var nofstacks = (layout[0].Length + 1) / 4;
        var stacks = Range(0, nofstacks).Select(_ => ImmutableStack<char>.Empty).ToImmutableArray();
        var q =
            from line in layout
            from stacknr in Range(0, nofstacks)
            let item = line[4 * stacknr + 1]
            where item != ' '
            select (stacknr, item);
        foreach (var (stacknr, item) in q)
        {
            stacks = stacks.SetItem(stacknr, stacks[stacknr].Push(item));
        }

        return new Yard(stacks);
    }

    internal Yard Apply(Move[] moves)
    {
        var builder = stacks.ToBuilder();
        foreach (var move in moves)
        {
            for (int j = 0; j < move.n; j++)
            {
                var src = builder[move.src - 1];
                var dest = builder[move.dest - 1];
                var item = src.Peek();
                builder[move.dest - 1] = dest.Push(item);
                builder[move.src - 1] = src.Pop();
            }
        }

        return this with { stacks = builder.MoveToImmutable() };
    }

    internal string Top() => string.Join("", stacks.Select(s => s.Peek()));
    internal Yard Apply2(Move[] moves)
    {
        var builder = stacks.ToBuilder();
        foreach (var move in moves)
        {
            var src = builder[move.src - 1];
            var dest = builder[move.dest - 1];
            Stack<char> crane = new();
            for (int j = 0; j < move.n; j++)
            {
                var item = src.Peek();
                crane.Push(item);
                src = src.Pop();
            }

            while (crane.Any())
            {
                dest = dest.Push(crane.Pop());
            }

            builder[move.src - 1] = src;
            builder[move.dest - 1] = dest;
        }

        return this with { stacks = builder.MoveToImmutable() };
    }
}

partial record struct Move(int n, int src, int dest)
{
    static Regex regex = GenerateRegex();
    public static Move Parse(string s) => regex.As<Move>(s)!.Value;
    [GeneratedRegex("move (?<n>\\d+) from (?<src>\\d+) to (?<dest>\\d+)", RegexOptions.Compiled)]
    private static partial Regex GenerateRegex();
}

internal static class RegexHelper
{
    public static T? As<T>(this Regex regex, string s, IFormatProvider? provider = null) where T: struct
    {
        var match = regex.Match(s);
        if (!match.Success) return default;

        var constructor = typeof(T).GetConstructors().Single();
        var parameters = constructor.GetParameters();

        var j = from p in parameters
                join m in match.Groups.OfType<Group>() on p.Name equals m.Name
                select Convert.ChangeType(m.Value, p.ParameterType, provider ?? CultureInfo.InvariantCulture);

        return (T)constructor.Invoke(j.ToArray());

    }

}