import pprint
from functools import cmp_to_key

def order_is_correct(left, right):
    if type(left)==list and type(right)==list:
        for i in range(0, min([len(left), len(right)])):
            check = order_is_correct(left[i],right[i])
            if check is not None:
                return check
        if len(left)<len(right):
            return True
        elif len(left)>len(right):
            return False
        else:
            return None
    elif type(left)==int and type(right)==int:
        if left<right:
            return True
        elif left>right:
            return False
        else:
            return None
    else:
        if type(left)==int:
            return order_is_correct([left], right)
        else:
            return order_is_correct(left, [right])

def compare(packet_1, packet_2):
    if (order_is_correct(packet_1, packet_2)):
        return -1
    else:
        return 1


if __name__ == '__main__':
    with open("day13.txt", 'r') as f:
        data = f.readlines()

    pairs = []
    packets=[]
    for i, l in enumerate(data):
        if l=='\n':
            packets.append(eval(data[i-2].rstrip()))
            packets.append(eval(data[i-1].rstrip()))
            pairs.append((eval(data[i-2].rstrip()), eval(data[i-1].rstrip())))

    pprint.pprint(pairs)

    correctly_ordered_pairs=[]
    for i, pair in enumerate(pairs):
        left, right = pair
        correct = order_is_correct(left, right)
        print("comparing "+str(left)+" with "+str(right))
        print(correct)
        if correct:
            correctly_ordered_pairs.append(i+1)
    print("PART 1: "+str(sum(correctly_ordered_pairs)))

    packets.append([[2]])
    packets.append([[6]])

    ordered_packets = [str(packet) for packet in sorted(packets, key=cmp_to_key(compare))]
    i1 = (ordered_packets.index(str([[2]])) + 1)
    i2 = (ordered_packets.index(str([[6]]))+1)
    print("PART 2: " + str(i1 * i2))