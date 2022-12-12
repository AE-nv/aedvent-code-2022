using QuickGraph;
using QuickGraph.Algorithms;

var input = File.ReadAllLines("input.txt");

var sw = Stopwatch.StartNew();
var part1 = Grid.Parse(input).CountSteps();
var part2 = Grid.Parse(input).FewestSteps();
Console.WriteLine((part1, part2, sw.Elapsed));

class Grid
{
    int[,] _grid;
    public Grid(int[,] grid) => _grid = grid;
    public (int x, int y) Start { get; private set; }

    public (int x, int y) End { get; private set; }

    int this[int x, int y] { get => _grid[y, x]; set => _grid[y, x] = value; }

    int this[(int x, int y) coordinate] => this[coordinate.x, coordinate.y];
    int Height => _grid.GetLength(0);
    int Width => _grid.GetLength(1);
    IEnumerable<(int x, int y)> All()
    {
        for (int y = 0; y < Height; y++)
            for (int x = 0; x < Width; x++)
                yield return (x, y);
    }

    public static Grid Parse(string[] lines)
    {
        var grid = new Grid(new int[lines.Length, lines[0].Length]);
        for (var y = 0; y < lines.Length; y++)
            for (var x = 0; x < lines[0].Length; x++)
            {
                grid[x, y] = lines[y][x] switch
                {
                    'S' => 0,
                    'E' => 'z' - 'a',
                    char c => c - 'a'
                };
                if (lines[y][x] == 'S')
                    grid.Start = (x, y);
                if (lines[y][x] == 'E')
                    grid.End = (x, y);
            }

        return grid;
    }

    int CountSteps(AdjacencyGraph<(int, int), Edge<(int, int)>> graph, (int x, int y) start)
    {
        var f = graph.ShortestPathsDijkstra(_ => 1, start);
        return f(End, out var path) ? path.Count() : -1;
    }

    private AdjacencyGraph<(int, int), Edge<(int, int)>> Graph() => (
        from coordinate in All()
        from n in Neighbors(coordinate)
        where this[n] <= this[coordinate] + 1
        select new Edge<(int x, int y)>(coordinate, n)).ToAdjacencyGraph<(int, int), Edge<(int, int)>>();

    public int CountSteps() => CountSteps(Graph(), Start);

    public int FewestSteps()
    {
        var graph = Graph();
        return (
            from c in All()
            where this[c] == 0
            let steps = CountSteps(graph, c)
            where steps > 0
            select steps).Min();
    }

    private IEnumerable<(int x, int y)> Neighbors((int x, int y) coordinate)
    {
        var (x, y) = coordinate;
        if (x > 0)
            yield return (x - 1, y);
        if (y > 0)
            yield return (x, y - 1);
        if (x < Width - 1)
            yield return (x + 1, y);
        if (y < Height - 1)
            yield return (x, y + 1);
    }

    public override string ToString()
    {
        var sb = new StringBuilder();
        for (int y = 0; y < Height; y++)
        {
            for (int x = 0; x < Width; x++)
                sb.Append((char)(this[x, y] + 'a'));
            sb.AppendLine();
        }

        return sb.ToString();
    }
}