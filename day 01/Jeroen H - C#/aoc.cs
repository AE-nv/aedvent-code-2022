var input = File.ReadAllLines("input.txt");
var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));
IEnumerable<IEnumerable<string>> Chunks()
{
    List<string> chunk = new();
    foreach (var line in input)
    {
        if (string.IsNullOrEmpty(line))
        {
            yield return chunk;
            chunk.Clear();
        }
        else
        {
            chunk.Add(line);
        }
    }

    if (chunk.Any())
        yield return chunk;
}

int Part1() => Chunks().Select(chunk => chunk.Select(int.Parse).Sum()).Max();
int Part2() => Chunks().Select(chunk => chunk.Select(int.Parse).Sum()).OrderDescending().Take(3).Sum();