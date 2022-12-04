if __name__ == '__main__':
    with open("day04.txt", 'r') as f:
        data = f.readlines()

    subsumingPairs = 0
    overlappingPairs = 0
    for l in data:
        elf1, elf2 = l.strip().split(',')
        elf1_1, elf1_2 = [int(x) for x in elf1.split('-')]
        elf2_1, elf2_2 = [int(x) for x in elf2.split('-')]
        if set(range(elf1_1, elf1_2 + 1)).issubset(range(elf2_1, elf2_2 + 1)) \
                or set(range(elf1_1, elf1_2 + 1)).issuperset(range(elf2_1, elf2_2 + 1)):
            subsumingPairs += 1
        if not set(range(elf1_1, elf1_2 + 1)).isdisjoint(range(elf2_1, elf2_2 + 1)):
            overlappingPairs += 1
    print("PART 1:" + str(subsumingPairs))
    print("PART 2:" + str(overlappingPairs))
