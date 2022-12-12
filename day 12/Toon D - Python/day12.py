class Node:
    def __init__(self, elevation):
        self.neighbours = []
        self.elevation = elevation
        self.explored = False
        self.parent = None

    def add(self, node):
        if node.elevation <= self.elevation + 1:
            self.neighbours.append(node)

    def __repr__(self):
        return chr(self.elevation)

    def explore(self, node):
        self.explored = True
        self.parent = node

    def reset(self):
        self.parent = None
        self.explored = False


grid = dict()

lines = open('input.txt').readlines()


def in_grid(lines, x, y):
    return -1 < x < len(lines[0].strip()) and -1 < y < len(lines)


start = None
end = None
for y in range(len(lines)):
    for x in range(len(lines[0].strip())):
        if lines[y][x] == 'S':
            node = Node(ord('a'))
            start = node
        elif lines[y][x] == 'E':
            node = Node(ord('z'))
            end = node
        else:
            elevation = ord(lines[y][x])
            node = Node(elevation)

        for dx, dy in [[-1, 0], [1, 0], [0, -1], [0, 1]]:
            k = str([x + dx, y + dy])
            if k in grid:
                grid[k].add(node)
                node.add(grid[k])
        grid[str([x, y])] = node



def reset():
    for key in grid:
        grid[key].reset()


def get_path(start):
    q = [start]
    while len(q) != 0:
        next = q[0]
        q = q[1:]
        if next == end:
            length = 1
            while next.parent != start:
                length += 1
                next = next.parent
            return length
        else:
            for n in next.neighbours:
                if not n.explored:
                    n.explore(next)
                    q.append(n)


print('Part 1: ' + str(get_path(start)))
results = []
for i in range(0, len(lines)):
    reset()
    results.append(get_path(grid[str([0, i])]))

print('Part 2: ' + str(sorted(results)[0]))
