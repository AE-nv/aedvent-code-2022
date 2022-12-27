using static Direction;
using static Side;

var input = File.ReadAllLines("input.txt");
var data = input.TakeWhile(s => !string.IsNullOrEmpty(s)).ToArray();
var instructions = input.SkipWhile(s => !string.IsNullOrEmpty(s)).Skip(1).First();
var length = 50;
var grid = Grid.Parse(data);
var cube = Cube.Parse(data, length);

var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));

long Part1()
{
    var x = Range(0, grid.Width).First(x => grid[x, 0] != ' ');
    var v = new Vector(new Coordinate(x, 0), E);
    foreach (var i in GetInstructions(instructions))
    {
        v = grid.Move(v, i);
    }

    return (v.position.y + 1L) * 1000L + (v.position.x + 1L) * 4L + v.direction switch
    {
        E => 0,
        S => 1,
        W => 2,
        N => 3,
        _ => throw new Exception()
    };
}

long Part2()
{
    var (side, position, facing) = GetInstructions(instructions).Aggregate(cube.InitializeCrawler(), (crawler, i) => cube.Move(crawler, i));
    var (x, y) = side.origin;
    var column = x * length + position.x + 1;
    var row = y * length + position.y + 1;
    return 1000 * row + 4 * column + facing;
}

IEnumerable<Instruction> GetInstructions(string path)
{
    StringBuilder steps = new();
    StringBuilder rotation = new();
    for (int i = 0; i < path.Length; i++)
    {
        (steps, rotation) = path[i] switch
        {
            >= '0' and <= '9' => (steps.Append(path[i]), rotation),
            _ => (steps, rotation.Append(path[i]))
        };
        if (steps.Length > 0 && rotation.Length > 0)
        {
            yield return new Instruction(int.Parse(steps.ToString()), rotation[0]);
            steps.Clear();
            rotation.Clear();
        }
    }

    if (steps.Length > 0)
    {
        yield return new Instruction(int.Parse(steps.ToString()), ' ');
        steps.Clear();
        rotation.Clear();
    }
}

record Instruction(int steps, char rotation)
{
    public override string ToString() => $"{steps}{rotation}";
}

record Vector(Coordinate position, Direction direction)
{
    public override string ToString() => $"{position}{direction}";
    public Vector Move((Coordinate lower, Coordinate upper) bounds)
    {
        return direction switch
        {
            N => this with { position = position.N(bounds.lower.y, bounds.upper.y) },
            E => this with { position = position.E(bounds.lower.x, bounds.upper.x) },
            S => this with { position = position.S(bounds.lower.y, bounds.upper.y) },
            W => this with { position = position.W(bounds.lower.x, bounds.upper.x) }
        };
    }

    public Vector Rotate(char rotation) => (rotation, direction) switch
    {
        ('R', N) => this with { direction = E },
        ('R', E) => this with { direction = S },
        ('R', S) => this with { direction = W },
        ('R', W) => this with { direction = N },
        ('L', N) => this with { direction = W },
        ('L', W) => this with { direction = S },
        ('L', S) => this with { direction = E },
        ('L', E) => this with { direction = N },
        _ => this
    };
}

readonly record struct Coordinate(int x, int y)
{
    public override string ToString() => $"({x},{y})";
    public Coordinate N(int min, int max) => this with { y = y == min ? max : y - 1 };
    public Coordinate E(int min, int max) => this with { x = x == max ? min : x + 1 };
    public Coordinate S(int min, int max) => this with { y = y == max ? min : y + 1 };
    public Coordinate W(int min, int max) => this with { x = x == min ? max : x - 1 };
    public static bool operator ==(Coordinate left, (int x, int y) tuple) => left.x == tuple.x && left.y == tuple.y;
    public static bool operator !=(Coordinate left, (int x, int y) tuple) => left.x != tuple.x || left.y != tuple.y;
    public static Coordinate operator +(Coordinate left, Delta delta) => left with { x = left.x + delta.x, y = left.y + delta.y };
    public static Coordinate operator -(Coordinate left, Delta delta) => left with { x = left.x - delta.x, y = left.y - delta.y };
    public bool IsOutside(int min, int max) => Math.Min(x, y) < min || Math.Max(x, y) > max - 1;
    public IEnumerable<Coordinate> Neighbours()
    {
        yield return this + Delta.X;
        yield return this + Delta.Y;
        yield return this - Delta.X;
        yield return this - Delta.Y;
    }
}

record CubeCrawler(SideData side, Coordinate position, int facing);

