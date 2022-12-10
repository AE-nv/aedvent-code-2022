using System.Runtime.InteropServices;

var input = File.ReadAllLines("input.txt");
var grid = new Grid(input);

var sw = Stopwatch.StartNew();
var part1 = (
    from p in grid.InteriorPoints()
    where grid.IsVisible(p)
    select p
    ).Count() + grid.Circumference;
var part2 = (
    from p in grid.InteriorPoints() 
    select grid.Score(p)
    ).Max()
Console.WriteLine((part1, part2, sw.Elapsed));

readonly record struct Point(int x, int y)
{
    public override string ToString() => $"({x},{y})";
}

class Grid
{
    readonly string[] input;
    readonly Point origin = new(0, 0);
    readonly Point length;
    public int Height => length.y;
    public int Width => length.x;
    public int Circumference => 2 * (Height - 1) + 2 * (Width - 1);
    public Grid(string[] input)
    {
        this.input = input;
        this.length = new(input[0].Length, input.Length);
    }

    public int this[Point p] => input[p.y][p.x] - '0';
    public IEnumerable<Point> Points() =>
        from y in Range(origin.y, length.y) from x in Range(origin.x, length.x) select new Point(x, y);
    public IEnumerable<Point> InteriorPoints() =>
        from y in Range(origin.y + 1, length.y - 2) from x in Range(origin.x + 1, length.x - 2) select new Point(x, y);
    public override string ToString()
    {
        var sb = new StringBuilder();
        for (int y = 0; y < length.y; y++)
        {
            for (int x = 0; x < length.x; x++)
                sb.Append(this[new(x, y)]);
            sb.AppendLine();
        }

        return sb.ToString();
    }

    internal bool IsVisible(Point p) => Range(0, p.x).All(x => this[new(x, p.y)] < this[p]) || Range(p.x + 1, Width - p.x - 1).All(x => this[new(x, p.y)] < this[p]) || Range(0, p.y).All(y => this[new(p.x, y)] < this[p]) || Range(p.y + 1, Height - p.y - 1).All(y => this[new(p.x, y)] < this[p]);
    public int Score(Point p)
    {
        int l = Left(p).TakeWhile(q => this[p] > this[q]).Count();
        int r = Right(p).TakeWhile(q => this[p] > this[q]).Count();
        int u = Up(p).TakeWhile(q => this[p] > this[q]).Count();
        int d = Down(p).TakeWhile(q => this[p] > this[q]).Count();
        if (l < p.x)
            l++;
        if (r < (Width - p.x - 1))
            r++;
        if (u < p.y)
            u++;
        if (d < (Height - p.y - 1))
            d++;
        return l * r * u * d;
    }

    IEnumerable<Point> Left(Point p)
    {
        for (int x = p.x - 1; x >= 0; x--)
            yield return p with { x = x };
    }

    IEnumerable<Point> Right(Point p)
    {
        for (int x = p.x + 1; x < Width; x++)
            yield return p with { x = x };
    }

    IEnumerable<Point> Down(Point p)
    {
        for (int y = p.y + 1; y < Height; y++)
            yield return p with { y = y };
    }

    IEnumerable<Point> Up(Point p)
    {
        for (int y = p.y - 1; y >= 0; y--)
            yield return p with { y = y };
    }
}