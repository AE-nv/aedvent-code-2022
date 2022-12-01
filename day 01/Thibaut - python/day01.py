if __name__ == '__main__':
    with open("day01.txt", 'r') as f:
        data = f.readlines()
    print()
    elves = []
    elf_accumulator = 0
    for line in data:
        if line == '\n':
            elves.append(elf_accumulator)
            elf_accumulator = 0
        else:
            elf_accumulator += int(line)
    print("Answer to part 1: " + str(max(elves)))
    print("Answer to part 2: " + str(sum(sorted(elves)[-3:])))
