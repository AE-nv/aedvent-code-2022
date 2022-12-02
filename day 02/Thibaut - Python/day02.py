def get_score(other_play, your_play):
    if your_play == 'X':
        if other_play == 'A':
            return 1 + 3
        elif other_play == 'B':
            return 1 + 0
        elif other_play == 'C':
            return 1 + 6
    elif your_play == 'Y':
        if other_play == 'A':
            return 2 + 6
        elif other_play == 'B':
            return 2 + 3
        elif other_play == 'C':
            return 2 + 0
    elif your_play == 'Z':
        if other_play == 'A':
            return 3 + 0
        elif other_play == 'B':
            return 3 + 6
        elif other_play == 'C':
            return 3 + 3


def get_required_play(other_play, outcome):
    if outcome == 'Y':
        if other_play == 'A':
            return 'X'
        elif other_play == 'B':
            return 'Y'
        elif other_play == 'C':
            return 'Z'
    elif outcome == 'Z':
        if other_play == 'A':
            return 'Y'
        elif other_play == 'B':
            return 'Z'
        elif other_play == 'C':
            return 'X'
    elif outcome == 'X':
        if other_play == 'A':
            return 'Z'
        elif other_play == 'B':
            return 'X'
        elif other_play == 'C':
            return 'Y'


if __name__ == '__main__':
    with open("day02.txt", 'r') as f:
        data = f.readlines()
    # OTHER: A is rock, B is paper, C is scissors
    # ME: Y is paper, X is rock, Z is scissors
    score = 0
    for advise in data:
        their_play, your_play = advise[0], advise[2]
        match_score = get_score(their_play, your_play)
        score += match_score
    print("Solution part 1: " + str(score))

    score = 0
    for advise in data:
        their_play, outcome = advise[0], advise[2]
        your_play = get_required_play(their_play, outcome)
        match_score = get_score(their_play, your_play)
        score += match_score
    print("Solution part 2: " + str(score))
