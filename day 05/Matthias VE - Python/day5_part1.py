import queue

input_file = open('input.txt', 'r')
lines = input_file.readlines()
lines = [line.rstrip() for line in lines]

crates = {}
setup_lines = []

def handle_instructions(line, number_of_columns):
    for i in range(number_of_columns):
        current_index = i + 1
        next_letter_index = 1 + i * 4
        if next_letter_index >= len(line):
            return
        
        next_letter = line[next_letter_index]
        if len(next_letter.strip()) == 0:
            continue
    
        if current_index not in crates:
            crates[current_index] = list()
           
        crates[current_index].append(next_letter)

def handle_initial_crate_setup(line):  
    if line[1] == '1':
        number_of_columns = int(line.split(" ").pop())
        for setup_line in setup_lines:
            handle_instructions(setup_line, number_of_columns)
        finalize()
    else:
        setup_lines.append(line)
        
def finalize():
    for crate in crates:
        crates[crate].reverse()
        copy = [x for x in crates[crate]]
        crates[crate] = queue.LifoQueue()
        
        for i in range(len(copy)):
            crates[crate].put(copy[i])

        
def handle_move_crate(line):
    split_line = line.split(" ")
    amount = int(split_line[1])
    from_stack = int(split_line[3])
    to_stack = int(split_line[5])
    
    for i in range(amount):
        element = crates[from_stack].get()
        crates[to_stack].put(element)
    

handle_setup = True

for line in lines:
    if len(line) == 0:
        handle_setup = False
        continue
    
    if handle_setup:
        handle_initial_crate_setup(line)
    else:
        handle_move_crate(line)
        
current_string = ""
for crate in sorted(crates.keys()):
    current_string += crates[crate].get()
    pass
     
print(current_string)