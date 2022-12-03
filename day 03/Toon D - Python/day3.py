lines = open('input.txt').readlines()
c = [*"0abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"]

# day 1
result = 0

for line in lines:
    line = line.strip()
    size = len(line)
    p1, p2 = line[:int(size / 2)], line[int(size / 2):]
    for x in p1:
        if x in p2:
            result += c.index(x)
            break

print(result)

# day 2
result = 0
i = 0
while i < len(lines):
    l1, l2, l3 = lines[i].strip(), lines[i+1].strip(), lines[i+2].strip()
    for e in set(l1):
        if e in l2 and e in l3:
            result += c.index(e)
    i += 3

print(result)

# oneliner part 1
print(sum("0abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".index([y for y in x[:i]if y in x[i:]][0])for x,i in([l.strip(),int((len(l)-1)/2)] for l in open('input.txt').readlines())))
