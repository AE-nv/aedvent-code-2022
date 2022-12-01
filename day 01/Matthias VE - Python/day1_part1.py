input_file = open('input.txt', 'r')
lines = input_file.readlines()

sum_of_calories = 0
calories_per_elf = []

for line in lines:
    if line != '\n':
        calories = int(line)
        sum_of_calories += calories
    else:
        calories_per_elf.append(sum_of_calories)
        sum_of_calories = 0
        
calories_per_elf.append(sum_of_calories)
print(max(calories_per_elf))
        