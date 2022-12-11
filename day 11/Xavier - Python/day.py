input = open('input.txt').read().split('\n\n')

class Monkey:
    def __init__(self, params):
        self.items = [int(item) for item in params[0].split(':')[1].split(',')]
        self.operation = params[1].split('=')[1].strip()
        self.divider = int(params[2].split('by ')[1])
        self.monkey_true = int(params[3].split(' ')[-1])
        self.monkey_false = int(params[4].split(' ')[-1])
        self.inspected = 0

    def add_item(self, item):
        self.items.append(item)
    
    def inspect_item(self, modulo=None):
        self.inspected += 1
        old = self.items.pop(0)
        if modulo is None:
            new = eval(self.operation) // 3
        else:
            new = eval(self.operation) % modulo
        if new % self.divider == 0:
            return self.monkey_true, new
        return self.monkey_false, new

def play_game(monkeys, rounds, modulo=None):
    for _ in range(rounds):
        for monkey in monkeys:
            while len(monkey.items) > 0:
                target, item = monkey.inspect_item(modulo)
                monkeys[target].add_item(item)

def monkey_business(monkeys):
    inspected_items = sorted([m.inspected for m in monkeys])
    return inspected_items[-1]*inspected_items[-2]

monkeys = [Monkey(m.split('\n')[1:]) for m in input]
play_game(monkeys, 20)
print(monkey_business(monkeys))

monkeys = [Monkey(m.split('\n')[1:]) for m in input]
modulo = 1
for m in monkeys:
    modulo *= m.divider
play_game(monkeys, 10000, modulo)
print(monkey_business(monkeys))
