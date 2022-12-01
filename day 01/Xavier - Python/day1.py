elves = open("input.txt").read().split('\n\n')
calories = [sum([int(food) for food in elf.split('\n')]) for elf in elves]

print("part 1", max(calories))

print("part 2", sum(sorted(calories, reverse=True)[:3]))