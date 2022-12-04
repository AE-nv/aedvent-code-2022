from enum import IntEnum

input_file = open('input.txt', 'r')
lines = input_file.readlines()
lines = [line.strip() for line in lines]

def score_for_character(character):

    if(character.isupper()):
        return (ord(character) - 64) + 26
    else:
        return (ord(character) - 96)

def find_doubles_and_calculate_score(first_part, second_part):
    double_character = (list(set(first_part) & set(second_part)))[0]
    return score_for_character(double_character)

score = 0
for line in lines:
    line_length = len(line)
    first_part_of_line = line[0:int(line_length/2)]
    second_part_of_line = line[int(line_length/2):line_length]
    
    score += find_doubles_and_calculate_score(first_part_of_line, second_part_of_line)
    
print(score)