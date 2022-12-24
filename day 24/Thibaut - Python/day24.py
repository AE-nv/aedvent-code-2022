import math
from copy import deepcopy

if __name__ == '__main__':
    with open("example.txt", 'r') as f:
        data = f.readlines()

    y_max = len(data)-2
    x_max = len(data[0].rstrip())-2
    directions = {">": lambda a, b: ((a+1)%x_max, b),
                  "v": lambda a, b: (a, (b+1)%y_max),
                  "<": lambda a, b: ((a-1)%x_max, b),
                  "^": lambda a, b: (a, (b-1)%y_max)}
    blizzards = set()
    for y, l in enumerate(data[1:-1]):
        for x, c in enumerate(list(l)[1:-1]):
            if c in directions.keys():
                blizzards.add((x, y, c, directions[c]))

    # start = (0, -1)
    # destination = (x_max, y_max+1)
    # frontier = [(start, 0)]
    # best = math.inf
    # visited = set()
    # while len(frontier) > 0:
    #     frontier.sort(key=lambda location, step_count: 10*(location[0]+location[1])+step_count, reverse=True)
    #
    #     (x, y), steps = frontier.pop()
    #
    #     if steps > best:
    #         continue
    #     elif (x, y) == destination:
    #         best = steps
    #         break
    #
    #     if ((x, y), steps) in visited:
    #         continue
    #     visited.add(((x, y), steps))
    #
    #     for xd in range(-1, 2):
    #         if x+xd >= 0:
    #             for yd in range(-1, 2):
    #                 if y+yd>=0 and abs(xd)+abs(yd)==1:
    #                     frontier.append(((x+xd, y+yd), steps+1))
    #     frontier.append(((x, y), steps+1))
    #
    # print(best)

    start = (0, -1)
    destination = (x_max-1, y_max)
    print("destination: "+str(destination))
    frontier = {start}
    step_count = 0
    while True:
        if destination in frontier:
            break

        # if step_count % 10 == 0:
        #     print(min([abs(destination[0]-x)+abs(destination[1]-y) for x, y in frontier]))

        new_blizzards = set()
        blizzard_locations = set()
        for x, y, s, move in blizzards:
            x_n, y_n = move(x, y)
            blizzard_locations.add((x, y))
            new_blizzards.add((x_n, y_n, s, move))
        blizzards = new_blizzards

        new_frontier = set()
        for x, y in frontier:
            for xd in range(-1, 2):
                for yd in range(-1, 2):
                    if (0 <= y+yd < y_max and 0 <= x+xd < x_max and abs(xd)+abs(yd)==1) \
                            or ((x+xd, y+yd) in [start, destination]):
                            new_frontier.add((x+xd, y+yd)) #move left, right, down or up
            new_frontier.add((x, y)) #wait

        frontier = new_frontier.difference(blizzard_locations) #filter out current blizzard locations
        step_count += 1

    print("PART 1:" + str(step_count - 1))
    print("HEADING BACK TO THE START...")
    start = (x_max-1, y_max)
    destination = (0, -1)
    frontier = {start}
    step_count = 0
    while True:
        if destination in frontier:
            break

        # if step_count % 10 == 0:
        #     print(min([abs(destination[0]-x)+abs(destination[1]-y) for x, y in frontier]))

        new_blizzards = set()
        blizzard_locations = set()
        for x, y, s, move in blizzards:
            x_n, y_n = move(x, y)
            blizzard_locations.add((x, y))
            new_blizzards.add((x_n, y_n, s, move))
        blizzards = new_blizzards

        new_frontier = set()
        for x, y in frontier:
            for xd in range(-1, 2):
                for yd in range(-1, 2):
                    if (0 <= y+yd < y_max and 0 <= x+xd < x_max and abs(xd)+abs(yd)==1) \
                            or ((x+xd, y+yd) in [start, destination]):
                            new_frontier.add((x+xd, y+yd)) #move left, right, down or up
            new_frontier.add((x, y)) #wait

        frontier = new_frontier.difference(blizzard_locations) #filter out current blizzard locations
        step_count += 1

    print("took " + str(step_count) + " steps")

    print("GOING BACK TO THE END...")
    start = (0, -1)
    destination = (x_max-1, y_max)
    frontier = {start}
    step_count = 0
    while True:
        step_count += 1
        if destination in frontier:
            break

        # if step_count % 10 == 0:
        #     print(min([abs(destination[0]-x)+abs(destination[1]-y) for x, y in frontier]))

        new_blizzards = set()
        blizzard_locations = set()
        for x, y, s, move in blizzards:
            x_n, y_n = move(x, y)
            blizzard_locations.add((x, y))
            new_blizzards.add((x_n, y_n, s, move))
        blizzards = new_blizzards

        new_frontier = set()
        for x, y in frontier:
            for xd in range(-1, 2):
                for yd in range(-1, 2):
                    if (0 <= y+yd < y_max and 0 <= x+xd < x_max and abs(xd)+abs(yd)==1) \
                            or ((x+xd, y+yd) in [start, destination]):
                            new_frontier.add((x+xd, y+yd)) #move left, right, down or up
            new_frontier.add((x, y)) #wait

        frontier = new_frontier.difference(blizzard_locations) #filter out current blizzard locations

    print("took " + str(step_count) + " steps")



