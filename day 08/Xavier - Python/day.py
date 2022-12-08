forest = [
    [int(tree) for tree in row]
    for row in open('input.txt').read().splitlines()]

def count_visible_trees(forest):
    # edges
    visible = len(forest)*2 + (len(forest[0])-2)*2
    # inner trees
    for i in range(1, len(forest)-1):
        for j in range(1, len(forest[0])-1):
            tree = forest[i][j]
            # check all sides, one visible side is enough
            if max(forest[i][:j]) < tree:
                visible += 1
                continue
            if max(forest[i][j+1:]) < tree:
                visible += 1
                continue
            if max([forest[k][j] for k in range(i)]) < tree:
                visible += 1
                continue
            if max([forest[k][j] for k in range(i+1, len(forest))]) < tree:
                visible += 1
    return visible

print(count_visible_trees(forest))

def max_scenic_score(forest):
    max_scenic_score = 0
    # edges not included because score is always zero
    for i in range(1,len(forest)-1):
        for j in range(1, len(forest[0])-1):
            height = forest[i][j]
            left = 1
            while j-left > 0 and forest[i][j-left] < height:
                left += 1
            right = 1
            while j+right+1 < len(forest[0]) and forest[i][j+right] < height:
                right += 1
            up = 1
            while i-up > 0 and forest[i-up][j] < height:
                up += 1
            down = 1
            while i+down+1 < len(forest) and forest[i+down][j] < height:
                down += 1
            max_scenic_score = max(max_scenic_score, left*right*up*down)
    return max_scenic_score
            
print(max_scenic_score(forest))