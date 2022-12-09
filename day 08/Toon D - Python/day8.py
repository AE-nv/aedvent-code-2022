lines = open('input.txt').readlines()
lines = [l.strip() for l in lines]
x_len = len(lines[0])
y_len = len(lines)

result = [[0 for i in range(x_len)] for j in range(y_len)]


for y in range(y_len):
    highest_left = -1
    highest_right = -1
    for x in range(x_len):
        if int(lines[y][x]) > highest_left:
            result[y][x] = 1
            highest_left = int(lines[y][x])
        if int(lines[y][x_len-x-1]) > highest_right:
            result[y][x_len-x-1] = 1
            highest_right = int(lines[y][x_len-x-1])

for x in range(x_len):
    highest_top = -1
    highest_down = -1
    for y in range(y_len):
        if int(lines[y][x]) > highest_top:
            result[y][x] = 1
            highest_top = int(lines[y][x])
        if int(lines[y_len-y-1][x]) > highest_down:
            result[y_len-y-1][x] = 1
            highest_down = int(lines[y_len-y-1][x])
print(sum([sum(e) for e in result]))


def getScore(x,y):
    if(x==45 and y==28):
        print("lala")

    tree = int(lines[y][x])
    c_left = 0
    for dx in range(1, x+1):
        i = int(lines[y][x-dx])
        c_left += 1
        if i >= tree:
            break
    c_right = 0
    for dx in range(1, x_len-x):
        i = int(lines[y][x+dx])
        c_right += 1
        if i >= tree:
            break
    c_up = 0
    for dy in range(1, y+1):
        i = int(lines[y-dy][x])
        c_up += 1
        if i >= tree:
            break
    c_down = 0
    for dy in range(1,y_len-y):
        i = int(lines[y+dy][x])
        c_down += 1
        if i >= tree:
            break
    return c_down*c_up*c_right*c_left


result2 = [[0 for i in range(x_len)] for j in range(y_len)]
for y in range(y_len):
    for x in range(x_len):
        result2[y][x] = getScore(x,y)
print(max([max(r) for r in result2]))
