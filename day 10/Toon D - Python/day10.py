lines = open('input.txt').readlines()

busy = False
instruction_counter = 0
instruction_length = len(lines)
x = 1
cycle = 0
strenghts = []

while busy or instruction_counter < instruction_length or cycle < 221:
    # start cycle
    cycle += 1
    # during cycle
    if (cycle - 20) % 40 == 0:
        strenghts += [cycle * x]
    # do next instruction
    if not busy:
        line = lines[instruction_counter].strip()
        if line != 'noop':
            amount = int(line.split(' ')[1])
            busy = True
        instruction_counter += 1
    # end of cycle
    elif busy:
        busy = False
        x += amount

print(sum(strenghts))

busy = False
instruction_counter = 0
instruction_length = len(lines)
x = 1
cycle = 0
row = ''
rows = []

while busy or instruction_counter < instruction_length or cycle < 221:
    # start cycle
    cycle += 1
    # during cycle
    if abs(((cycle-1) % 40) - x) <= 1:
        row += '#'
    else:
        row += '_'
    if cycle % 40 == 0:
        rows += [row]
        row = ''
    # do next instruction
    if not busy:
        line = lines[instruction_counter].strip()
        if line != 'noop':
            amount = int(line.split(' ')[1])
            busy = True
        instruction_counter += 1
    # end of cycle
    elif busy:
        busy = False
        x += amount
for r in rows:
    print(r)
