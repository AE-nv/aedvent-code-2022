instructions = open('input.txt').read().splitlines()

cycles = [20,60,100,140,180,220]
strengths, pixels = [], ""

X, cycle = 1, 0

def process():
    global cycle, cycles, strengths, pixels
    if cycle in cycles:
        strengths.append(cycle*X)
    if X <= cycle%40 <= X+2:
        pixels += "#"
    else:
        pixels += "."

for i in instructions:
    if i == "noop":
        cycle += 1
        process()
    else:
        number = int(i.split(' ')[-1])
        for _ in range(2):
            cycle += 1
            process()
        X += number

print(sum(strengths))
for i in range(6):
    print(pixels[40*i:40*(i+1)])