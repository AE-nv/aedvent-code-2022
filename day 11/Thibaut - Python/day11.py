import copy
import pprint
from copy import deepcopy
import numpy
from collections import defaultdict
from math import prod
from operator import add, mul

if __name__ == '__main__':
    with open("day11.txt", 'r') as f:
        data = f.readlines()

    monkeys = {}
    for i in range(0, len(data)+1, 7):
        monkey_i = data[i].rstrip().split()[1][:-1]
        monkey = {}
        monkey["items"] = [int(item) for item in data[i+1].rstrip().split(':')[1].split(',')]
        _, operation, value = data[i+2].rstrip().split('=')[1].split()
        monkey["function"] = {"operator": operation, "value": value}
        monkey["test"] = int(data[i+3].rstrip().split()[-1])
        monkey["True"] = data[i+4].rstrip().split()[-1]
        monkey["False"] = data[i+5].rstrip().split()[-1]
        monkeys[monkey_i] = monkey

    og_monkeys = deepcopy(monkeys)

    nb_rounds = 20
    monkey_inspections = defaultdict(lambda: 0)
    for r in range(0, nb_rounds):
        for monkey_i in range(0, int((len(data)+1)/7)):
            monkey = monkeys[str(monkey_i)]
            monkey_inspections[str(monkey_i)] += len(monkey["items"])
            for item in monkey["items"]:
                if monkey["function"]["operator"] == "+":
                    worry_level = item+int(monkey["function"]["value"])
                elif monkey["function"]["operator"] == "*":
                    if monkey["function"]["value"] == "old":
                        worry_level = item**2
                    else:
                        worry_level = item*int(monkey["function"]["value"])
                else:
                    print("OH fuck")
                worry_level = worry_level // 3
                next_monkey = monkey[str(worry_level % monkey["test"] == 0)]
                monkeys[next_monkey]["items"].append(worry_level)
            monkeys[str(monkey_i)]["items"] = []

    print("PART 1: "+str(sorted(monkey_inspections.values())[-2]*sorted(monkey_inspections.values())[-1]))

    modulus = numpy.prod([int(monkey["test"]) for monkey in og_monkeys.values()])
    print("modulus: "+str(modulus))

    nb_rounds = 10000
    monkeys = og_monkeys
    monkey_inspections = defaultdict(lambda: 0)
    for r in range(0, nb_rounds):
        for monkey_i in range(0, int((len(data)+1)/7)):
            monkey = monkeys[str(monkey_i)]
            monkey_inspections[str(monkey_i)] += len(monkey["items"])
            for item in monkey["items"]:
                worry_level = item
                if monkey["function"]["operator"] == "+":
                    worry_level = item+int(monkey["function"]["value"])
                elif monkey["function"]["operator"] == "*":
                    if monkey["function"]["value"] == "old":
                        worry_level = int(str(item))*int(str(item))
                    else:
                        worry_level = item*int(monkey["function"]["value"])
                else:
                    print("OH fuck")


                worry_level%=modulus

                next_monkey = monkey[str(worry_level % monkey["test"] == 0)]
                monkeys[next_monkey]["items"].append(worry_level)


            monkeys[str(monkey_i)]["items"] = []

        # print("finished round "+str(r))
    # pprint.pprint(monkeys)
    pprint.pprint(monkey_inspections)
    pprint.pprint(sorted(monkey_inspections.values())[-2]*sorted(monkey_inspections.values())[-1])


