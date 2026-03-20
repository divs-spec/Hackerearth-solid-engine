import sys
sys.setrecursionlimit(10**7)
input = sys.stdin.readline

MAXN = 10**6 + 1

# adjacency list
a = [[] for _ in range(MAXN)]
# color stacks
c = [[] for _ in range(MAXN)]

v = [0] * MAXN  # visited
b = [0] * MAXN  # colors
d = [0] * MAXN  # answer


def dfs(s, k):
    v[s] = 1

    # check k-th ancestor with same color
    if len(c[b[s]]) >= k:
        d[s] = c[b[s]][-k]
    else:
        d[s] = -1

    # push current node
    c[b[s]].append(s)

    for nei in a[s]:
        if v[nei] == 0:
            dfs(nei, k)

    # backtrack
    c[b[s]].pop()


# input
n, k = map(int, input().split())
vals = list(map(int, input().split()))

for i in range(1, n + 1):
    b[i] = vals[i - 1]

for _ in range(n - 1):
    x, y = map(int, input().split())
    a[x].append(y)
    a[y].append(x)

dfs(1, k)

# output
print(*d[1:n+1])
