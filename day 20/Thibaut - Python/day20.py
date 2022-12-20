def getCoordinates(numbers):
    start = numbers.index(0)
    indexes = [1000, 2000, 3000]
    magic_numbers = []
    for index in indexes:
        actual_index = (start + index) % len(numbers)
        magic_numbers.append(numbers[actual_index])
    return magic_numbers

def doMixing(input, key=1, n_iterations=1):
    numbers = [n*key for n in input]
    og_positions = list(range(0, len(data)))
    for i in range(0, n_iterations):
        for og_position_that_needs_to_be_moved in range(0, len(data)):
            position = og_positions.index(og_position_that_needs_to_be_moved)
            value = numbers[position]

            del numbers[position]
            del og_positions[position]

            new_position = (position + value) % len(numbers)
            if new_position == 0:
                numbers.append(value)
                og_positions.append(og_position_that_needs_to_be_moved)
            else:
                numbers.insert(new_position, value)
                og_positions.insert(new_position, og_position_that_needs_to_be_moved)
    return numbers

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    numbers = [int(l.rstrip()) for l in data]
    has_been_moved = [False for _ in data]
    while not all(has_been_moved):
        # print(numbers)
        position = has_been_moved.index(False)
        value = numbers[position]

        del numbers[position]
        del has_been_moved[position]

        new_position = (position + value) % len(numbers)
        if new_position==0:
            numbers.append(value)
            has_been_moved.append(True)
        else:
            numbers.insert(new_position, value)
            has_been_moved.insert(new_position, True)

    print("PART 1: "+str(sum(getCoordinates(numbers))))

    with open("input.txt", 'r') as f:
        data = f.readlines()

    encryption_key = 811589153
    numbers = [int(l.rstrip()) for l in data]
    numbers = doMixing(numbers, key=811589153, n_iterations=10)

    print("PART 2: " + str(sum(getCoordinates(numbers))))