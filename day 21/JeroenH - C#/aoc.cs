var input = File.ReadAllLines("input.txt");

var calculationRegex = new Regex(@"(?<name>[a-z]+): (?<l>[a-z]+) (?<operation>.) (?<r>[a-z]+)", RegexOptions.Compiled);
var numberRegex = new Regex(@"(?<name>[a-z]+): (?<number>[\d]+)", RegexOptions.Compiled);

var instructions = (
    from line in input
    select calculationRegex.As<Calculation>(line) as Instruction ?? numberRegex.As<Number>(line)).ToImmutableDictionary(o => o.name);

var parents = (
    from i in instructions
    where i.Value is Calculation m
    let m = (Calculation)i.Value
    from child in (m.l, m.r).AsEnumerable()
    let parent = m
    select (child, parent)).ToDictionary(x => x.child, x => x.parent);

var sw = Stopwatch.StartNew();
var part1 = GetValue("root", instructions);
var part2 = GetValue("humn", TransformInstructions(instructions).ToImmutableDictionary(x => x.name));
Console.WriteLine((part1, part2, sw.Elapsed));

IEnumerable<Instruction> TransformInstructions(IReadOnlyDictionary<string, Instruction> instructions)
{
    var name = "humn";
    while (true)
    {
        var parent = parents[name];
        var sibling = parent.Other(name);

        if (parent is { name: "root" })
        {
            yield return new Number(name, GetValue(sibling, instructions));
            break;
        }

        var transformed = Transform(name, parent);
        var number = new Number(sibling, GetValue(sibling, instructions));
        yield return transformed;
        yield return number;
        name = transformed.Other(sibling);
    }
}

static Calculation Transform(string child, Calculation parent) => parent.operation switch
{
    '+' when parent.l == child => new(parent.l, parent.name, '-', parent.r),
    '+' when parent.r == child => new(parent.r, parent.name, '-', parent.l),
    '-' when parent.l == child => new(parent.l, parent.name, '+', parent.r),
    '-' when parent.r == child => new(parent.r, parent.l, '-', parent.name),
    '*' when parent.l == child => new(parent.l, parent.name, '/', parent.r),
    '*' when parent.r == child => new(parent.r, parent.name, '/', parent.l),
    '/' when parent.l == child => new(parent.l, parent.name, '*', parent.r),
    '/' when parent.r == child => new(parent.r, parent.l, '/', parent.name),
    _ => throw new Exception()
};

long GetValue(string operation, IReadOnlyDictionary<string, Instruction> instructions) => instructions[operation] switch
{
    Number n => n.number,
    Calculation m => m.operation switch
    {
        '+' => GetValue(m.l, instructions) + GetValue(m.r, instructions),
        '-' => GetValue(m.l, instructions) - GetValue(m.r, instructions),
        '*' => GetValue(m.l, instructions) * GetValue(m.r, instructions),
        '/' => GetValue(m.l, instructions) / GetValue(m.r, instructions),
        _ => throw new Exception()
    },
    _ => throw new Exception()
};

interface Instruction
{
    string name { get; }
}

record struct Number(string name, long number) : Instruction;
record struct Calculation(string name, string l, char operation, string r) : Instruction
{
    public string Other(string name) => name == l ? r : l;
}


internal static class RegexHelper
{
    public static T? As<T>(this Regex regex, string s, IFormatProvider? provider = null) where T: struct
    {
        var match = regex.Match(s);
        if (!match.Success) return default;

        var constructor = typeof(T).GetConstructors().Single();
        var parameters = constructor.GetParameters();

        var j = from p in parameters
                join m in match.Groups.OfType<Group>() on p.Name equals m.Name
                select Convert.ChangeType(m.Value, p.ParameterType, provider ?? CultureInfo.InvariantCulture);

        return (T)constructor.Invoke(j.ToArray());

    }

    public static int GetInt32(this Match m, string name) => int.Parse(m.Groups[name].Value);

}

static class LinqExtensions
{
    public static IEnumerable<T> AsEnumerable<T>(this (T a, T b) tuple)
    {
        yield return tuple.a;
        yield return tuple.b;
    }
}
