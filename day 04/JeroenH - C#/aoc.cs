var input = File.ReadAllLines("input.txt");
var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));

int Part1() => (
    from line in input
    let pair = RangeEx.ParsePair(line)
    where pair.left.Contains(pair.right) || pair.right.Contains(pair.left)
    select pair).Count();

int Part2() => (
    from line in input
    let pair = RangeEx.ParsePair(line)
    where pair.left.Overlaps(pair.right)
    select pair).Count();

static partial class RangeEx
{
    public static bool Contains(this Range left, Range right)
        => left.Start.Value <= right.Start.Value && left.End.Value >= right.End.Value;
    public static bool Overlaps(this Range left, Range right) 
        => left.Start.Value <= right.End.Value && left.End.Value >= right.Start.Value;

    static Regex _regex = Regex();

    [GeneratedRegex(@"^(?<l1>\d+)-(?<u1>\d+),(?<l2>\d+)-(?<u2>\d+)$")]
    private static partial Regex Regex();

    public static (Range left, Range right) ParsePair(string s)
    {
        var match = _regex.Match(s);
        return (new(int.Parse(match.Groups["l1"].Value), int.Parse(match.Groups["u1"].Value)), 
                new(int.Parse(match.Groups["l2"].Value), int.Parse(match.Groups["u2"].Value)));
    }
}