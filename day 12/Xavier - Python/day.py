grid = [[char for char in line] for line in open('input.txt').read().splitlines()]
for i in range(len(grid)):
    for j in range(len(grid[i])):
        if grid[i][j] == 'S':
            start = (i,j)
            grid[i][j] = 0
        elif grid[i][j] == 'E':
            end = (i,j)
            grid[i][j] = 25
        else:
            grid[i][j] = ord(grid[i][j])-ord('a')

def dijkstra(grid, start, end):
    distances = {}
    queue = {start: 0}
    while len(queue.values()) > 0:
        coords, distance = min(queue.items(), key=lambda x: x[1])
        del queue[coords]
        distances[coords] = distance
        height = grid[coords[0]][coords[1]]
        for dx, dy in [(-1,0),(1,0),(0,-1),(0,1)]:
            x,y = coords[0]+dx, coords[1]+dy
            if not (0<=x<len(grid) and 0<=y<len(grid[0])):
                continue
            if grid[x][y] <= height+1 and distances.get((x,y), -1) < 0:
                queue[(x,y)] = distance+1
    return distances.get(end, float('inf'))

print(dijkstra(grid, start, end))

starts = [(i,j) for i in range(len(grid)) for j in range(len(grid[0])) if grid[i][j] == 0]
print(min(dijkstra(grid, s, end) for s in starts))
