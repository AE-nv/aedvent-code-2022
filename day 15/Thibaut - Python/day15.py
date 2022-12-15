import pprint
from functools import cmp_to_key

def compare_bounds(b1, b2):
    if b1[0]<b2[0]:
        return 1
    else:
        return -1

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    pairs = []
    for l in data:
        sensor, beacon = l.rstrip().split(':')
        sx, sy = [int(c.split("=")[1][:-1]) for c in (sensor+":").split()[-2:]]
        bx, by = [int(c.split("=")[1][:-1]) for c in (beacon+"t").split()[-2:]]
        distance = abs(sx-bx)+abs(sy-by)
        pairs.append(((sx, sy), (bx, by), distance))
    pprint.pprint(pairs)

    beacons = [pair[1] for pair in pairs]

    count = 0
    y=2000000
    lefts = []
    rights = []
    bounds = []
    for (sx, sy), _, sensor_range in pairs:
        xr = sensor_range - abs(sy-y) + sx
        rights.append(xr)
        xl = - sensor_range + abs(sy-y) + sx
        lefts.append(xl)
        bounds.append((xl, xr))

    for x in range(min(lefts), max(rights)+1):
        for left_bound, right_bound in bounds:
            if left_bound <= x <= right_bound and (x, y) not in beacons:
                count+=1
                break
    print("PART 1: " + str(count))

    top_x = 4000000
    top_y = 4000000
    for y in reversed(range(0, top_y+1)): #For my input, this is faster :p
        bounds = []
        for (sx, sy), _, sensor_range in pairs:
            xr = sensor_range - abs(sy - y) + sx
            xl = - sensor_range + abs(sy - y) + sx
            bounds.append((xl, xr))

        found = False
        for bl1, br1 in bounds:
            to_investigate = [bl1-1, br1+1]
            for x in to_investigate:
                if 0 <= x <= top_x:
                    found_overlapping_bound = False
                    for bl2, br2 in bounds:
                        if bl2 <= x <= br2:
                            found_overlapping_bound = True
                            break
                    if not found_overlapping_bound:
                        print(x, y)
                        print("PART 2: " + str(x * 4000000 + y))
                        found = True
            if found:
                break
        if found:
            break


