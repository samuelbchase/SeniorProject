# This program takes a complete graph of n vertices (represented by a integer number of verteces), 
# and returns a list of all possible valid permutations of per-vertex degree


# Partition logic, taken graciously from
#   https://stackoverflow.com/questions/10035752/elegant-python-code-for-integer-partitioning
def partition(n, d, depth=0):
    if d == depth:
        return [[]]
    return [
        item + [i]
        for i in range(n+1)
        for item in partition(n-i, d, depth=depth+1)
        ]


# Change this value to select how many vertices you want to be in your complete graph
vertices = 5

# Logic below, please do not change
calculatedValue = 2 * (vertices - 1)
allPartitions = [[calculatedValue - sum(p)] + p for p in partition(calculatedValue, vertices - 1)]

allUniquePartitions = set()
for x in allPartitions:
    if 0 not in x:
        # Need to convert to tuple to be accepted by a set, and needs to be sorted then reversed for readability
        allUniquePartitions.add(tuple(sorted(x)[::-1]))

# Simply prints unique partitions. Can be changed if you wish to process returned values
print(allUniquePartitions)
