import math


class Monkey:
    def __init__(self, items: list, oType: str, oAmount: int, test: int, ifTrue: int, ifFalse: int):
        self.items = items
        self.oType = oType
        self.oAmount = oAmount
        self.test = test
        self.ifTrue = ifTrue
        self.ifFalse = ifFalse
        self.inspected = 0
    def has_inspected(self):
        self.inspected += len(self.items)
        self.items = []

    def __repr__(self):
        return str(self.inspected)


lines = open('input.txt').readlines()
monkeys = []
for i in range(0, len(lines), 7):
    items = [int(l) for l in lines[i + 1].strip().split(':')[1].split(', ')]
    oType, oAmount = lines[i + 2].strip().split('old ')[1].split(' ')
    if oAmount == 'old':
        oType = '* old'
        oAmount = '0'
    test = int(lines[i + 3].strip().split(' ')[-1])
    ifTrue = int(lines[i + 4].strip().split(' ')[-1])
    ifFalse = int(lines[i + 5].strip().split(' ')[-1])
    monkeys.append(Monkey(items, oType, int(oAmount), test, ifTrue, ifFalse))

part1 = False
if part1:
    rounds = 20
else:
    rounds = 10000

divider = 1
for m in monkeys:
    divider = divider * m.test
for round in range(0, rounds):
    for m in monkeys:
        for item in m.items:
            if m.oType == '*':
                newItem = item * m.oAmount
            elif m.oType == '+':
                newItem = item + m.oAmount
            else:
                newItem = item * item

            if part1:
                newItem = math.floor(newItem/3)
            else:
                newItem = newItem % divider

            if newItem % m.test == 0:
                monkeys[m.ifTrue].items.append(newItem)
            else:
                monkeys[m.ifFalse].items.append(newItem)
        m.has_inspected()

inspects = sorted([m.inspected for m in monkeys], reverse=True)[0:2]
print(str(inspects[0] * inspects[1]))
