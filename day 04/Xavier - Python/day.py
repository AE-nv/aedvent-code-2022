class Elf:
    def __init__(self, sections):
        start, end = sections.split('-')
        self.start = int(start)
        self.end = int(end)
    
    def contains(self, elf):
        return self.start <= elf.start and self.end >= elf.end

    def overlaps(self, elf):
        if self.end < elf.start or elf.end < self.start:
            return False
        return True

input = open('input.txt').read().splitlines()
pairs = [line.split(',') for line in input]
elves = [[Elf(elf) for elf in pair] for pair in pairs]

contained = sum(e1.contains(e2) or e2.contains(e1) for e1,e2 in elves)
print(contained)

overlapping = sum(e1.overlaps(e2) for e1,e2 in elves)
print(overlapping)