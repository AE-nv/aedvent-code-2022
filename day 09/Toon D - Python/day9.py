lines = open('input.txt').readlines()

tailPositions = set()
head = [0, 0]
tail = [0, 0]
tailPositions.add(str(tail))

def getMoves(line):
    move, amount = line.strip().split()
    for x in range(int(amount)):
        yield move


for line in lines:
    for move in getMoves(line):
        old = [head[0],head[1]]
        match move:
            case 'D':
                head[1] -= 1
            case 'U':
                head[1] += 1
            case 'L':
                head[0] -= 1
            case 'R':
                head[0] += 1
        if (abs(head[0]-tail[0]) > 1 or abs(head[1]-tail[1]) > 1):
            tail = old
            tailPositions.add(str(tail))

print(len(tailPositions))

track = set()
track.add(str([0,0]))
rope = [[0,0] for i in range(10)]
for line in lines:
    for move in getMoves(line):
        match move:
            case 'D':
                rope[0][1] -= 1
            case 'U':
                rope[0][1] += 1
            case 'L':
                rope[0][0] -= 1
            case 'R':
                rope[0][0] += 1
        for i in range(1,10):
            dx = rope[i-1][0]-rope[i][0]
            dy = rope[i-1][1]-rope[i][1]
            if (abs(dx) > 1 or abs(dy) > 1):
                move = [0, 0]
                if dx >= 1:
                    move[0] = 1
                if dx <= -1:
                    move[0] = -1
                if dy >= 1:
                    move[1] = 1
                if dy <= -1:
                    move[1] = -1
                rope[i] = [rope[i][0] + move[0], rope[i][1] + move[1]]
                if(i == 9):
                    track.add(str(rope[9]))
print(len(track))
