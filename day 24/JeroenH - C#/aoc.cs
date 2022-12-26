var input = File.ReadAllLines("input.txt");

var start = new Coordinate(1, 0);
var target = new Coordinate(input[0].Length - 2, input.Length - 1);
var grid = new Grid(input);

var sw = Stopwatch.StartNew();
var part1 = FindPath(grid, start, target);
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));

int Part2()
{
    var t1 = FindPath(grid, start, target, 0);
    var t2 = FindPath(grid, target, start, t1);
    var t3 = FindPath(grid, start, target, t1 + t2);
    return t1 + t2 + t3;
}

int FindPath(Grid grid, Coordinate start, Coordinate destination, int t0 = 0)
{
    var cache = new Dictionary<Coordinate, int>();
    cache[start] = 0;
    int t = 0;

    while (!cache.ContainsKey(destination))
    {
        var blocked = grid.BlockedAt(t0 + t + 1);
        var updated = (
            from p in grid.All()
            where cache.TryGetValue(p, out var x) && x == t
            from n in p.AsEnumerable().Concat(p.Neighbours())
            where !blocked.Contains(n)
            select n).ToList();
        foreach (var item in updated)
        {
            cache[item] = t + 1;
        }

        t++;
    }

    return cache[destination];
}

readonly record struct Coordinate(int x, int y)
{
    public override string ToString() => $"({x},{y})";
    public static implicit operator Coordinate((int x, int y) t) => new(t.x, t.y);
    public IEnumerable<Coordinate> Neighbours()
    {
        yield return this with { x = x + 1 };
        yield return this with { y = y + 1 };
        yield return this with { x = x - 1 };
        yield return this with { y = y - 1 };
    }
}

/// <summary>
/// A finite, immutable grid. Y increments downward, X increments rightward
/// </summary>
class Grid
{
    //        x
    //   +---->
    //   |
    //   |
    // y v
    readonly ImmutableDictionary<Coordinate, char> items;
    readonly Coordinate origin = new(0, 0);
    readonly Coordinate bottomright;
    readonly char empty;
    public int Bottom => bottomright.y;
    public int Right => bottomright.x;
    int Left => origin.x;
    int Top => origin.y;
    public Grid(string[] input, char empty = '.')
    {
        bottomright = new(input[0].Length - 1, input.Length - 1);
        items = (
            from y in Range(Top, Bottom + 1)
            from x in Range(Left, Right + 1)
            let c = input[y][x]
            where c != '.' || (x == 0 || y == 0 || x == Right || y == Bottom)
            select (x, y, c)).ToImmutableDictionary(t => new Coordinate(t.x, t.y), t => t.c);
        this.empty = empty;
    }

    public ImmutableHashSet<Coordinate> BlockedAt(int t) => (
            from kv in items
            let c = kv.Key
            let v = kv.Value
            where v is not '.'
            select v switch
            {
                '#' => c,
                '>' => c with { x = Mod(c.x - 1 + t, Right - 1) + 1 },
                '<' => c with { x = Mod(c.x - 1 - t, Right - 1) + 1 },
                'v' => c with { y = Mod(c.y - 1 + t, Bottom - 1) + 1 },
                '^' => c with { y = Mod(c.y - 1 - t, Bottom - 1) + 1 }
            }
            ).ToImmutableHashSet();

    public bool Contains(Coordinate n) => items.ContainsKey(n);
    static T Mod<T>(T n, T m)
        where T : INumber<T> => (n % m + m) % m;
    public char this[Coordinate p] => items.TryGetValue(p, out var c) ? c : '.';
    char this[int x, int y] => this[new Coordinate(x, y)];
    public IEnumerable<Coordinate> All() =>
        from y in Range(Top, Bottom + 1) from x in Range(Left, Right + 1) select new Coordinate(x, y);

    public void Draw(int t, Coordinate current, IReadOnlyDictionary<Coordinate, int> cache)
    {
        var grid = (
            from kv in items
            let c = kv.Key
            let v = kv.Value
            let next = v switch
            {
                '#' or '.' => c,
                '>' => c with { x = Mod(c.x - 1 + t, Right - 1) + 1 },
                '<' => c with { x = Mod(c.x - 1 - t, Right - 1) + 1 },
                'v' => c with { y = Mod(c.y - 1 + t, Bottom - 1) + 1 },
                '^' => c with { y = Mod(c.y - 1 - t, Bottom - 1) + 1 }
            }
            group v by next).ToImmutableDictionary(x => x.Key, x => x.ToImmutableList());

        Console.Clear();

        for (int y = Top; y <= Bottom; y++)
        {
            for (int x = Left; x <= Right; x++)
                Console.Write("+---");
            Console.Write("+");
            Console.WriteLine();
            for (int i = 0; i < 2; i++)
            {
                for (int x = Left; x <= Right; x++)
                {
                    if (current == (x, y))
                    {
                        Console.Write("|");
                        Console.BackgroundColor = ConsoleColor.Yellow;
                        Console.Write("   ");
                        Console.ResetColor();
                    }
                    else
                    {
                        if (!grid.TryGetValue((x, y), out var l))
                            l = ImmutableList<char>.Empty.Add('.');
                        Console.Write("|");
                        if (l is ['#'])
                        {
                            Console.BackgroundColor = ConsoleColor.DarkRed;
                            Console.Write("###");
                            Console.ResetColor();
                        }
                        else if (l is ['.'])
                        {
                            Console.Write("   ");
                            Console.ResetColor();
                        }
                        else
                        {
                            Console.BackgroundColor = ConsoleColor.DarkRed;
                            var c = l.Count == 1 ? l[0] : l.Count.ToString()[0];
                            if (i == 0)
                                Console.Write($"{c}  ");
                            else if (cache.TryGetValue((x, y), out var v))
                            {
                                Console.BackgroundColor = ConsoleColor.DarkBlue;
                                Console.ForegroundColor = ConsoleColor.White;
                                Console.Write(v.ToString().PadRight(3));
                            }
                            else
                                Console.Write($"   ");
                        }
                    }
                }

                Console.Write("|");
                Console.WriteLine();
            }
        }

        for (int x = Left; x <= Right; x++)
            Console.Write("+---");
        Console.Write("+");
        Console.WriteLine();
        Console.ReadKey();
    }

    public override string ToString()
    {
        var sb = new StringBuilder();
        for (int y = Top; y <= Bottom; y++)
        {
            for (int x = Left; x <= Right; x++)
            {
                var c = this[x, y];
                sb.Append(c);
            }

            sb.AppendLine();
        }

        return sb.ToString();
    }
}

static class LinqExtensions
{
    public static IEnumerable<T> AsEnumerable<T>(this T item)
    {
        yield return item;
    }
}