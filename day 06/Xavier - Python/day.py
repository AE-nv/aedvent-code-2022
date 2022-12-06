datastream = open('input.txt').read().strip()

def find_marker(datastream, num_unique):
    for i in range(num_unique, len(datastream)):
        if len(set(datastream[i-num_unique:i])) == num_unique:
            return i

print(find_marker(datastream, 4))
print(find_marker(datastream, 14))