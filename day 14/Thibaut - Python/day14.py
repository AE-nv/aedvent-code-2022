import copy
import pprint
import numpy as np
from copy import deepcopy

def print_cave(cave):
    for r in cave:
        print("".join(r))

if __name__ == '__main__':
    with open("day14.txt", 'r') as f:
        data = f.readlines()

    paths = []
    lowest_y = 0
    highest_x = 0
    for l in data:
        path = [eval("(" + point + ")") for point in l.split('->')]
        paths.append(path)
        for point in path:
            x, y = point
            if y>lowest_y:
                lowest_y=y
            if x>highest_x:
                highest_x=x

    cave=[["." for xi in range(0, highest_x+500)] for yi in range(0, lowest_y+5)]

    for path in paths:
        for i, point in enumerate(path):
            if i>0:
                ox, oy = path[i-1]
                dx, dy = point
                if ox==dx:
                    for y in range(min([oy, dy]), max([oy, dy])+1):
                        cave[y][ox]="#"
                elif oy==dy:
                    for x in range(min([ox, dx]), max([ox, dx])+1):
                        cave[oy][x]="#"
                else:
                    print("Oh, shit!")
    og_cave = copy.deepcopy(cave)
    print_cave(cave)

    grain = (500, 0)
    grains = []
    while grain[1]<=lowest_y:
        x, y = grain
        moved=False
        for future_x, future_y in [(x, y+1), (x-1, y+1), (x+1, y+1)]:
            if cave[future_y][future_x] == ".":
                cave[future_y][future_x] = "o"
                cave[y][x] = "."
                grain=(future_x, future_y)
                moved=True
                break
        if not moved:
            grains.append(grain)
            grain=(500,0)
    print_cave(cave)
    print("PART 1: " + str(len(grains)))

    cave = og_cave
    for x in range(0, highest_x+500):
        cave[lowest_y+2][x]="#"

    grain = (500, 0)
    grains = []
    while True:
        x, y = grain
        moved = False
        for future_x, future_y in [(x, y + 1), (x - 1, y + 1), (x + 1, y + 1)]:
            if cave[future_y][future_x] == ".":
                cave[future_y][future_x] = "o"
                cave[y][x] = "."
                grain = (future_x, future_y)
                moved = True
                break
        if not moved:
            if x==500 and y==0:
                break
            else:
                grains.append(grain)
                grain = (500, 0)
    print_cave(cave)
    print("PART 2: " + str(len(grains)+1))
