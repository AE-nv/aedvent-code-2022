from collections import defaultdict

def visualize(elves):
    y_min = min([y for _, y in elves])
    x_min = min([x for x, _ in elves])
    display = set()
    for x, y in elves:
        display.add((x-x_min, y-y_min))
    y_max = max([y for _, y in display])
    x_max = max([x for x, _ in display])
    viz = []
    for y in range(0, y_max+1):
        viz.append("")
        for x in range(0, x_max+1):
            if (x, y) in display:
                display.remove((x, y))
                viz[-1] += "#"
            else:
                viz[-1] += "."
    for l in viz:
        print(l)




if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    elves=set()
    for y, l in enumerate(data):
        for x, c in enumerate(l.rstrip()):
            if c=="#":
                elves.add((x,y))

    north = lambda x, y: (lambda a, b: (a, b-1), [(x-1, y-1), (x, y-1), (x+1, y-1)])
    east = lambda x, y: (lambda a, b: (a+1, b), [(x+1, y-1), (x+1, y), (x+1, y+1)])
    south = lambda x, y: (lambda a, b: (a, b+1), [(x+1, y+1), (x, y+1), (x-1, y+1)])
    west = lambda x, y: (lambda a, b: (a-1, b), [(x-1, y+1), (x-1, y), (x-1, y-1)])
    directions = [north, south, west, east]

    n_rounds = 10
    for i in range(0, n_rounds):
        print("ITERATION "+str(i))
        visualize(elves)
        movable = set()
        for x, y in elves:
            neighbours = [(x+1, y), (x+1, y+1), (x, y+1), (x-1, y), (x-1, y-1), (x, y-1), (x-1, y+1), (x+1, y-1)]
            if len(elves.intersection(neighbours)) > 0:
                movable.add((x,y))

        targets = defaultdict(lambda: [])
        for x, y in movable:
            for direction in directions:
                move, check = direction(x, y)
                if len(elves.intersection(check))==0:
                    target = move(x, y)
                    targets[target].append((x, y))
                    break

        direction = directions.pop(0) #cycle the direction
        directions.append(direction)

        for target, originators in targets.items():
            if len(originators)==1:
                elves.remove(originators[0])
                elves.add(target)

    y_min = min([y for _, y in elves])
    h = max([y for _, y in elves]) - y_min + 1
    x_min = min([x for x, _ in elves])
    w = max([x for x, _ in elves]) - x_min + 1

    print("PART 1: " + str(h*w - len(elves)))

    with open("input.txt", 'r') as f:
        data = f.readlines()

    elves=set()
    for y, l in enumerate(data):
        for x, c in enumerate(l.rstrip()):
            if c=="#":
                elves.add((x,y))

    north = lambda x, y: (lambda a, b: (a, b-1), [(x-1, y-1), (x, y-1), (x+1, y-1)])
    east = lambda x, y: (lambda a, b: (a+1, b), [(x+1, y-1), (x+1, y), (x+1, y+1)])
    south = lambda x, y: (lambda a, b: (a, b+1), [(x+1, y+1), (x, y+1), (x-1, y+1)])
    west = lambda x, y: (lambda a, b: (a-1, b), [(x-1, y+1), (x-1, y), (x-1, y-1)])
    directions = [north, south, west, east]

    has_moved = True
    iterations = 0
    while has_moved:
        iterations+=1
        has_moved = False
        movable = set()
        for x, y in elves:
            neighbours = [(x + 1, y), (x + 1, y + 1), (x, y + 1), (x - 1, y), (x - 1, y - 1), (x, y - 1),
                          (x - 1, y + 1), (x + 1, y - 1)]
            if len(elves.intersection(neighbours)) > 0:
                movable.add((x, y))

        targets = defaultdict(lambda: [])
        for x, y in movable:
            for direction in directions:
                move, check = direction(x, y)
                if len(elves.intersection(check)) == 0:
                    target = move(x, y)
                    targets[target].append((x, y))
                    break

        direction = directions.pop(0)  # cycle the direction
        directions.append(direction)

        for target, originators in targets.items():
            if len(originators) == 1:
                elves.remove(originators[0])
                elves.add(target)
                has_moved = True

    print("PART 2: " + str(iterations))
