using static Direction;

var input = File.ReadAllLines("input.txt");

var sw = Stopwatch.StartNew();
var part1 = Grid.Parse(input).DoMoves(10).Count();
var part2 = Grid.Parse(input).DoMovesWhileMoving();
Console.WriteLine((part1, part2, sw.Elapsed));

readonly record struct Coordinate(int x, int y)
{
    public override string ToString() => $"({x},{y})";
    public Coordinate Move(Direction d) => d switch
    {
        Direction.N => N(),
        Direction.E => E(),
        Direction.S => S(),
        Direction.W => W()
    };
    public IEnumerable<Coordinate> AllNeighbours()
    {
        yield return N();
        yield return NE();
        yield return E();
        yield return SE();
        yield return S();
        yield return SW();
        yield return W();
        yield return NW();
    }

    public Coordinate N() => this with { y = y + 1 };
    public Coordinate S() => this with { y = y - 1 };
    public Coordinate E() => this with { x = x + 1 };
    public Coordinate W() => this with { x = x - 1 };
    public Coordinate SE() => this with { x = x + 1, y = y - 1 };
    public Coordinate SW() => this with { x = x - 1, y = y - 1 };
    public Coordinate NE() => this with { x = x + 1, y = y + 1 };
    public Coordinate NW() => this with { x = x - 1, y = y + 1 };
}

class Grid
{
    readonly ImmutableHashSet<Coordinate> _items;
    readonly (Direction a, Direction b, Direction c, Direction d) _directions;
    public Grid(ImmutableHashSet<Coordinate> items, (Direction a, Direction b, Direction c, Direction d) directions)
    {
        _items = items;
        _directions = directions;
        BottomLeft = _items.Aggregate(Coordinate.MaxValue, (o, p) => o with { x = (0, o.x, p.x).Min(), y = (0, o.y, p.y).Min() });
        TopRight = _items.Aggregate(Coordinate.MinValue, (o, p) => o with { x = (o.x, p.x).Max(), y = (o.y, p.y).Max() });
    }

    private Coordinate BottomLeft { get; }

    private Coordinate TopRight { get; }

    private int Size => (TopRight.y - BottomLeft.y + 1) * (TopRight.x - BottomLeft.x + 1);
    internal int Count() => Size - _items.Count;
    public static Grid Parse(string[] input)
    {
        return new Grid((
            from row in Range(0, input.Length)
            let y = input.Length - row - 1
            from x in Range(0, input[y].Length)
            where input[row][x] == '#'
            select new Coordinate(x, y)).ToImmutableHashSet(), (N, S, W, E));
    }

    public char this[Coordinate c] => _items.Contains(c) ? '#' : '.';
    public Grid DoMoves(int n)
    {
        var grid = this;
        foreach (var _ in Repeat(0, n))
        {
            grid = grid.DoMoves();
        }

        return grid;
    }

    public int DoMovesWhileMoving()
    {
        int n = 0;
        var grid = this;
        while (true)
        {
            var next = grid.DoMoves();
            n++;
            if (grid.Equals(next))
                return n;
            grid = next;
        }
    }

    bool Equals(Grid other) => _items.SequenceEqual(other._items);
    Grid DoMoves() => new((
        from c in _items
        let proposed = (
            from d in _directions.AsEnumerable()
            where CanMove(c, d)
            select c.Move(d) as Coordinate?).FirstOrDefault() ?? c
        group c by proposed into g
        from item in g.Skip(1).Any() ? g : g.Key.AsEnumerable()
        select item).ToImmutableHashSet(), (_directions.b, _directions.c, _directions.d, _directions.a));
    bool CanMove(Coordinate c, Direction d) => c.AllNeighbours().Any(c => this[c] == '#') && d switch
    {
        N => (this[c.NW()], this[c.N()], this[c.NE()]) == ('.', '.', '.'),
        E => (this[c.NE()], this[c.E()], this[c.SE()]) == ('.', '.', '.'),
        S => (this[c.SW()], this[c.S()], this[c.SE()]) == ('.', '.', '.'),
        W => (this[c.NW()], this[c.W()], this[c.SW()]) == ('.', '.', '.')
    };
    public override string ToString()
    {
        var sb = new StringBuilder();
        for (int y = TopRight.y; y >= BottomLeft.y; y--)
        {
            for (int x = BottomLeft.x; x <= TopRight.x; x++)
            {
                sb.Append(_items.Contains(new(x, y)) ? '#' : '.');
            }

            sb.AppendLine();
        }

        return sb.ToString();
    }
}

enum Direction { N, E, S, W }

static class TupleEx
{
    public static T Max<T>(this (T item1, T item2) tuple) where T : INumber<T>
       => T.Max(tuple.item1, tuple.item2);
    public static T Min<T>(this (T item1, T item2, T item3) tuple) where T : INumber<T>
        => T.Min(tuple.item1, T.Min(tuple.item2, tuple.item3));

    public static IEnumerable<T> AsEnumerable<T>(this T item)
    {
        yield return item;
    }
    public static IEnumerable<T> AsEnumerable<T>(this (T a, T b, T c, T d) tuple)
    {
        yield return tuple.a;
        yield return tuple.b;
        yield return tuple.c;
        yield return tuple.d;
    }
}