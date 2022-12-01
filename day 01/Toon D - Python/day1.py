
file = open('input.txt', 'r')
lines = file.readlines()
listOfLists = []
newList = []
for line in lines:
    if line == "\n":
        listOfLists.append(newList)
        newList = []
    else:
        newList.append(int(line[:-1]))

print("part 1: ")
print(max((sum(x) for x in listOfLists)))

print("part 2:")
print(sum(sorted((sum(x) for x in listOfLists), reverse=True)[0:3]))