readonly record struct Delta(int x, int y)
{
    public static readonly Delta X = new(1, 0);
    public static readonly Delta Y = new(0, 1);
    public static Delta operator -(Delta item) => item with { x = -item.x, y = -item.y };
}

record Cube(ImmutableDictionary<Side, SideData> sides, ImmutableArray<Delta> deltas, int length)
{
    public static Cube Parse(string[] input, int length)
    {
        var neighbours = new[] 
        { 
            (side: Front, neighbours: new[] { Right, Bottom, Left, Top }.ToImmutableArray()), 
            (side: Right, neighbours: new[] { Front, Top, Back, Bottom }.ToImmutableArray()), 
            (side: Bottom, neighbours: new[] { Front, Right, Back, Left }.ToImmutableArray()), 
            (side: Left, neighbours: new[] { Front, Bottom, Back, Top }.ToImmutableArray()), 
            (side: Back, neighbours: new[] { Left, Bottom, Right, Top }.ToImmutableArray()), 
            (side: Top, neighbours: new[] { Right, Front, Left, Back }.ToImmutableArray()) 
        }.ToImmutableDictionary(x => x.side, x => x.neighbours);

        var tiles = (
            from sy in Range(0, 4)
            from sx in Range(0, 4)
            where input.Length > sy * 50 && input[sy * 50].Length > sx * 50 && input[sy * 50][sx * 50] != ' '
            let origin = new Coordinate(sx, sy)
            let grid = (
                from y in Range(0, 50)
                from x in Range(0, 50)
                let row = sy * 50 + y
                let col = sx * 50 + x
                let c = new Coordinate(x, y)
                where input[row][col] is '.'
                select c).ToImmutableHashSet()
            select (origin, grid)).ToImmutableDictionary(x => x.origin, x => x.grid);
        var deltas = new[] { Delta.X, Delta.Y, -Delta.X, -Delta.Y, }.ToImmutableArray();
        var sides = Connect(neighbours, tiles, deltas, length);
        return new Cube(sides, deltas, length);
    }

    static ImmutableDictionary<Side, SideData> Connect(ImmutableDictionary<Side, ImmutableArray<Side>> faces, ImmutableDictionary<Coordinate, ImmutableHashSet<Coordinate>> tiles, ImmutableArray<Delta> deltas, int length)
    {
        var facedata = new Dictionary<Side, (int offset, Coordinate origin)>();
        var first = tiles.Keys.First();
        var queue = new Queue<(Coordinate origin, Side face, (int i, Side face) from)>();
        var visited = new HashSet<Coordinate>() { first };
        queue.Enqueue((first, Front, (1, Top)));
        while (queue.Any())
        {
            var (origin, face, from) = queue.Dequeue();
            var offset = (4 + from.i + 2 % 4 - faces[face].IndexOf(from.face)) % 4;
            facedata[face] = (offset, origin);
            for (int i = 0; i < 4; i++)
            {
                var delta = deltas[i];
                var next = origin + delta;
                if (tiles.ContainsKey(next) && !visited.Contains(next))
                {
                    visited.Add(next);
                    queue.Enqueue((next, faces[face][(4 + i - offset) % 4], (i, face)));
                }
            }
        }

        return (
            from side in faces.Keys
            let neighbours = faces[side]
            let data = facedata[side]
            let origin = data.origin
            let offset = data.offset
            let tile = tiles[data.origin]
            select new SideData(side, neighbours, origin, tile, offset, length)).ToImmutableDictionary(s => s.side);
    }

    internal CubeCrawler InitializeCrawler() => new CubeCrawler(sides[Front], new(0, 0), 0);

    internal CubeCrawler Move(CubeCrawler crawler, Instruction instruction)
    {
        var (side, position, facing) = crawler;
        var (steps, rotation) = instruction;
        for (var i = 0; i < steps; i++)
        {
            if (!side.Contains(position + deltas[facing]))
            {
                var next = sides[side.GetNeighbour(facing)];
                var (nextpos, nextfacing) = next.AdjustFrom(position, side, facing);
                if (!next.IsAvailable(nextpos))
                    break;
                (side, position, facing) = (next, nextpos, nextfacing);
            }
            else
            {
                var newPosition = position + deltas[facing];
                if (!side.IsAvailable(newPosition))
                    break;
                position = newPosition;
            }
        }

        if (rotation == 'R')
        {
            facing = (facing + 1) % 4;
        }
        else if (rotation == 'L')
        {
            facing = (facing + 3) % 4;
        }

        return new(side, position, facing);
    }
}

