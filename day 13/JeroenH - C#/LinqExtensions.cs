static class LinqExtensions
{
    public static IEnumerable<(T a, T b)> PairWise<T>(this IEnumerable<T> list)
    {
        var enumerator = list.GetEnumerator();
        while (true)
        {
            if (!enumerator.MoveNext()) yield break;
            var a = enumerator.Current;
            if (!enumerator.MoveNext()) yield break;
            var b = enumerator.Current;
            yield return (a, b);
        }
    }
}