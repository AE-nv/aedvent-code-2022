from copy import deepcopy

stacks, instructions = open('input.txt').read().split('\n\n')

stacks = stacks.splitlines()[:-1]
stack_indices = range(1, len(stacks[0]), 4)
stacks = [[line[i] for line in reversed(stacks) if line[i] != ' '] for i in stack_indices]

stacks_9001 = deepcopy(stacks)

for i in instructions.splitlines():
    _, amount, _, orig, _, dest = i.split(' ')
    amount, orig, dest = int(amount), int(orig)-1, int(dest)-1
    # part 1
    for _ in range(amount):
        stacks[dest].append(stacks[orig].pop())
    # part 2
    stacks_9001[dest].extend(stacks_9001[orig][-amount:])
    stacks_9001[orig] = stacks_9001[orig][:-amount]

tops = "".join(s[-1] for s in stacks)
tops_9001 = "".join(s[-1] for s in stacks_9001)
print(tops, tops_9001)
