using static System.Math;

var input = File.ReadAllLines("input.txt");
var movements =
    from line in input
    let d = line[0]
    let l = int.Parse(line[2..])
    from n in Repeat(1, l)
    select d;
var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));
int Part1()
{
    Point h = new(), t = new();
    HashSet<Point> visited = new()
    {t};
    foreach (var d in movements)
    {
        h = h.Move(d);
        t = t.MoveTo(h);
        visited.Add(t);
    }

    return visited.Count;
}

int Part2()
{
    Point[] knots = Repeat(new Point(0, 0), 10).ToArray();
    HashSet<Point> visited = new()
    {knots.Last()};
    foreach (var d in movements)
    {
        knots[0] = knots[0].Move(d);
        for (int i = 1; i < knots.Length; i++)
        {
            knots[i] = knots[i].MoveTo(knots[i - 1]);
        }

        visited.Add(knots[9]);
    }

    return visited.Count;
}

readonly record struct Point(int x, int y)
{
    public Point Move(char d) => d switch
    {
        'R' => Move(1, 0),
        'L' => Move(-1, 0),
        'U' => Move(0, 1),
        'D' => Move(0, -1),
        _ => throw new Exception("unexpected input")
    };
    public Point Move(int dx, int dy) => this with { x = x + dx, y = y + dy };
    public Point MoveTo(Point h) => Distance(h) switch
    {
        > 1 => Move((h.x - x) switch
        {
            > 0 => 1,
            < 0 => -1,
            0 => 0
        }, (h.y - y) switch
        {
            > 0 => 1,
            < 0 => -1,
            0 => 0
        }),
        _ => this
    };
    private int Distance(Point other) => Max(Abs(other.x - x), Abs(other.y - y));
    public override string ToString() => $"({x},{y})";
}