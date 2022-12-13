var input = File.ReadAllLines("input.txt");

var sw = Stopwatch.StartNew();

var comparer = new JTokenComparer();

var part1 = input.Where(s => !string.IsNullOrEmpty(s))
    .Select(JArray.Parse).Chunk(2)
    .Select((pair, index) => (result: comparer.Compare(pair[0], pair[1]), index: index + 1))
    .Where(item => item.result <= 0)
    .Select(item => item.index)
    .Sum();

var dividers = new[] { JArray.Parse("[[2]]"), JArray.Parse("[[6]]") };

var part2 = input.Where(s => !string.IsNullOrEmpty(s))
    .Select(JArray.Parse)
    .Concat(dividers)
    .ToImmutableSortedSet(comparer)
    .Select((p, i) => (p, index: i + 1))
    .Join(dividers, outer => outer.p, p => p, (outer, _) => outer.index)
    .Aggregate(1, (l, r) => l * r);

Console.WriteLine((part1, part2, sw.Elapsed));

class JTokenComparer : IComparer<JToken>
{
    public int Compare(JToken? left, JToken? right) => (left!.Type, right!.Type) switch
    {
        (JTokenType.Array, _) or (_, JTokenType.Array) => Compare(AsArray(left), AsArray(right)),
        (JTokenType.Integer, JTokenType.Integer) => left.Value<int>().CompareTo(right.Value<int>()),
        _ => 0
    };

    int Compare(JToken[] left, JToken[] right) => (
        from p in left.Zip(right)
        let result = Compare(p.First, p.Second)
        where result != 0
        select result as int?
        ).FirstOrDefault() ?? left.Length.CompareTo(right.Length);

    static JToken[] AsArray(JToken token) => token.Type switch
    {
        JTokenType.Array => token.Value<JArray>()!.Children().ToArray(),
        JTokenType.Integer => JArray.Parse($"[{token.Value<int>()}]").Children().ToArray(),
        _ => throw new Exception("unexpected")
    };
}