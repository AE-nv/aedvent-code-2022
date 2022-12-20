using System.Numerics;

var input = File.ReadAllLines("input.txt");

var numbers = (
    from line in input
    select int.Parse(line)
    ).ToImmutableArray();

var sw = Stopwatch.StartNew();
var part1 = Decrypt();
var part2 = Decrypt(811589153L, 10);
Console.WriteLine((part1, part2, sw.Elapsed));

long Decrypt(long key = 1, int rounds = 1)
{
    var linkedList = new LinkedList<long>();

    var nodesList = (
        from n in numbers
        let node = linkedList.AddLast(n * key)
        select node).ToList();

    var q =
        from round in Repeat(0, rounds)
        from node in nodesList
        where node.Value != 0
        let shift = (int)Mod(node.Value, numbers.Length - 1)
        let target = Shift(node, shift)
        where node != target
        select (node, target);

    foreach (var (node, target) in q)
    {
        linkedList.Remove(node);
        linkedList.AddAfter(target, node);
    }

    var grove = nodesList.Find(n => n.Value == 0)!;
    var result = 0L;
    for (int i = 0; i < 3; i++)
    {
        grove = Shift(grove, 1000);
        result += grove.Value;
    }

    return result;
}

T Mod<T>(T n, T m) where T : INumber<T> => (n % m + m) % m;

LinkedListNode<T> Shift<T>(LinkedListNode<T> node, int shift)
{
    var next = node;
    foreach (var _ in Repeat(1, shift))
    {
        next = next.NextOrFirst();
        if (next == node)
            next = next.NextOrFirst();
    }

    return next;
}

static class LinqExtensions
{
    public static LinkedListNode<T> PreviousOrLast<T>(this LinkedListNode<T> node) => node.Previous ?? node?.List?.Last ?? throw new Exception("Inconsistent linked list");
    public static LinkedListNode<T> NextOrFirst<T>(this LinkedListNode<T> node) => node.Next ?? node?.List?.First ?? throw new Exception("Inconsistent linked list");

}