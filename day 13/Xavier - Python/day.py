import ast
import functools

packets = [ast.literal_eval(line) for line in open('input.txt').read().splitlines() if line.strip() != '']

def check_order(left, right):
    for i in range(len(left)):
        if i >= len(right):
            return 1
        if isinstance(left[i], int) and isinstance(right[i], int):
            if left[i] < right[i]:
                return -1
            if left[i] > right[i]:
                return 1
        elif isinstance(left[i], list) and isinstance(right[i], list):
            right_order = check_order(left[i], right[i])
            if right_order != 0:
                return right_order
        elif isinstance(left[i], list) and isinstance(right[i], int):
            right_order = check_order(left[i], [right[i]])
            if right_order != 0:
                return right_order
        elif isinstance(left[i], int) and isinstance(right[i], list):
            right_order = check_order([left[i]], right[i])
            if right_order != 0:
                return right_order
    if len(left) == len(right):
        return 0
    return -1

right_index_sum = sum(i+1 for i in range(len(packets)//2) if check_order(packets[i*2], packets[i*2+1])==-1)
print(right_index_sum)

divider_packets = [[[2]], [[6]]]
packets.extend(divider_packets)

packets = sorted(packets, key=functools.cmp_to_key(check_order))

decoder_key = (packets.index(divider_packets[0])+1) * (packets.index(divider_packets[1])+1)
print(decoder_key)