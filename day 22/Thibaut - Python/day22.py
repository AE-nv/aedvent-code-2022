from copy import deepcopy


def update_visualization(visualization, x, y, direction):
    arrow = {0: ">", 1: "v", 2: "<", 3: "^"}[direction]
    line = list(visualization[y])
    line[x] = arrow
    visualization[y] = "".join(line)


if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    visualization = deepcopy(data[:-2])

    instructions = list(data[-1].rstrip())

    width = max([len(l.rstrip()) for l in data])
    y = 0
    x = data[0].index('.')
    direction = 0 #0 for right (>), 1 for down (v), 2 for left (<), and 3 for up (^)
    while len(instructions) > 0:
        command = instructions.pop(0)
        if command.isnumeric():
            while len(instructions) > 0 and instructions[0].isnumeric():
                command += instructions.pop(0)
        if command.isnumeric():
            print("moving in direction "+str(direction)+" for "+command+" steps")
            if direction==0:
                for i in range(0, int(command)):
                    update_visualization(visualization, x, y, direction)

                    x+=1
                    if x>=len(data[y].rstrip()):
                        other_side = data[y][min(data[y].index('.'), data[y].index('#'))]
                        if other_side=='#':
                            x-=1
                            break
                        else:
                            x=data[y].index('.')
                    elif data[y][x]=='#':
                        x-=1
                        break
            elif direction==1:
                for i in range(0, int(command)):
                    update_visualization(visualization, x, y, direction)
                    y+=1
                    if x>=len(data[y].rstrip()) or data[y][x]==' ':
                        other_side_i=0
                        for t in reversed(range(0, y)):
                            if data[t][x]==' ':
                                other_side_i=t+1
                                break
                        other_side = data[other_side_i][x]
                        if other_side=='#':
                            y-=1 #wall on the other side, stay here
                            break
                        else:
                            y=other_side_i
                    elif data[y][x]=='#':
                        y-=1
                        break
            elif direction==2:
                for i in range(0, int(command)):
                    update_visualization(visualization, x, y, direction)
                    x-=1
                    if x<0 or data[y][x]==' ':
                        other_side = data[y][len(data[y].rstrip())-1]
                        if other_side=='#':
                            x+=1 #wall on the other side, stay here
                            break
                        else:
                            x=len(data[y].rstrip())-1
                    elif data[y][x]=='#':
                        x+=1
                        break
            elif direction==3:
                for i in range(0, int(command)):
                    update_visualization(visualization, x, y, direction)
                    y-=1
                    if y<0 or data[y][x]==' ':
                        for t in range(0, len(data)):
                            if x>=len(data[t].rstrip()):
                                other_side_i=t-1
                                break
                        other_side = data[other_side_i][x]
                        if other_side=='#':
                            y+=1 #wall on the other side, stay here
                            break
                        else:
                            y=other_side_i
                    elif data[y][x]=='#':
                        y+=1
                        break

        else:
            if command=='R':
                direction = (direction + 1) % 4
            else:
                direction = (direction - 1) % 4
        # update_visualization(visualization, x, y, direction)

    for l in visualization:
        print(l.rstrip())
    print("============================================")
    print(x, y, direction)
    print(1000*(y+1)+4*(x+1)+direction)



