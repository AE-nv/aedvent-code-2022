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

object Part1() => (
    from chunk in Chunks()
    select (
        from line in chunk
        let n = int.Parse(line)
        select n).Sum()).Max();
object Part2() => (
    from chunk in Chunks()
    select (
        from line in chunk
        let n = int.Parse(line)
        select n).Sum()).OrderDescending().Take(3).Sum();