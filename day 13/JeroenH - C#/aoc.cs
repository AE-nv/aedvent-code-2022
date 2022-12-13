var input = File.ReadAllLines("input.txt");

var sw = Stopwatch.StartNew();

var comparer = new MyJsonArrayComparer();

var part1 = input.Where(s => !string.IsNullOrEmpty(s))
    .Select(Parse.AsJsonElement).PairWise()
    .Select((pair, index) => (result: comparer.Compare(pair.a, pair.b), index: index + 1))
    .Where(item => item.result <= 0)
    .Select(item => item.index)
    .Sum();

var dividers = "[ [[2]], [[6]] ]".AsJsonElement().ChildArray();

var part2 = input.Where(s => !string.IsNullOrEmpty(s))
    .Select(Parse.AsJsonElement)
    .Concat(dividers)
    .ToImmutableSortedSet(comparer)
    .Select((p, i) => (p, index: i + 1))
    .Join(dividers, outer => outer.p, p => p, (outer, _) => outer.index)
    .Aggregate(1, (l, r) => l * r);

Console.WriteLine((part1, part2, sw.Elapsed));

static class Parse
{
    public static JsonElement AsJsonElement(this string s) => JsonDocument.Parse(s).RootElement;
    public static JsonElement[] ChildArray(this JsonElement e) => e.EnumerateArray().ToArray();
}
class MyJsonArrayComparer : IComparer<JsonElement>
{
    public int Compare(JsonElement left, JsonElement right) => (left.ValueKind, right.ValueKind) switch
    {
        (JsonValueKind.Array, _) or (_, JsonValueKind.Array) => Compare(AsArray(left), AsArray(right)),
        (JsonValueKind.Number, JsonValueKind.Number) => left.GetInt32().CompareTo(right.GetInt32()),
        _ => 0
    };

    int Compare(JsonElement[] left, JsonElement[] right) => (
        from p in left.Zip(right)
        let result = Compare(p.First, p.Second)
        where result != 0
        select result as int?
        ).FirstOrDefault() ?? left.Length.CompareTo(right.Length);

    static JsonElement[] AsArray(JsonElement token) => token.ValueKind switch
    {
        JsonValueKind.Array => token.ChildArray(),
        JsonValueKind.Number => $"[{token.GetInt32()}]".AsJsonElement().ChildArray(),
        _ => throw new Exception("unexpected")
    };
}
