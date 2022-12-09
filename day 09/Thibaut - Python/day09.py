from collections import defaultdict
import numpy as np

def part1_OG(data):
    Hx, Hy, Tx, Ty = 0, 0, 0, 0

    visited = {}
    for l in data:
        command, amount = l.rstrip().split(' ')
        for i in range(0, int(amount)):
            if command == 'R':
                Hx += 1
                if not (range(Hx - 1, Hx + 2).__contains__(Tx)
                        and range(Hy - 1, Hy + 2).__contains__(Ty)):
                    Tx = Hx - 1
                    Ty = Hy
            elif command == 'L':
                Hx -= 1
                if not (range(Hx - 1, Hx + 2).__contains__(Tx)
                        and range(Hy - 1, Hy + 2).__contains__(Ty)):
                    Tx = Hx + 1
                    Ty = Hy
            elif command == 'U':
                Hy += 1
                if not (range(Hx - 1, Hx + 2).__contains__(Tx)
                        and range(Hy - 1, Hy + 2).__contains__(Ty)):
                    Ty = Hy - 1
                    Tx = Hx
            elif command == 'D':
                Hy -= 1
                if not (range(Hx - 1, Hx + 2).__contains__(Tx)
                        and range(Hy - 1, Hy + 2).__contains__(Ty)):
                    Ty = Hy + 1
                    Tx = Hx
            visited[str((Tx, Ty))] = 0
    print("PART 1:" + str(len(visited.keys())))

def get_new_head(H, command):
    Hx, Hy = H
    if command == 'R':
        Hx += 1
    elif command == 'L':
        Hx -= 1
    elif command == 'U':
        Hy += 1
    elif command == 'D':
        Hy -= 1
    return (Hx, Hy)

def get_new_tail(H, T):
    Hx, Hy = H
    Tx, Ty = T
    if not (range(Hx - 1, Hx + 2).__contains__(Tx)
            and range(Hy - 1, Hy + 2).__contains__(Ty)):
        if (Hx, Hy) in [(Tx + 1, Ty + 2), (Tx + 2, Ty + 1), (Tx + 2, Ty + 2)]:
            Tx += 1
            Ty += 1
        elif (Hx, Hy) in [(Tx + 1, Ty - 2), (Tx + 2, Ty - 1), (Tx + 2, Ty - 2)]:
            Tx += 1
            Ty -= 1
        elif (Hx, Hy) in [(Tx - 1, Ty - 2), (Tx - 2, Ty - 1), (Tx - 2, Ty - 2)]:
            Tx -= 1
            Ty -= 1
        elif (Hx, Hy) in [(Tx - 1, Ty + 2), (Tx - 2, Ty + 1), (Tx - 2, Ty + 2)]:
            Tx -= 1
            Ty += 1
        elif Hx == Tx and Hy == Ty + 2:
            Ty += 1
        elif Hx == Tx and Hy == Ty - 2:
            Ty -= 1
        elif Hx == Tx + 2 and Hy == Ty:
            Tx += 1
        elif Hx == Tx - 2 and Hy == Ty:
            Tx -= 1
    return Tx, Ty

if __name__ == '__main__':
    with open("day09.txt", 'r') as f:
        data = f.readlines()

    rope = [(0, 0) for i in range(0, 10)]
    rope_visits = [defaultdict(int) for chain in rope]

    for l in data:
        command, amount = l.rstrip().split(' ')
        for i in range(0, int(amount)):
            rope[0] = get_new_head(rope[0], command)
            for element_i in range(1, len(rope)):
                rope[element_i] = get_new_tail(rope[element_i-1], rope[element_i])

            for chain_index, chain in enumerate(rope):
                rope_visits[chain_index][str(chain)] += 1

    print("PART 1: "+str(len(rope_visits[1].keys())))
    print("PART 2: "+str(len(rope_visits[-1].keys())))
