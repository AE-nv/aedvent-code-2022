from enum import IntEnum

input_file = open('input.txt', 'r')
lines = input_file.readlines()
lines = [line.strip() for line in lines]

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

my_play_mapping = {
    'X': Play.ROCK,
    'Y': Play.PAPER,
    'Z': Play.SCISSORS
}

def calculate_points_for_round(opponent_play, my_play):
    return my_play.score_for_beating(opponent_play)

total_score = 0
for line in lines:
    
    round_info = line.split(' ')
    opponent_play = opponent_mapping[round_info[0]]
    my_play = my_play_mapping[round_info[1]]
    
    total_score += calculate_points_for_round(opponent_play, my_play)
    
print(total_score)
        