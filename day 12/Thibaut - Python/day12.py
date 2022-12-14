import copy
import pprint
from copy import deepcopy
import numpy
from collections import defaultdict
from math import prod
from operator import add, mul
from functools import cache

import numpy as np

def in_grid(h_map, point):
    x, y = point
    return 0 <= x < len(h_map[:, 1]) and 0 <= y < len(h_map[1, :])

def find_neighbours(h_map, start):
    x, y = start
    result = []
    if in_grid(h_map, (x-1, y)) and h_map[x-1,y]-h_map[x,y]<=1:
        result.append((x-1,y))
    if in_grid(h_map, (x+1, y)) and h_map[x+1,y]-h_map[x,y]<=1:
        result.append((x+1,y))
    if in_grid(h_map, (x, y-1)) and h_map[x,y-1]-h_map[x,y]<=1:
        result.append((x,y-1))
    if in_grid(h_map, (x, y+1)) and h_map[x,y+1]-h_map[x,y]<=1:
        result.append((x,y+1))
    return result

def find_optimal_path(h_map, start, destination, past=[], cache={}):
    if start == destination:
        return [destination], 1
    else:
        neighbours = find_neighbours(h_map, start)
        best_score=10000000000000
        best_path=[]
        for n in neighbours:
            if not n in past:
                if str(n) in cache.keys():
                    print("HIT")
                    path, score = cache[str(n)]
                else:
                    print(past)
                    path, score = find_optimal_path(h_map, n, destination, past+[start], cache)
                cache[str(n)] = (path, score)
                if best_score > score:
                    best_score = score
                    best_path = path
        print(best_score)
        print(start)
        return [start]+best_path, best_score+1
        # Add start location to path

def find_optimal_path_2(h_map, start, destination):
    best_score=10000000000
    past=set()
    frontier=[(start,0)]

    while len(frontier)>0:
        node, score = frontier.pop(0)
        if node==destination:
            if score<best_score:
                best_score=score
            continue

        if node in past:
            continue
        else:
            past.add(node)

        neighbours = find_neighbours(h_map, node)
        for n in neighbours:
            frontier.append((n, score+1))
    return best_score

if __name__ == '__main__':
    with open("day12.txt", 'r') as f:
        data = f.readlines()

    h_map = np.zeros((len(data), len(data[0].rstrip())))
    starting_points=[]
    for r, row in enumerate(data):
        for c, element in enumerate(row.rstrip()):
            if element=='S':
                h_map[r,c]=ord('a')
                start=(r,c)
            elif element=='E':
                h_map[r,c]=ord('z')
                end=(r,c)
            else:
                h_map[r,c]=ord(element)

            if element=='a':
                starting_points.append((r,c))

    print(start)
    print(end)
    print(h_map)
    # path, score = find_optimal_path(h_map, start, end)
    # print(len(path)-1)
    print(find_optimal_path_2(h_map, start, end))
    scores=[]
    for start in starting_points:
        scores.append(find_optimal_path_2(h_map, start, end))
    print(min(scores))




