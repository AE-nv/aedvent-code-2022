var input = File.ReadAllLines("input.txt");

var sw = Stopwatch.StartNew();
var part1 = new Tetris(input[0]).Play1(2022);
var part2 = new Tetris(input[0]).Play2(1000000000000);
Console.WriteLine((part1, part2, sw.Elapsed));

readonly record struct Rock(Point origin, ImmutableHashSet<Point> shape, long height, int width)
{
    public static Rock Parse(string[] items)
    {
        var shape = GetPoints(items.Reverse()).ToImmutableHashSet();
        var height = shape.Max(s => s.y) - shape.Min(x => x.y) + 1;
        var width = shape.Max(s => s.x) - shape.Min(x => x.x) + 1;
        return new(new Point(0, 0), shape, height, width);
    }

    static IEnumerable<Point> GetPoints(IEnumerable<string> items)
    {
        return
            from py in items.Select((l, i) => (l, i))
            from px in py.l.Select((c, i) => (c, i))
            where px.c != '.'
            select new Point(px.i, (long)py.i);
    }

    public Rock PlaceAt(Point origin) => this with { origin = origin };
    public Rock MoveLeft() => this with { origin = origin with { x = origin.x - 1 } };
    public Rock MoveRight() => this with { origin = origin with { x = origin.x + 1 } };
    public Rock MoveDown() => this with { origin = origin with { y = origin.y - 1 } };
    public IEnumerable<Point> All()
    {
        foreach (var p in shape)
            yield return origin + p;
    }

    public bool Overlaps(HashSet<Point> other) => other.Overlaps(All());
    public override string ToString()
    {
        var sb = new StringBuilder();
        for (var y = 0L; y < height; y++)
        {
            for (var x = 0; x < width; x++)
                sb.Append(shape.Contains(new Point(x, y)) ? '#' : '.');
            sb.AppendLine();
        }

        return sb.ToString();
    }
}

readonly record struct Point(int x, long y)
{
    public override string ToString() => $"({x},{y})";
    public static Point operator +(Point left, Point right) => new(left.x + right.x, left.y + right.y);
}

class Tetris
{
    ImmutableArray<Rock> rocks = new[] 
    { 
        new[] { "####" }, new[] { ".#.", "###", ".#." }, new[] { "..#", "..#", "###" }, new[] { "#", "#", "#", "#" }, new[] { "##", "##" } 
    }.Select(Rock.Parse).ToImmutableArray();
    private Rock GetRock(long i) => rocks[(int)(i % 5)];
    readonly string movements;
    readonly int Width = 7;
    HashSet<Point> map = new();
    public Tetris(string movements)
    {
        this.movements = movements;
    }

    long Height => map.Any() ? map.Max(x => x.y) + 1 : 0;
    public long Play1(long moves)
    {
        int i = 0;
        for (long n = 0; n < moves; n++)
        {
            var rock = GetRock(n).PlaceAt(new(2, Height + 3));
            while (true)
            {
                rock = movements[i] switch
                {
                    '<' when CanMoveLeft(rock) => rock.MoveLeft(),
                    '>' when CanMoveRight(rock) => rock.MoveRight(),
                    _ => rock
                };
                i = (i + 1) % movements.Length;
                if (CanMoveDown(rock))
                {
                    rock = rock.MoveDown();
                }
                else
                {
                    map.UnionWith(rock.All());
                    break;
                }
            }
        }

        return Height;
    }

    public long Play2(long moves)
    {
        int i = 0;
        var cache = new Dictionary<(int i, long movenr), (long movenr, long height)>();
        var addedHeight = 0L;
        var height = 0L;
        for (long movenr = 0; movenr < moves; movenr++)
        {
            var rock = GetRock(movenr).PlaceAt(new(2, Height + 3));
            while (true)
            {
                rock = movements[i] switch
                {
                    '<' when CanMoveLeft(rock) => rock.MoveLeft(),
                    '>' when CanMoveRight(rock) => rock.MoveRight(),
                    _ => rock
                };
                i = (i + 1) % movements.Length;
                if (CanMoveDown(rock))
                {
                    rock = rock.MoveDown();
                }
                else
                {
                    map.UnionWith(rock.All());
                    height = map.Max(y => y.y);
                    // todo - imperfect cycle detection (off by one for my input)
                    var key = (i, movenr % 5);
                    if (cache.ContainsKey(key) && movenr >= 2022)
                    {
                        var (prevmovenr, prevheight) = cache[key];
                        var cycleHeight = height - prevheight;
                        var cycleMoves = movenr - prevmovenr;
                        var amount = (moves - movenr) / cycleMoves;
                        addedHeight += amount * cycleHeight;
                        movenr += amount * cycleMoves;
                    }

                    cache[key] = (movenr, height);
                    break;
                }
            }
        }

        return height + addedHeight + 1;
    }

    bool CanMoveLeft(Rock rock) => rock.origin.x > 0 && !rock.MoveLeft().Overlaps(map);
    bool CanMoveRight(Rock rock) => (rock.origin.x + rock.width) < Width && !rock.MoveRight().Overlaps(map);
    bool CanMoveDown(Rock rock) => rock.origin.y > 0 && !rock.MoveDown().Overlaps(map);
    private void Draw(Rock? falling)
    {
        var copy = map.ToDictionary(x => x, _ => '#');
        if (falling is not null)
        {
            foreach (var point in falling.Value.All())
                copy[point] = '@';
        }

        var maxY = Math.Min(Height, 6);
        var sb = new StringBuilder();
        for (var y = maxY; y >= 0; y--)
        {
            sb.Append('|');
            for (int x = 0; x < Width; x++)
            {
                sb.Append(copy.TryGetValue(new(x, y), out var c) ? c : '.');
            }

            sb.Append('|').AppendLine();
        }

        sb.Append('+').AppendLine(new string(Repeat('-', 7).Append('+').ToArray()));
        Console.Clear();
        Console.WriteLine(sb.ToString());
    }
}