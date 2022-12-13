import ast
lines = open('input.txt').readlines()

cache = {}


def compare_cached(a, b):
    k = str(a) + str(b)
    if k not in cache:
        cache[k] = compare(a, b)
    return cache[k]


def compare(a, b):
    if isinstance(a, int) and isinstance(b, int):
        if a == b:
            return None
        return a < b
    elif isinstance(a, list) and isinstance(b, list):
        x = -1
        r = None
        while r is None:
            x += 1
            if len(a) <= x and len(b) <= x:
                return None
            elif len(a) <= x:
                return True
            elif len(b) <= x:
                return False
            r = compare_cached(a[x], b[x])
        return r
    elif isinstance(a, int):
        return compare_cached([a], b)
    else:
        return compare_cached(a, [b])


results = []
i = 0
while i < len(lines):
    l1 = ast.literal_eval(lines[i])
    l2 = ast.literal_eval(lines[i+1])
    if compare_cached(l1, l2):
        results.append(i/3+1)
    i += 3

print(sum(results))

s = [[[2]], [[6]]]

for line in lines:
    if line == '\n':
        continue
    l = ast.literal_eval(line)
    i = 0
    while i < len(s) and compare(s[i], l):
        i += 1
    s = s[0:i] + [l] + s[i:]

print((s.index([[2]])+1) * (s.index([[6]])+1))

