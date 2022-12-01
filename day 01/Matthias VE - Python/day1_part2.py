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
sorted_calories_per_elf = sorted(calories_per_elf, reverse=True)

top_3_sum = 0
for i in range(3):
    top_3_sum += sorted_calories_per_elf[i]
    
print(top_3_sum)