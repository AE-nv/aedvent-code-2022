using Newtonsoft.Json.Linq;

var input = File.ReadAllLines("input.txt");

var sw = Stopwatch.StartNew();

var comparer = new JTokenComparer();
var dividers = new[] { JArray.Parse("[[2]]"), JArray.Parse("[[6]]") };

var part1 = input.Where(s => !string.IsNullOrEmpty(s))
    .Select(JArray.Parse).Chunk(2)
    .Select((pair, index) => (result: comparer.Compare(pair[0], pair[1]), index: index + 1))
    .Where(item => item.result > 0)
    .Select(item => item.index)
    .ToList()
    .Sum(); 

var part2 = input.Where(s => !string.IsNullOrEmpty(s))
    .Select(JArray.Parse)
    .Concat(dividers)
    .ToImmutableSortedSet(comparer)
    .Reverse()
    .Select((p, i) => (p, index: i + 1))
    .Join(dividers, outer => outer.p, p => p, (outer, _) => outer.index)
    .Aggregate(1, (l, r) => l * r);

Console.WriteLine((part1, part2, sw.Elapsed));

class JTokenComparer : IComparer<JToken>
{
    public int Compare(JToken? left, JToken? right) => (left!.Type, right!.Type) switch
    {
        (JTokenType.Array, _) or (_, JTokenType.Array) => Compare(AsArray(left), AsArray(right)),
        (JTokenType.Integer, JTokenType.Integer) => right.Value<int>().CompareTo(left.Value<int>()),
        _ => 0
    };

    int Compare(JArray l, JArray r)
    {
        var left = l.Children().ToArray();
        var right = r.Children().ToArray();

        for (int i = 0; i < Math.Min(left.Length, right.Length); i++)
        {
            var result = Compare(left[i], right[i]);
            if (result != 0)
            {
                return result;
            }
        }
        return right.Length.CompareTo(left.Length);
    }

    static JArray AsArray(JToken token) => token.Type switch
    {
        JTokenType.Array => token.Value<JArray>()!,
        JTokenType.Integer => JArray.Parse($"[{token.Value<int>()}]"),
        _ => throw new Exception("unexpected")
    };
}