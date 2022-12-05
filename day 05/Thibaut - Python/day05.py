from copy import deepcopy

if __name__ == '__main__':
    with open("day05.txt", 'r') as f:
        data = f.readlines()

    drawing = []
    instructions = []
    instructions_reached = False
    for l in data:
        if l == '\n':
            instructions_reached = True
        else:
            if instructions_reached:
                instructions.append(l)
            else:
                drawing.append(l)

    columns={}
    for col in range(1,10):
        columns[str(col)]=[]
    for row in reversed(drawing[:-1]):
        for col_i in range(0,9):
            index = col_i*4+1
            if index<len(row):
                letter = row[index]
                if letter!=' ':
                    columns[str(col_i+1)].append(letter)
    print(columns)

    columns_2 = deepcopy(columns)
    for l in instructions:
        _, amount, _, source, _, destination = l.split(' ')
        temp = []
        for x in range(0, int(amount)):
            crate = columns[source].pop()
            columns[destination.strip()].append(crate)

            crate = columns_2[source].pop()
            temp.append(crate)
        for crate in reversed( temp):
            columns_2[destination.strip()].append(crate)
    print(columns)

    solution=''
    solution_2=''
    for col in columns:
        solution+=columns[col][-1]
        solution_2+=columns_2[col][-1]
    print(solution)
    print(solution_2)
    #pop number of elements of the stack and then append them to the correct stack
    #
