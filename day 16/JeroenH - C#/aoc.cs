using QuickGraph;
using QuickGraph.Algorithms.ShortestPath;

var regex = new Regex(@"Valve (?<src>[A-Z]+) has flow rate=(?<rate>\d+); tunnel[s]? lead[s]? to valves? (?<dest>[A-Z ,]*)");
var input = File.ReadAllLines("input.txt");

var valves = (
    from line in input
    let match = regex.Match(line)
    let src = match.Groups["src"].Value
    let rate = int.Parse(match.Groups["rate"].Value)
    let dest = match.Groups["dest"].Value.Split(", ")
    select new Valve(src, rate, dest)).ToArray();

var nonzeroValves = (
    from v in valves
    where v.rate > 0
    orderby v.rate descending
    select v).ToImmutableArray();

var ShortestPaths = GetShortestPaths();

var sw = Stopwatch.StartNew();
var part1 = FindPath(30, "AA", 0, ImmutableHashSet<string>.Empty, nonzeroValves, ShortestPaths);
var part2 = FindPath2(0, "AA", nonzeroValves, valves.ToImmutableDictionary(v => v.name), (26, 0), false);
Console.WriteLine((part1, part2, sw.Elapsed));

IImmutableDictionary<(string, string), int> GetShortestPaths()
{
    var graph = (
        from valve in valves
        from destination in valve.destinations
        select new Edge<string>(valve.name, destination)).ToAdjacencyGraph<string, Edge<string>>();
    var floydWarshall = new FloydWarshallAllShortestPathAlgorithm<string, Edge<string>>(graph, _ => 1);
    floydWarshall.Compute();
    var shortestPaths = ImmutableDictionary<(string, string), int>.Empty.ToBuilder();
    foreach (string source in graph.Vertices)
        foreach (string target in graph.Vertices)
            if (floydWarshall.TryGetPath(source, target, out var path))
                shortestPaths[(source, target)] = path.Count();
    return shortestPaths.ToImmutable();
}

long FindPath(int minutes, string roomName, long time, ImmutableHashSet<string> visited, IImmutableList<Valve> valves, IImmutableDictionary<(string, string), int> shortestPaths) => (
    from valve in valves
    where !visited.Contains(valve.name)
    let needed = time + shortestPaths[(roomName, valve.name)] + 1
    where needed <= minutes
    let rate = valve.rate * (minutes - needed)
    let next = FindPath(minutes, valve.name, needed, visited.Add(valve.name), valves, shortestPaths)
    select (rate + next)).Aggregate(0L, Math.Max);

int FindPath2(int pressure, string from, ImmutableArray<Valve> valves, ImmutableDictionary<string, Valve> valvesByName, (int time, int score) previous, bool elephant)
{
    foreach (var valve in valves)
    {
        var distance = ShortestPaths[(from, valve.name)];
        var time = previous.time - distance - 1;
        var score = previous.score + (time * valvesByName[valve.name].rate);
        if (time >= 0)
        {
            pressure = Math.Max(score, pressure);
            if (valves.Length > 1)
            {
                pressure = FindPath2(pressure, valve.name, valves.Remove(valve), valvesByName, (time, score), elephant);
            }
        }
        else if (!elephant && previous.score >= pressure / 2)
        {
            pressure = FindPath2(pressure, "AA", valves, valvesByName, (26, previous.score), true);
        }
    }

    return pressure;
}

record Valve(string name, int rate, string[] destinations) : IComparable<Valve>
{
    public int CompareTo(Valve? other) => (rate == other!.rate) ? name.CompareTo(other!.name) : rate.CompareTo(other.rate);
}