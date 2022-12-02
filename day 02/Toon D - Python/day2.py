#     A B C
# 1 X 3 0 6
# 2 Y 6 3 0
# 3 Z 0 6 3

choice = {
    'X': 1,
    'Y': 2,
    'Z': 3
}

win = {
    'A': {
        'X': 3,
        'Y': 6,
        'Z': 0
    },
    'B': {
        'X': 0,
        'Y': 3,
        'Z': 6
    },
    'C': {
        'X': 6,
        'Y': 0,
        'Z': 3
    }
}


def points(line):
    [a, x] = line.replace('\n', '').split(' ')
    return win[a][x] + choice[x]


file = open('input.txt', 'r').readlines()
print(sum(map(points, file)))

#    A B C
#  X 3 1 2
#  Y 4 5 6
#  Z 8 9 7

part2 = {
    'A': {
        'X': 3,
        'Y': 4,
        'Z': 8
    },
    'B': {
        'X': 1,
        'Y': 5,
        'Z': 9
    },
    'C': {
        'X': 2,
        'Y': 6,
        'Z': 7
    }
}


def points2(line):
    [a, x] = line.replace('\n', '').split(' ')
    return part2[a][x]


print(sum(map(points2, file)))
