from functools import reduce

rucksacks = open('input.txt').read().splitlines()

def priority(item):
    return ord(item)-96 if item.islower() else ord(item)-38

def first_common_item(*sacks):
    items = reduce(lambda a,b: set(a) & set(b), sacks)
    return list(items)[0]

priorities = []

for r in rucksacks:
    item = first_common_item(r[:len(r)//2], r[len(r)//2:])
    priorities.append(priority(item))

print(sum(priorities))

priorities = []

for i in range(len(rucksacks)//3):
    badge = first_common_item(rucksacks[i*3], rucksacks[i*3+1], rucksacks[i*3+2])
    priorities.append(priority(badge))

print(sum(priorities))
    