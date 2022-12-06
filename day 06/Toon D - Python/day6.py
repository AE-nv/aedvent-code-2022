line = open('input.txt').readlines()[0]

i = 0
while True:
    if len(set(line[i:i+4])) == 4:
        print(i+4)
        break
    i+=1

i = 0
while True:
    if len(set(line[i:i+14])) == 14:
        print(i+14)
        break
    i += 1
