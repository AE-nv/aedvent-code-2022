import math
import pprint
from copy import deepcopy


# 2 + 2 + 1 => [0, 1]
# 2 + 2 => [-1, 1]
# 2 + 1 => [-2, 1]
# 2 + 0 => [2, 0]
# 2 + -1 => [1, 0]
# 2 + -2 => [0, 0]
# 1 + 1 => [2, 0]
# 1 + 0 => [1, 0]
# 1 + -1 => [0, 0]
# 1 + -2 => [-1, 0]
# 0 + -1 => [-1, 0]
# 0 + -2 => [-2, 0]
# -1 + -2 => [2, -1]
# -2 + -2 => [1, -1]
# -1 + -2 + -2 => [0, -1]
def sum_snafu(base, snafu):
    result = []
    carry = 0
    for i in range(0, max(len(base), len(snafu))):
        if i>=len(base):
            a = 0
        else:
            a = base[i]

        if i>=len(snafu):
            b = 0
        else:
            b = snafu[i]

        c = a+b+carry
        if c > 2:
            carry = 1
            result.append(c-5)
        elif c < -2:
            carry = -1
            result.append(c+5)
        else:
            carry = 0
            result.append(c)
    if carry != 0:
        result.append(carry)
    return result


if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    base = [0]
    for l in data:
        number = [{"2": 2, "1": 1, "0": 0, "-": -1, "=": -2}[c] for c in list(l.rstrip())]
        base = sum_snafu(base, list(reversed(number)))
    print("".join([{2: "2", 1: "1", 0: "0", -1: "-", -2: "="}[n] for n in reversed(base)]))
