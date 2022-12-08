import pprint
import numpy as np


def get_viewing_score(viewing_line, height):
    for (index, tree) in enumerate(viewing_line):
        if tree >= height:
            return index + 1
    return len(viewing_line)


if __name__ == '__main__':
    with open("day08.txt", 'r') as f:
        data = f.readlines()

    forest = np.array([[int(c) for c in r.strip()] for r in data])
    print(forest)

    visible_trees = []
    scores = {}
    for x in range(1, len(data) - 1):
        for y in range(1, len(data[0].strip()) - 1):
            current_tree = forest[x, y]
            obstructions = 0
            score = 1

            left = forest[x, :y]
            score *= get_viewing_score(list(reversed(left)), current_tree)
            if max(left) >= current_tree:
                obstructions += 1

            up = forest[:x, y]
            score *= get_viewing_score(list(reversed(up)), current_tree)
            if max(up) >= current_tree:
                obstructions += 1

            down = forest[x + 1:, y]
            score *= get_viewing_score(down, current_tree)
            if max(down) >= current_tree:
                obstructions += 1

            right = forest[x, y + 1:]
            score *= get_viewing_score(right, current_tree)
            if max(right) >= current_tree:
                obstructions += 1

            if obstructions < 4:
                visible_trees.append((x, y))
            scores[str((x, y))] = score

    print("PART 1: " + str(len(visible_trees) + len(data) * 2 + 2 * (len(data[0].strip()) - 2)))
    print("PART 2: " + str(max(scores.values())))
