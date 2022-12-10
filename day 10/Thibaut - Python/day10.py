

if __name__ == '__main__':
    with open("day10.txt", 'r') as f:
        data = f.readlines()

    cycle_counter = 1
    register = 1
    screen = ""
    results = []
    for l in data:
        if cycle_counter % 40 == 20:
            results.append(cycle_counter*register)
        if (cycle_counter-1) % 40 in range(register-1, register+2):
            screen += "#"
        else:
            screen += "."
        if l.rstrip() == 'noop':
            cycle_counter += 1
        else:
            _, amount = l.rstrip().split()
            cycle_counter += 1
            if cycle_counter % 40 == 20:
                results.append(cycle_counter * register)
            if (cycle_counter-1) % 40 in range(register - 1, register + 2):
                screen += "#"
            else:
                screen += "."
            register += int(amount)
            cycle_counter += 1
    print("PART 1: "+str(sum(results)))
    print("PART 2:")
    for i in range(0, len(screen), 40):
        print(screen[i:i+40])

