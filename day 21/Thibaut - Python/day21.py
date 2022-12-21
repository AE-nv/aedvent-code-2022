import pprint

def get_value(monkeys, name):
    monkey = monkeys[name]
    if type(monkey)==int:
        return monkey
    else:
        operand1 = get_value(monkeys, monkey["operand1"])
        operand2 = get_value(monkeys, monkey["operand2"])
        operation = monkey["operation"]
        if operation == "+":
            return operand1 + operand2
        elif operation == "*":
            return operand1 * operand2
        elif operation == "-":
            return operand1 - operand2
        elif operation == "/":
            return operand1 / operand2
        else:
            print("OH, fuck")

def find_human(monkeys, starting_point):
    name = "humn"
    path = [name]
    while name!="root":
        for key, monkey in monkeys.items():
            if type(monkey)!=int and (monkey["operand1"]==name or monkey["operand2"]==name):
                name = key
                path.append(name)
                break
    return path

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    monkeys = {}
    for l in data:
        name, action = l.rstrip().split(":")
        if action.lstrip().isnumeric():
            monkeys[name]=int(action)
        else:
            operand1, operation, operand2 = action.split()
            monkeys[name]={"operand1": operand1, "operation": operation, "operand2": operand2}

    print("PART 1:" + str(int(get_value(monkeys, "root"))))
    human_path = find_human(monkeys, "root")
    if monkeys["root"]["operand1"]==human_path[-2]:
        target = get_value(monkeys, monkeys["root"]["operand2"])
    else:
        target = get_value(monkeys, monkeys["root"]["operand1"])

    for i, monkey_name in enumerate(list(reversed(human_path[1:]))[1:]):
        monkey = monkeys[monkey_name]
        if monkey["operand1"] in human_path:
            value = get_value(monkeys, monkey["operand2"])
            operation = monkey["operation"]
            if operation == "+":
                target -= value
            elif operation == "*":
                target /= value
            elif operation == "-":
                target += value
            elif operation == "/":
                target *= value
            else:
                print("OH, fuck")
        elif monkey["operand2"] in human_path:
            value = get_value(monkeys, monkey["operand1"])
            operation = monkey["operation"]
            if operation == "+":
                target -= value
            elif operation == "*":
                target /= value
            elif operation == "-":
                target = value - target
            elif operation == "/":
                target = value / target
            else:
                print("OH, fuck")
        else:
            print("OH FUCK")

    print("PART 2: "+str(int(target)))