record SideData(Side side, ImmutableArray<Side> neighbours, Coordinate origin, ImmutableHashSet<Coordinate> tile, int offset, int length)
{
    public Side GetNeighbour(int facing) => neighbours[(4 + facing - offset) % 4];
    public bool IsAvailable(Coordinate c) => tile.Contains(c);
    public bool Contains(Coordinate c) => !c.IsOutside(0, length);
    public (Coordinate, int) AdjustFrom(Coordinate position, SideData other, int facing)
    {
        var relativeFrom = (facing + 2) % 4;
        var rotations = (neighbours.IndexOf(other.side) - relativeFrom + offset) % 4;
        (position, facing) = Repeat(0, rotations).Aggregate((position, facing), (a, i) => (new Coordinate(length - 1 - a.position.y, a.position.x), (a.facing + 1) % 4));
        return (facing switch
        {
            0 => new(0, position.y),
            1 => new(position.x, 0),
            2 => new(length - 1, position.y),
            3 => new(position.x, length - 1)
        }, facing);
    }
}

class Grid
{
    char[,] _grid;
    Dictionary<(int x, int y), char> _path = new();
    public Grid(char[,] grid) => _grid = grid;
    public char this[Coordinate p]
    {
        get => InBounds(p) ? this[p.x, p.y] : ' ';
        set
        {
            if (InBounds(p))
                this[p.x, p.y] = value;
        }
    }

    public char this[int x, int y] { get => _grid[y, x]; set => _grid[y, x] = value; }

    public int Height => _grid.GetLength(0);
    public int Width => _grid.GetLength(1);
    public (Coordinate lower, Coordinate upper) Bound(Coordinate p) => (new(MinX(p.y), MinY(p.x)), new(MaxX(p.y), MaxY(p.x)));
    int MinY(int x)
    {
        for (int y = 0; y < Height; y++)
            if (this[x, y] != ' ')
                return y;
        return Height;
    }

    int MaxY(int x)
    {
        for (int y = Height - 1; y >= 0; y--)
            if (this[x, y] != ' ')
                return y;
        return -1;
    }

    int MinX(int y)
    {
        for (int x = 0; x < Width; x++)
            if (this[x, y] != ' ')
                return x;
        return Width;
    }

    int MaxX(int y)
    {
        for (int x = Width - 1; x >= 0; x--)
            if (this[x, y] != ' ')
                return x;
        return -1;
    }

    public static Grid Parse(string[] lines)
    {
        var grid = new Grid(new char[lines.Length, lines.Max(l => l.Length)]);
        for (var y = 0; y < lines.Length; y++)
        {
            for (var x = 0; x < lines[y].Length; x++)
                grid[x, y] = lines[y][x];
            for (var x = lines[y].Length; x < grid.Width; x++)
                grid[x, y] = ' ';
        }

        return grid;
    }

    public Vector Move(Vector v, Instruction i)
    {
        _path[(v.position.x, v.position.y)] = (char)v.direction;
        for (int j = 0; j < i.steps; j++)
        {
            var next = v.Move(Bound(v.position));
            if (this[next.position] == '.')
            {
                v = next;
            }
            else
                break;
        }

        v = v.Rotate(i.rotation);
        _path[(v.position.x, v.position.y)] = (char)v.direction;
        return v;
    }

    private bool InBounds(Coordinate p) => p.x >= MinX(p.y) && p.x <= MaxX(p.y) && p.y >= MinY(p.x) && p.y <= MaxY(p.x);
    public void Draw()
    {
        for (int y = 0; y < Height; y++)
        {
            for (int x = 0; x < Width; x++)
            {
                if (!_path.TryGetValue((x, y), out var c))
                    c = this[x, y];
                if (c is '>' or '<' or 'v' or '^')
                    Console.ForegroundColor = ConsoleColor.Yellow;
                Console.Write(c);
                if (c is '>' or '<' or 'v' or '^')
                    Console.ResetColor();
            }

            Console.WriteLine();
        }
    }

    public override string ToString()
    {
        var sb = new StringBuilder();
        for (int y = 0; y < Height; y++)
        {
            for (int x = 0; x < Width; x++)
            {
                sb.Append(_path.TryGetValue((x, y), out var c) ? c : this[x, y]);
            }

            sb.AppendLine();
        }

        return sb.ToString();
    }
}

enum Direction
{
    N = '^',
    E = '>',
    S = 'v',
    W = '<'
}

enum Side
{
    Front = 1,
    Right = 2,
    Bottom = 3,
    Left = 4,
    Back = 5,
    Top = 6
}