from collections import defaultdict


def get_priority(intersection):
    if intersection.isupper():
        return ord(intersection) - 38
    else:
        return ord(intersection) - 96


if __name__ == '__main__':
    with open("day03.txt", 'r') as f:
        data = f.readlines()

    score=0
    for l in data:
        sack = l.strip()
        p1 = sack[:len(sack)//2]
        p2 = sack[len(sack)//2:]
        intersection = list(set(p1).intersection(set(p2)))[0]
        score+=get_priority(intersection)
    print("Part 1: " + str(score))

    score=0
    for x in range(0, len(data), 3):
        team = data[x:x+3]
        intersection = list(set(team[0].strip()).intersection(set(team[1].strip())).intersection(set(team[2].strip())))[0]
        score += get_priority(intersection)
    print("Part 2: " + str(score))

