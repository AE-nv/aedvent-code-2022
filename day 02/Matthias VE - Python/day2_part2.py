from enum import IntEnum

input_file = open('input.txt', 'r')
lines = input_file.readlines()
lines = [line.strip() for line in lines]

class Outcome(IntEnum):
    LOSE = 1
    DRAW = 2
    WIN = 3
    
    def get_play_for_outcome(self, other_plays):
        if self == Outcome.LOSE:
            if other_plays == Play.ROCK:
                return Play.SCISSORS
            elif other_plays == Play.PAPER:
                return Play.ROCK
            else:
                return Play.PAPER
        elif self == Outcome.DRAW:
            if other_plays == Play.ROCK:
                return Play.ROCK
            elif other_plays == Play.PAPER:
                return Play.PAPER
            else:
                return Play.SCISSORS
        else:
            if other_plays == Play.ROCK:
                return Play.PAPER
            elif other_plays == Play.PAPER:
                return Play.SCISSORS
            else:
                return Play.ROCK            

class Play(IntEnum):
    ROCK = 1
    PAPER = 2
    SCISSORS = 3
    
    def score_for_beating(self, other):  
        score = int(self)
    
        if self == Play.ROCK:
            if other == Play.ROCK:
                score += 3
            elif other == Play.PAPER:
                score += 0
            else:
                score += 6
        elif self == Play.PAPER:
            if other == Play.ROCK:
                score += 6
            elif other == Play.PAPER:
                score += 3
            else:     
                score += 0
        else:
            if other == Play.ROCK:
                score += 0
            elif other == Play.PAPER:
                score += 6
            else:
                score += 3
    
        return score

opponent_mapping = {
    'A': Play.ROCK,
    'B': Play.PAPER,
    'C': Play.SCISSORS
}

outcome_mapping = {
    'X': Outcome.LOSE,
    'Y': Outcome.DRAW,
    'Z': Outcome.WIN
}

def calculate_points_for_round(opponent_play, outcome):
    return outcome.score_for_beating(opponent_play)

total_score = 0
for line in lines:
    
    round_info = line.split(' ')
    opponent_play = opponent_mapping[round_info[0]]
    outcome = outcome_mapping[round_info[1]]
    my_play = outcome.get_play_for_outcome(opponent_play)
    
    total_score += calculate_points_for_round(opponent_play, my_play)
    
print(total_score)
        