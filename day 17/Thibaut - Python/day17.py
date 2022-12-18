import pprint
from collections import defaultdict


def move(rock, command):
    if command==">":
        if max([x for x, _ in rock])==6:
            return rock
        else:
            return list([(x+1, y) for x, y in rock])
    elif command=="<":
        if min([x for x, _ in rock])==0:
            return rock
        else:
            return list([(x-1, y) for x, y in rock])


def get_potential_flooring(rock):
    sliced = defaultdict(lambda: [])
    for x, y in rock:
        sliced[x].append(y)
    return list([x, min(ys)-1] for x, ys in sliced.items())


def down(rock):
    return list([(x, y-1) for x, y in rock])

def display(rocks):
    max_y = max([y for _, y in rocks])
    display = [[".", ".", ".", ".", ".", ".", "."] for y in range(0, max_y+1)]
    for x, y in rocks:
        if y>=0:
            display[y][x]="#"
    for l in reversed(display):
        print("|"+"".join(l)+"|")
    print("+-------+")

def get_display(rocks):
    max_y = max([y for _, y in rocks])
    display = [[".", ".", ".", ".", ".", ".", "."] for y in range(0, max_y + 1)]
    for x, y in rocks:
        if y >= 0:
            display[y][x] = "#"
    return display


if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    commands = data[0].rstrip()

    #y on the following functions is the bottom of the rock
    rock1 = lambda y: [(2, y), (3, y), (4, y), (5, y)]
    rock2 = lambda y: [(3, y), (2, y+1), (3, y+1), (4, y+1), (3, y+2)]
    rock3 = lambda y: [(2, y), (3, y), (4, y), (4, y+1), (4, y+2)]
    rock4 = lambda y: [(2, y), (2, y+1), (2, y+2), (2, y+3)]
    rock5 = lambda y: [(2, y), (2, y+1), (3, y), (3, y+1)]
    rocks = [rock1, rock2, rock3, rock4, rock5]

    spawn_height = 3
    command_i = 0
    settled_rocks = set([(x, -1) for x in range(0, 7)])
    total_time = 4000
    deltas = []
    highest_rock_component = 0
    for rock_i in range(0, total_time):
        if rock_i%100==0:
            print(rock_i/total_time)
        rock = rocks[rock_i % len(rocks)](spawn_height)

        settled = False
        while not settled:
            command = commands[command_i]
            command_i = (command_i + 1) % len(commands)

            new_rock = move(rock, command)
            if len(settled_rocks.intersection(new_rock))==0:
                rock = new_rock

            new_rock = down(rock)
            if len(settled_rocks.intersection(new_rock))>0:
                settled_rocks = settled_rocks.union(rock)
                spawn_height = max(spawn_height, max([y for _, y in rock]) + 1 + 3)
                break
            else:
                rock = new_rock
        deltas.append(max([y for _, y in settled_rocks]) + 1-highest_rock_component)
        highest_rock_component = max([y for _, y in settled_rocks]) + 1

    highest_rock_component = max([y for _, y in settled_rocks])+1
    print("PART 1: "+str(sum(deltas[:2022])))

    print("Start looking for cycle...")
    cycle_length=0
    cycle_start=0
    for length in range(10, len(deltas)//2):
        for i in range(0, len(deltas)//2):
            first = deltas[i:i+length]
            second = deltas[i+length:i+2*length]
            if first==second:
                cycle_start = i
                cycle_length = length
                break
        if cycle_length>0:
            break
    print("Cycle found: start at step "+str(cycle_start)+" and repeats every "+str(cycle_length)+" steps")

    # example cycle start : 15
    # example cycle length : 34

    # input cycle start : 217
    # input cycle length : 1755

    head = deltas[:cycle_start]
    segment = deltas[cycle_start:cycle_start+cycle_length]

    iterations = 1000000000000
    n_segments = (iterations - cycle_start) // cycle_length
    tail_length = (iterations - cycle_start) % cycle_length
    tail = segment[:tail_length]

    print("PART 2: "+str(sum(head)+n_segments*sum(segment)+sum(tail)))

