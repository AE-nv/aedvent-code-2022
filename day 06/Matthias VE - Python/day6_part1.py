input_file = open('input.txt', 'r')
line = input_file.readlines()[0]

def main():
    characters_checked = 0
    last_four_characters = []

    for character in line:
        
        if len(last_four_characters) == 4:
            if len(set(last_four_characters)) == len(last_four_characters):
                return characters_checked
        
        last_four_characters.append(character)
        if len(last_four_characters) > 4:
            last_four_characters = last_four_characters[1:5]
            
        characters_checked += 1    
        
marker = main()
print(marker)