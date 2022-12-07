var input = File.ReadAllLines("input.txt");
var Root = Directory.Parse(input);
var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));
long Part1() => (
    from d in Root.AllChildren()
    where d.Size < 100000
    select d.Size).Sum();
long Part2() => (
    from d in Root.AllChildren()
    where d.Size >= (Root.Size - 40000000)
    orderby d.Size
    select d).First().Size;
abstract class FileSystemEntry
{
    public FileSystemEntry(string name) => Name = name;
    public string Name { get; }

    public List<FileSystemEntry> Children { get; } = new();
    public abstract long Size { get; }
}

partial class Directory : FileSystemEntry
{
    static Regex FileRegex = CreateFileRegex();
    static Regex DirectoryRegex = CreateDirectoryRegex();
    public static Directory Parse(IEnumerable<string> input)
    {
        var root = new Directory("/", null);
        var cd = root;
        foreach (var line in input.Skip(1))
        {
            cd = line[0..4] switch
            {
                "$ cd" => cd.Find(line[5..]),
                "$ ls" => cd,
                _ when DirectoryRegex.Match(line) is { Success: true } m => cd.AddDirectory(m.Groups["name"].Value),
                _ when FileRegex.Match(line) is { Success: true } m => cd.AddFile(m.Groups["name"].Value, long.Parse(m.Groups["size"].Value)),
                _ => throw new NotImplementedException()
            };
        }

        return root;
    }

    public IEnumerable<Directory> AllChildren()
    {
        foreach (var child in Children.OfType<Directory>())
        {
            foreach (var d in child.AllChildren())
                yield return d;
        }

        yield return this;
    }

    private Directory(string name, Directory? parent) : base(name) => Parent = parent;
    public Directory? Parent { get; }

    public override long Size => Children.Sum(f => f.Size);
    public Directory AddFile(string name, long size)
    {
        Children.Add(new File(name, size));
        return this;
    }

    public Directory AddDirectory(string name)
    {
        Children.Add(new Directory(name, this));
        return this;
    }

    public Directory Find(string name) => name switch
    {
        ".." => Parent ?? throw new Exception("root does not have parent"),
        _ => Children.OfType<Directory>().First(c => c.Name == name)
    };
    public override string ToString() => $"dir {Name}";
    [GeneratedRegex("^(?<size>\\d+) (?<name>.+)$", RegexOptions.Compiled)]
    private static partial Regex CreateFileRegex();
    [GeneratedRegex("^dir (?<name>.+)$", RegexOptions.Compiled)]
    private static partial Regex CreateDirectoryRegex();
}

class File : FileSystemEntry
{
    public File(string name, long size) : base(name) => Size = size;
    public override long Size { get; }

    public override string ToString() => $"{Size} {Name}";
}