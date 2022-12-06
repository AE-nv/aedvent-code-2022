lines = open('input.txt').readlines()
containers = ['','','','','','','','','']
for line in lines:

    if line[1] == '1':
        break
    for element in range(0,len(line)):
        if (element-1) % 4 == 0:
            containers[int((element-1)/4)] += line[element]


containers = [l.strip() for l in containers]

for line in lines:
    if 'move' not in line:
        continue
    [_, amount, _, a, _, b] = line.strip().split(' ')
    amount, a, b = int(amount), int(a)-1, int(b)-1
    stack = containers[a][0:amount][::-1]
    containers[a] = containers[a][amount:]
    containers[b] = stack + containers[b]

print(''.join([e[0] for e in containers]))

containers = ['','','','','','','','','']
for line in lines:

    if line[1] == '1':
        break
    for element in range(0,len(line)):
        if (element-1) % 4 == 0:
            containers[int((element-1)/4)] += line[element]

containers = [l.strip() for l in containers]

for line in lines:
    if 'move' not in line:
        continue
    [_, amount, _, a, _, b] = line.strip().split(' ')
    amount, a, b = int(amount), int(a)-1, int(b)-1
    stack = containers[a][0:amount]
    containers[a] = containers[a][amount:]
    containers[b] = stack + containers[b]

print(''.join([e[0] for e in containers]))
