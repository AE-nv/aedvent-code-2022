moves = open('input.txt').read().splitlines()

def fix_tail(h,t):
    distance = [h[0]-t[0], h[1]-t[1]]
    if abs(distance[0]) > 1:
        t[0] += distance[0]/abs(distance[0])
        if abs(distance[1]) > 0:
            t[1] += distance[1]/abs(distance[1])
    elif abs(distance[1]) > 1:
        t[1] += distance[1]/abs(distance[1])
        if abs(distance[0]) > 0:
            t[0] += distance[0]/abs(distance[0])
    return t

def get_unique_tail_positions(snake, moves):
    unique_positions = set({tuple(snake[-1])})
    for move in moves:
        direction, steps = move.split(' ')
        for _ in range(int(steps)):
            if direction == 'D':
                snake[0][0] -= 1
            elif direction == 'U':
                snake[0][0] += 1
            elif direction == 'L':
                snake[0][1] -= 1
            elif direction == 'R':
                snake[0][1] += 1
            for i in range(1, len(snake)):
                snake[i] = fix_tail(snake[i-1], snake[i])
            unique_positions.add(tuple(snake[-1]))
    return unique_positions

snake = [[0,0], [0,0]]
print(len(get_unique_tail_positions(snake, moves)))

snake = [[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0]]
print(len(get_unique_tail_positions(snake, moves)))