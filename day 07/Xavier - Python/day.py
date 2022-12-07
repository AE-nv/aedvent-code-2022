console = open('input.txt').read().splitlines()

class Directory:
    def __init__(self, name, parent=None):
        self.name = name
        self.files = {}
        self.subdir = {'..': parent}

root = Directory("/")
pwd = root

for line in console[1:]:
    if line[:4] == "$ ls":
        continue
    if line[:4 ]== "$ cd":
        target = line.split(' ')[-1]
        pwd = pwd.subdir[target]
    else:
        item, name = line.split(' ')
        if item == "dir":
            pwd.subdir[name] = Directory(name, parent=pwd)
        else:
            size = int(item)
            pwd.files[name] = size

def get_all_directory_sizes(root):
    directory_sizes = []

    def _calculate_size(directory):
        size = sum(directory.files.values())
        for name, d in directory.subdir.items():
            if name == '..':
                continue
            size += _calculate_size(d)
        directory_sizes.append(size)
        return size
    
    _calculate_size(root)

    return directory_sizes

sizes = get_all_directory_sizes(root)
print(sum(s for s in sizes if s <= 100000))

memory, needed = 70000000, 30000000
available = memory - max(sizes)
to_free = needed-available

print(min(s for s in sizes if s >= to_free))