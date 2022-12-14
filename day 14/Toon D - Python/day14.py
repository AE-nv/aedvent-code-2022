lines = open('input.txt').readlines()
grid = set()
lowest_point = 0
for line in lines:
    coordinateList = [[int(a), int(b)] for a, b in [l.split(',') for l in line.strip().split(' -> ')]]
    [x1, y1] = coordinateList[0]
    if y1 > lowest_point:
        lowest_point = y1
    for [x2, y2] in coordinateList[1:]:
        if y2 > lowest_point:
            lowest_point = y2
        if x1 == x2:
            if y1 > y2:
                for y in range(y2, y1 + 1):
                    grid.add(str([x1, y]))
            else:
                for y in range(y1, y2 + 1):
                    grid.add(str([x1, y]))
        elif y1 == y2:
            if x1 > x2:
                for x in range(x2, x1 + 1):
                    grid.add(str([x, y1]))
            else:
                for x in range(x1, x2 + 1):
                    grid.add(str([x, y1]))
        else:
            raise 'Error'
        [x1, y1] = [x2, y2]

def drop(x,y):
    if str([x,y+1]) in grid:
        if str([x-1, y+1]) in grid:
            if str([x+1,y+1]) in grid:
                grid.add(str([x,y]))
                return [x,y]
            else:
                return drop(x+1,y+1)
        else:
            return drop(x-1,y+1)
    else:
        if y > (lowest_point+4):
            return 'falling through'
        return drop(x,y+1)

count = 0
while True:
    result = drop(500,0)
    if (result != 'falling through'):
        count += 1
    else:
        break
print(count)

for i in range(-(lowest_point+2)*2, (lowest_point+2)*2):
    grid.add(str([500+i, lowest_point+2]))

while True:
    [x,y] = drop(500,0)
    if not (x == 500 and y == 0):
        count += 1
    else:
        break
print(count+1)


