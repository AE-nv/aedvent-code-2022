rock_paths = [path.split(' -> ') for path in open('input.txt').read().splitlines()]

def get_rock_locations(rock_paths):
    rocks = set()
    for path in rock_paths:
        x,y = map(int, path.pop(0).split(','))
        for coord in path:
            next_x, next_y = map(int, coord.split(','))
            for i in range(min(x, next_x), max(x, next_x)+1):
                for j in range(min(y, next_y), max(y, next_y)+1):
                    rocks.add((i,j))
            x,y = next_x, next_y
    return rocks

def drop_grain(rocks, sand, x, y, limit, floor):
    while True:
        if limit is not None:
            if y+1 > limit:
                return None
        if floor is not None:
            if y+1 == floor:
                return (x,y)
        new = (x, y+1)
        if new in rocks or new in sand:
            new = (x-1, y+1)
            if new in rocks or new in sand:
                new = (x+1, y+1)
                if new in rocks or new in sand:
                    return (x,y)
        x,y = new

rocks = get_rock_locations(rock_paths)
max_rock_depth = max(y for _,y in rocks)

sand = set()
while (grain := drop_grain(rocks, sand, 500, 0, max_rock_depth, None)) is not None:
    sand.add(grain)
print(len(sand))

sand = set()
while (grain := drop_grain(rocks, sand, 500, 0, None, max_rock_depth+2)) != (500,0):
    sand.add(grain)
print(len(sand)+1)