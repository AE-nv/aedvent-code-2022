input_file = open('input.txt', 'r')
lines = input_file.readlines()
lines = [line.strip() for line in lines]

def get_section_as_list(section):
    split = section.split("-")
    beginning = int(split[0])
    end = int(split[1])
    
    return [x for x in range(beginning, end + 1)]
    
def one_list_contained_by_the_other(l1, l2):
    return all(elem in l1 for elem in l2) or all(elem in l2 for elem in l1)

overlap_count = 0
for line in lines:
    sections = line.split(",")
    
    first_section = get_section_as_list(sections[0])
    second_section = get_section_as_list(sections[1])
    
    overlap = one_list_contained_by_the_other(first_section, second_section)
    if overlap:
        overlap_count += 1
        
print(overlap_count)
    
