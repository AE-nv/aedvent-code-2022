from collections import deque
import collections

input_file = open('input.txt', 'r')
lines = input_file.readlines()
lines = [x.strip() for x in lines]

def handle_cd(line, directory):
    directory_to_cd_to = line.split("$ cd")[1].strip()
    
    if directory_to_cd_to == "..":
        directory = directory[:-1]
    else:
        directory.append(directory_to_cd_to)
        
    return directory
    
def handle_ls(line):
    pass
    
def handle_file_structure(line, current_directory_level, size_per_directory):
    if line.startswith("dir"):
        pass
    else:
        size = int(line.split(" ")[0].strip())
        
        current_key = ""
        for tmp_key in current_directory_level:
            current_key += tmp_key + "/"
            if current_key not in size_per_directory:
                size_per_directory[current_key] = 0
            size_per_directory[current_key] += size
        
            
    return size_per_directory

current_directory_level = []
sizes = {}

for line in lines:
    if line == "$ ls":
        handle_ls(line)
    elif line.startswith("$ cd"):
        current_directory_level = handle_cd(line, current_directory_level)
    else:
        sizes = handle_file_structure(line, current_directory_level, sizes)

total_sum = 0
for key, value in sizes.items():
    if value > 100000:
        continue
        
    total_sum += value
  
print("Total: "  + str(total_sum))
    