var sw = Stopwatch.StartNew();
var part1 = Find(Input(), 4);
var part2 = Find(Input(), 14);
Console.WriteLine((part1, part2, sw.Elapsed));
StreamReader Input() => new StreamReader(File.OpenRead("input.txt"));
int Find(StreamReader input, int size)
{
    var q = new Queue<int>();
    for (int i = 0; i < size; i++) q.Enqueue(input.Read());
    int position = size + 1;
    while (input.Peek() >= 0)
    {
        var c = input.Read();
        q.Enqueue(c);
        q.Dequeue();
        if (q.Distinct().Count() == size)
            return position;
        position++;
    }
    return -1;
}