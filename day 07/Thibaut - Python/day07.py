import pprint

pp = pprint.PrettyPrinter()


def get_directory(directories, path):
    if path == []:
        return directories
    else:
        for v in directories:
            if type(v) == dict and list(v.keys())[0] == path[0]:
                return get_directory(v[path[0]], path[1:])


def get_dir_size(directory):
    result = 0
    entries = list(directory.values())[0]
    for entry in entries:
        if type(entry) == dict:
            result += get_dir_size(entry)
        else:
            result += int(entry.split()[0])
    return result


if __name__ == '__main__':
    with open("day07.txt", 'r') as f:
        data = f.readlines()

    path = []
    possible_paths = []
    directories = [{'/': []}]
    ls = False
    for l in data:
        if ls and l[0] != '$':
            directory = get_directory(directories, path)
            if l.rstrip().split()[0] == 'dir':
                dir_name = l.rstrip().split()[1]
                possible_paths.append(path + [dir_name])
                directory.append({dir_name: []})
            else:
                directory.append(l.rstrip())
        elif l[0] == '$':
            ls = False
            command = l[2:].rstrip().split()
            if command[0] == 'cd':
                if command[1] == '/':
                    path = ['/']
                elif command[1] == '..':
                    path.pop()
                else:
                    path.append(command[1])
            elif command[0] == 'ls':
                ls = True

    pp.pprint(directories)
    pp.pprint(possible_paths)

    print("-----------------------------------------")

    result = 0
    sizes = {}
    for possible_path in possible_paths:
        directory = get_directory(directories, possible_path)
        size = get_dir_size({'dir': directory})
        sizes[str(possible_path)] = size
        # print(size)
        if size < 100000:
            result += size
    pp.pprint(sizes)
    print("PART 1: " + str(result))

    sizes['/'] = get_dir_size(directories[0])

    already_free_space = 70000000 - sizes['/']
    looking_for = 30000000 - already_free_space

    options = []
    for size in sizes.values():
        if size >= looking_for:
            options.append(size)

    print("PART 2: " + str(min(options)))
