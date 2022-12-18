var input = File.ReadAllLines("input.txt");

var Coordinates = (
    from line in input
    let split = line.Split(',')
    select new Coordinate(int.Parse(split[0]), int.Parse(split[1]), int.Parse(split[2]))).ToImmutableArray();
var maxX = Coordinates.Max(c => c.x);
var maxY = Coordinates.Max(c => c.y);
var maxZ = Coordinates.Max(c => c.z);
var origin = new Coordinate(0, 0, 0);
var max = new Coordinate(maxX, maxY, maxZ);

if (args.Length > 0 && args[0] == "draw")
{
    Draw(Coordinates.ToHashSet());
    Console.Clear();
}

var sw = Stopwatch.StartNew();
var part1 = SurfaceArea(Coordinates);
var part2 =  SurfaceArea(Fill(Coordinates));
Console.WriteLine((part1, part2, sw.Elapsed));

int SurfaceArea(IEnumerable<Coordinate> set)
{
    var x =
        from c in set
        group c.x by (c.y, c.z) into g
        select FindRanges(g).Count();
    var y =
        from c in set
        group c.y by (c.x, c.z) into g
        select FindRanges(g).Count();
    var z =
        from c in set
        group c.z by (c.x, c.y) into g
        select FindRanges(g).Count();
    return 2 * (x.Sum() + y.Sum() + z.Sum());
}

IEnumerable<Range> FindRanges(IEnumerable<int> list)
{
    var enumerator = list.Order().GetEnumerator();
    if (!enumerator.MoveNext())
        yield break;
    var start = enumerator.Current;
    var end = start;
    while (enumerator.MoveNext())
    {
        var next = enumerator.Current;
        if (next > (end + 1))
        {
            yield return new Range(start, end);
            start = next;
        }

        end = next;
    }

    yield return new Range(start, end);
}

IEnumerable<Coordinate> Fill(IEnumerable<Coordinate> coordinates)
{
    var set = coordinates.ToHashSet();
    var all =
        from x in Range(0, maxX)
        from y in Range(0, maxY)
        from z in Range(0, maxZ)
        let c = new Coordinate(x, y, z)
        where !set.Contains(c) && IsContained(c, set)
        select c;
    foreach (var c in all)
    {
        set.Add(c);
        FillNeighbors(c, set);
    }

    return set;
}

void FillNeighbors(Coordinate c, HashSet<Coordinate> set)
{
    foreach (var n in c.Neighbors().Where(n => !set.Contains(n)))
    {
        set.Add(n);
        FillNeighbors(n, set);
    }
}

void Draw(HashSet<Coordinate> set)
{
    int z = 0;
    while (true)
    {
        Console.Clear();
        DrawZ(set, z);
        var key = Console.ReadKey();
        if (key.Key == ConsoleKey.LeftArrow)
            z--;
        if (key.Key == ConsoleKey.RightArrow)
            z++;
        if (key.Key == ConsoleKey.Escape)
            break;
    }
}

void DrawZ(HashSet<Coordinate> set, int z)
{
    Console.WriteLine($"z = {z}");
    for (int y = 0; y <= maxY; y++)
    {
        for (int x = 0; x <= maxX; x++)
        {
            if (set.Contains(new(x, y, z)))
            {
                Console.ForegroundColor = ConsoleColor.Red;
                Console.Write('#');
                Console.ResetColor();
            }
            else if (IsContained(new(x, y, z), set))
            {
                Console.ForegroundColor = ConsoleColor.Yellow;
                Console.Write('#');
                Console.ResetColor();
            }
            else
            {
                Console.ForegroundColor = ConsoleColor.Blue;
                Console.Write('#');
                Console.ResetColor();
            }
        }

        Console.WriteLine();
    }
}

bool IsContained(Coordinate c, HashSet<Coordinate> set)
{
    var queue = new Queue<Coordinate>();
    queue.Enqueue(c);
    var visited = new HashSet<Coordinate>();
    if (c == new Coordinate(3, 9, 12))
        Debugger.Break();
    while (queue.Count > 0)
    {
        var current = queue.Dequeue();
        if (visited.Contains(current))
        {
            continue;
        }

        if (current < origin! || current > max!)
        {
            return false;
        }

        visited.Add(current);
        var neighbors =
            from n in current.Neighbors()
            where !set.Contains(n) && !visited.Contains(n)
            select n;
        foreach (var neighbor in neighbors)
        {
            queue.Enqueue(neighbor);
        }
    }

    return true;
}

public record Coordinate(int x, int y, int z)
{
    public override string ToString() => $"({x},{y},{z})";
    public static bool operator >(Coordinate left, Coordinate right) => left.x > right.x || left.y > right.y || left.z > right.z;
    public static bool operator <(Coordinate left, Coordinate right) => left.x < right.x || left.y < right.y || left.z < right.z;
    public IEnumerable<Coordinate> Neighbors()
    {
        yield return new(x - 1, y, z);
        yield return new(x + 1, y, z);
        yield return new(x, y - 1, z);
        yield return new(x, y + 1, z);
        yield return new(x, y, z - 1);
        yield return new(x, y, z + 1);
    }
}