from enum import IntEnum

input_file = open('input.txt', 'r')
lines = input_file.readlines()
lines = [line.strip() for line in lines]

def score_for_character(character):

    if(character.isupper()):
        return (ord(character) - 64) + 26
    else:
        return (ord(character) - 96)

def find_doubles_and_calculate_score(first_part, second_part, third_part):
    double_character = (list(set(first_part) & set(second_part) & set(third_part)))[0]
    return score_for_character(double_character)
    
chunks = [lines[x:x+3] for x in range(0, len(lines), 3)]

score = 0
for lines_chunks in chunks:    
    score += find_doubles_and_calculate_score(lines_chunks[0], lines_chunks[1], lines_chunks[2])
    
print(score)