if __name__ == '__main__':
    with open("day06.txt", 'r') as f:
        data = f.readlines()

    input = data[0]
    for i in range(0, len(input)-4):
        if len(list({*input[i:i + 4]})) == 4:
            print("PART 1: " + str(i+4))
            break

    input = data[0]
    for i in range(0, len(input) - 14):
        if len(list({*input[i:i + 14]})) == 14:
            print("PART 2: " + str(i + 14))
            break

