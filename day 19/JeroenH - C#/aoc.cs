using System.Numerics;

var regex = new Regex(@"Blueprint (?<id>\d+): Each ore robot costs (?<ore>\d+) ore. Each clay robot costs (?<clay_ore>\d+) ore. Each obsidian robot costs (?<obsidian_ore>\d+) ore and (?<obsidian_clay>\d+) clay. Each geode robot costs (?<geode_ore>\d+) ore and (?<geode_obsidian>\d+) obsidian.", RegexOptions.Compiled);

var input = File.ReadAllLines("input.txt");

var blueprints = (
    from line in input
    let match = regex.Match(line)
    select new Blueprint(
        id: int.Parse(match.Groups["id"].Value), 
        ore: int.Parse(match.Groups["ore"].Value), 
        clay: (ore: int.Parse(match.Groups["clay_ore"].Value), 0), 
        obsidian: (
            ore: int.Parse(match.Groups["obsidian_ore"].Value), 
            clay: int.Parse(match.Groups["obsidian_clay"].Value)), 
        geode: (
            ore: int.Parse(match.Groups["geode_ore"].Value), 
            obsidian: int.Parse(match.Groups["geode_obsidian"].Value))
    )).ToImmutableArray();

var sw = Stopwatch.StartNew();

var part1 = (
    from b in blueprints
    select b.id * UseBlueprint(b)
    ).Sum();

var part2 = (
    from b in blueprints.Take(3)
    select UseBlueprint(b, 32)
    ).Aggregate(1, (l, r) => l * r);

Console.WriteLine((part1, part2, sw.Elapsed));

int UseBlueprint(Blueprint blueprint, int startTime = 24)
{
    HashSet<((Resources resources, Robots robots), int)> cache = new();
    var startState = ((new Resources(0, 0, 0, 0), new Robots(1, 0, 0, 0)), startTime);
    Queue<((Resources resources, Robots robots), int)> queue = new();
    int max = 0;
    queue.Enqueue(startState);
    while (queue.TryDequeue(out var item))
    {
        var (state, time) = item;
        max = Math.Max(max, state.resources.geode);
        if (time == 0)
            continue;
        int nextTick = time - 1;
        state = state with 
        { 
            robots = state.robots with 
            { 
                ore = Math.Min(state.robots.ore, blueprint.MostExpensiveOre),
                clay = Math.Min(state.robots.clay, blueprint.obsidian.clay),
                obsidian = Math.Min(state.robots.obsidian, blueprint.geode.obsidian) 
            }, 
            resources = state.resources with
            {
                ore = Math.Min(state.resources.ore, (time * blueprint.MostExpensiveOre) - (state.robots.ore * nextTick)),
                clay = Math.Min(state.resources.clay, (time * blueprint.obsidian.clay) - (state.robots.clay * nextTick)),
                obsidian = Math.Min(state.resources.obsidian, (time * blueprint.geode.obsidian) - (state.robots.obsidian * nextTick)) 
            } 
        };
        if (cache.Contains((state, time)))
            continue;
        cache.Add((state, time));

        var newresources = state.robots.Harvest(state.resources);

        queue.Enqueue(((newresources, state.robots), nextTick));

        if (blueprint.CanBuyOre(state.resources))
        {
            queue.Enqueue((blueprint.BuyOreRobot(newresources, state.robots), nextTick));
        }

        if (blueprint.CanBuyClay(state.resources))
        {
            queue.Enqueue((blueprint.BuyClayRobot(newresources, state.robots), nextTick));
        }

        if (blueprint.CanBuyObsidian(state.resources))
        {
            queue.Enqueue((blueprint.BuyObsidianRobot(newresources, state.robots), nextTick));
        }

        if (blueprint.CanBuyGeode(state.resources))
        {
            queue.Enqueue((blueprint.BuyGeodeRobot(newresources, state.robots), nextTick));
        }
    }

    return max;
}

record struct Blueprint(int id, int ore, (int ore, int _) clay, (int ore, int clay) obsidian, (int ore, int obsidian) geode)
{
    public bool CanBuyOre(Resources resources) => resources.ore >= ore;
    public bool CanBuyClay(Resources resources) => resources.ore >= clay.ore;
    public bool CanBuyObsidian(Resources resources) => resources.ore >= obsidian.ore && resources.clay >= obsidian.clay;
    public bool CanBuyGeode(Resources resources) => resources.ore >= geode.ore && resources.obsidian >= geode.obsidian;
    public (Resources resources, Robots robots) BuyOreRobot(Resources resources, Robots robots) => (resources with { ore = resources.ore - ore }, robots with { ore = robots.ore + 1 });
    public (Resources resources, Robots robots) BuyClayRobot(Resources resources, Robots robots) => (resources with { ore = resources.ore - clay.ore }, robots with { clay = robots.clay + 1 });
    public (Resources resources, Robots robots) BuyObsidianRobot(Resources resources, Robots robots) => (resources with { ore = resources.ore - obsidian.ore, clay = resources.clay - obsidian.clay }, robots with { obsidian = robots.obsidian + 1 });
    public (Resources resources, Robots robots) BuyGeodeRobot(Resources resources, Robots robots) => (resources with { ore = resources.ore - geode.ore, obsidian = resources.obsidian - geode.obsidian }, robots with { geode = robots.geode + 1 });
    public int MostExpensiveOre => (ore, clay.ore, obsidian.ore, geode.ore).Max();
}

record struct Robots(int ore, int clay, int obsidian, int geode)
{
    public Resources Harvest(Resources resources) => resources with { ore = resources.ore + ore, clay = resources.clay + clay, obsidian = resources.obsidian + obsidian, geode = resources.geode + geode };
}

record struct Resources(int ore, int clay, int obsidian, int geode);

static class LinqExtensions
{
    public static T Max<T>(this (T item1, T item2, T item3, T item4) tuple) where T : INumber<T>
    {
        return T.Max(tuple.item1, T.Max(tuple.item2, T.Max(tuple.item3, tuple.item4)));
    }
}
