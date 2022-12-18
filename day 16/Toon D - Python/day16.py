lines = open('input.txt').readlines()


class Valve:
    def __init__(self, name, rate, others):
        self.name = name
        self.rate = rate
        self.others = others

    def __repr__(self):
        return self.name + ':' + str(self.rate) + ':' + str(self.others)


valves = dict()
for line in lines:
    valve = line[6:8]
    rate = int(line.split('=')[1].split(';')[0])
    other_valves = [s.strip().replace(',', '') for s in line.split(';')[1].split(' ')[5:]]
    valves[valve] = Valve(valve, rate, other_valves)

start = 'AA'
openable_valves = [v for v in valves if valves[v].rate > 0]


def shortest(frm, to, length, visited):
    neighbours = []
    for f in frm:
        neighbours.extend(valves[f].others)
    neighbours = [n for n in neighbours if n not in visited]
    if to in neighbours:
        return length
    else:
        visited.extend(neighbours)
        return shortest(neighbours, to, length + 1, visited)


from_dict = dict()
from_valves = openable_valves + [start]
for frm in from_valves:
    to_dict = dict()
    from_dict[frm] = to_dict
    for to in openable_valves:
        if frm == to:
            continue
        to_dict[to] = shortest([frm], to, 1, [])


def calculate_flow(time, visited):
    return sum([valves[v].rate * time for v in visited])


def find_longest(current, visited, time_left, score_until_now):
    best_score = score_until_now
    current_open = visited + [current]  # AA + all open ones
    if len(current_open) == len(from_dict):
        total_score = score_until_now + calculate_flow(time_left, current_open)
        return total_score

    changed = False
    for next in from_dict[current]:
        if next in current_open:
            continue
        time_passed = from_dict[current][next] + 1  # (move time + open time)
        time_left_next = time_left - time_passed
        if time_left_next < 0:
            continue
        score_next = calculate_flow(time_passed, current_open)
        result = find_longest(next, current_open, time_left_next, score_until_now + score_next)
        if result > best_score:
            best_score = result
            changed = True

    if not changed:
        return score_until_now + calculate_flow(time_left, current_open)
    else:
        return best_score


print(find_longest('AA', [], 30, 0))


def find_longest_with_elephant(current_positions, visited, time_left, score_until_now):
    best_score = score_until_now
    current_open = visited.copy().update(current_positions)

    for next_one in from_dict[current_positions[0]]:
        if next_one in current_open:
            continue
        for next_two in from_dict[current_positions[1]]:
            if next_two in current_open:
                continue



print(find_longest_with_elephant(['AA', 'AA'], set(), 26, 0))
