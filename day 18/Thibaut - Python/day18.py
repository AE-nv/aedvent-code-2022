from collections import defaultdict
import pprint


def get_neighbours(droplet):
    x, y, z = droplet
    neighbours = []
    for xd in range(-1, 2):
        for yd in range(-1, 2):
            for zd in range(-1, 2):
                if abs(yd)+abs(xd)+abs(zd) == 1:
                    neighbours.append((x+xd, y+yd, z+zd))
    return neighbours

def group_droplets(droplets):
    grouped_droplets=[]
    connected_droplets = defaultdict(lambda: [])
    for x1, y1, z1 in droplets:
        for x2, y2, z2 in droplets:
            if abs(x1 - x2) + abs(y1 - y2) + abs(z1 - z2) == 1:
                connected_droplets[(x1, y1, z1)].append((x2, y2, z2))
    for d in droplets:
        if d not in connected_droplets.keys():
            connected_droplets[d]
    pprint.pprint(connected_droplets)
    grouped_droplets = []
    while len(connected_droplets) > 0:
        key = list(connected_droplets.keys())[0]
        connections = connected_droplets.pop(key)
        grouped_droplets.append(set())
        grouped_droplets[-1].add(key)
        while len(connections) > 0:
            key = connections.pop()
            if type(key) == list:
                print(connections)
            if key not in grouped_droplets[-1]:
                grouped_droplets[-1].add(key)
                connections.extend(connected_droplets.pop(key))
    return grouped_droplets

def get_group_surface_area(group):
    total = len(group) * 6
    for droplet in group:
        neighbours = get_neighbours(droplet)
        for n in neighbours:
            if n in group:
                total -= 1
    return total

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    droplets = []
    for l in data:
        x, y, z = [int(c) for c in l.rstrip().split(',')]
        droplets.append((x, y, z))

    grouped_droplets = group_droplets(droplets)
    pprint.pprint(grouped_droplets)
    print(len(grouped_droplets))

    group_surfaces = []
    for group in grouped_droplets:
        group_surfaces.append(get_group_surface_area(group))

    print(group_surfaces)
    print("PART 1: "+str(sum(group_surfaces)))

    #for PART 2: inverse the clusters
    # * generate all other squares that fit in the cubic volume of the droplet
    # * divide this new set op points into droplets
    # * check every droplet for presence of edges of the cubic volume => these are on the outsie
    # * all other cubes should be interior air spaces
    # => works for example, not for my input :/

    xs = [x for x, _, _ in droplets]
    x_min = min(xs)
    x_max = max(xs)
    ys = [y for _, y, _ in droplets]
    y_min = min(ys)
    y_max = max(ys)
    zs = [z for _, _, z in droplets]
    z_min = min(zs)
    z_max = max(zs)
    # air_droplets = []
    # for x in range(x_min, x_max+1):
    #     for y in range(y_min, y_max+1):
    #         for z in range(z_min, z_max+1):
    #             air_droplet = (x, y, z)
    #             if air_droplet not in droplets:
    #                 air_droplets.append(air_droplet)
    #
    # grouped_air_droplets = group_droplets(air_droplets)
    # pprint.pprint(grouped_air_droplets)
    # air_surfaces = []
    # for air_group in grouped_air_droplets:
    #     is_contained = True
    #     for x in [x_min, x_max]:
    #         for y in [y_min, y_max]:
    #             for z in [z_min, z_max]: #corners
    #                 if (x, y, z) in air_group:
    #                     is_contained = False
    #     if is_contained:
    #         print(air_group)
    #         air_surfaces.append(get_group_surface_area(air_group))
    #
    # print("PART 2: " + str(sum(group_surfaces) - sum(air_surfaces)))

    # PART 2: strategy 2 - explore the air
    frontier = [(x_min, y_min, z_min)]
    print("starting point in the air: "+str(frontier in droplets))
    visited = set()
    surface=0
    while len(frontier)>0:
        cube = frontier.pop()
        visited.add(cube)
        neighbours = get_neighbours(cube)
        for n in neighbours:
            x, y, z = n
            if n in droplets:
                surface+=1
            if n not in visited and n not in frontier and n not in droplets and x_min-1<=x<=x_max+1 and y_min-1<=y<=y_max+1 and z_min-1<=z<=z_max+1:
                frontier.append(n)
    print("PART 2: "+str(surface))








