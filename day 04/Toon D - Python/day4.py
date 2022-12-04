lines = open('input.txt').readlines()
numbers = [[[int(c),int(d)],[int(e),int(f)]]for [[c,d],[e,f]] in [[a.split('-'),b.split('-')] for [a,b] in [line.strip().split(',') for line in lines]]]

result = 0
for [[c,d],[e,f]] in numbers:
    if c <= e and d >= f:
        result += 1
    elif e <= c and f >= d:
        result += 1

print(result)
result2 = 0
for [[c,d],[e,f]] in numbers:
    if len(set(range(c,d+1)).intersection(set(range(e,f+1)))) > 0:
        result2 += 1

print(result2)

# oneliners
print(sum([(g.issubset(h)or h.issubset(g))for[g,h]in[[set(range(c,d+1)),set(range(e,f+1))]for[[c,d],[e,f]]in[[[int(i)for i in a.split('-')],[int(i)for i in b.split('-')]]for[a,b]in[l.strip().split(',')for l in open('input.txt').readlines()]]]]))
print(sum([1for[[g,h],[i,j]]in[[a.split('-'),b.split('-')]for[a,b]in[l.strip().split(',')for l in open('input.txt').readlines()]]if len(set(range(int(g),int(h)+1))&set(range(int(i),int(j)+1))) > 0]))
