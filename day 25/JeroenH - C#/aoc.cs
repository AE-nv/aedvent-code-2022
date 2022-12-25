var input = File.ReadAllLines("input.txt");

var sw = Stopwatch.StartNew();
var part1 = input.Select(Snafu.Parse).Aggregate(Snafu.Zero, (l, r) => l + r);
var part2 = Snafu.Zero;
Console.WriteLine((part1, part2, sw.Elapsed));

public readonly record struct Snafu(ImmutableArray<int> digits)
{
    public static Snafu Parse(string s) => new Snafu((
        from c in s.Reverse()
        select c switch
        {
            '=' => -2,
            '-' => -1,
            _ => c - '0'
        }).ToImmutableArray());

    public int ToInt32() => (
        from t in digits.Select((digit, p) => (digit, p)) select t.digit * (int)Pow(5, t.p)).Sum();

    int this[int i] => i < digits.Length ? digits[i] : 0;

    int Length => digits.Length;

    public static Snafu Zero = new(ImmutableArray<int>.Empty.Add(0));

    public override string ToString() => digits.Reverse().Aggregate(new StringBuilder(), (sb, d) => sb.Append(d switch
    {
        -2 => '=',
        -1 => '-',
        _ => (char)(d + '0')
    })).ToString();

    public static Snafu operator +(Snafu left, Snafu right)
    {
        var result = ImmutableArray<int>.Empty.ToBuilder();
        var carry = 0;
        var length = Max(left.Length, right.Length) + 1;
        for (int i = 0; i < length; i++)
        {
            var sum = carry + left[i] + right[i];
            carry = 0;
            while (sum > 2)
                (carry, sum) = (carry + 1, sum - 5);

            while (sum < -2)
                (carry, sum) = (carry - 1, sum + 5);

            result.Add(sum);
        }

        while (result.Last() == 0)
            result.RemoveAt(result.Count - 1);
        return new Snafu(result.ToImmutable());
    }
}