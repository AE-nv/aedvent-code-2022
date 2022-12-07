lines = open('input.txt').readlines()


class Dir:
    def __init__(self, name, parent):
        self.parent = parent
        self.name = name
        self.children = {}
        self.files = {}

    def addChild(self, dir):
        self.children[dir.name] = dir

    def addFile(self, name, size):
        self.files[name] = size

    def goTo(self, child):
        if child == '..':
            return self.parent
        if child in self.children:
            return self.children[child]
        else:
            self.children[child] = Dir(child, self)
            return self.children[child]

    def getSize(self):
        return sum([self.files[k] for k in self.files]) + sum([self.children[k].getSize() for k in self.children])

    def getSpecialSumSize(self):
        childSum = sum([self.children[k].getSpecialSumSize() for k in self.children])
        ownSize = self.getSize()
        if ownSize <= 100000:
            return childSum + ownSize
        else:
            return childSum

    def getClosestTo(self, currentClosest, sizeNeeded, sizeClosest):
        size = self.getSize()
        if sizeNeeded - size < 0 and sizeNeeded - size > sizeNeeded - sizeClosest:
            currentClosest = self,
            sizeClosest = size

        for child in self.children:
            (currentClosest, sizeClosest) = self.children[child].getClosestTo(currentClosest, sizeNeeded, sizeClosest)
        return (currentClosest, sizeClosest)

baseDir = Dir('/', None)
currentDir = baseDir
for line in lines:
    if line[0] == '$':
        if '/' in line:
            currentDir = baseDir
        elif 'cd' in line:
            currentDir = currentDir.goTo(line.strip().split(' ')[2])
    else:
        [a, b] = line.strip().split(' ')
        if a=='dir':
            currentDir.addChild(Dir(b, currentDir))
        else:
            currentDir.addFile(b, int(a))
print(baseDir.getSpecialSumSize())

available = 70000000
used = baseDir.getSize()
update = 30000000
(dir, size) = (baseDir.getClosestTo(baseDir, update - (available - used), baseDir.getSize()))
print(size)
print(available-used+size)
