import sys
sys.setrecursionlimit(1_000_000)

from collections import defaultdict

def input():
    return sys.stdin.readline()

def max_quad_sum(path):
    n = len(path)
    if n < 4:
        return 0

    dp1 = path[:]
    dp2 = [float('-inf')] * n
    dp3 = [float('-inf')] * n
    dp4 = [float('-inf')] * n

    max_sum = 0
    for i in range(n):
        for j in range(i):
            if path[j] < path[i]:
                dp2[i] = max(dp2[i], dp1[j] + path[i])
                dp3[i] = max(dp3[i], dp2[j] + path[i])
                dp4[i] = max(dp4[i], dp3[j] + path[i])
        max_sum = max(max_sum, dp4[i])
    return max_sum


def dfs(node, parent, path, tree, A):
    path.append(A[node])
    is_leaf = True
    for neighbor in tree[node]:
        if neighbor != parent:
            is_leaf = False
            dfs(neighbor, node, path, tree, A)
    if is_leaf:
        # At a leaf, compute the best increasing subsequence of length 4
        global_max[0] = max(global_max[0], max_quad_sum(path))
    path.pop()


# Read input
n = int(sys.stdin.readline())
A = [0] + list(map(int, sys.stdin.readline().split()))  # 1-based indexing

tree = defaultdict(list)
for _ in range(n - 1):
    u, v = map(int, sys.stdin.readline().split())
    tree[u].append(v)
    tree[v].append(u)

global_max = [0]
dfs(1, -1, [], tree, A)

print(global_max[0])
