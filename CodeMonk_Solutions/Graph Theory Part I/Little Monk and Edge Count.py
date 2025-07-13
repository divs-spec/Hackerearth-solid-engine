n, q = map(int, input().split())
graph = [[] for _ in range(n+1)]
edges = [(0, 0)]
for _ in range(1, n):
    a, b = map(int, input().split())
    edges.append((a, b))
    graph[a].append(b)
    graph[b].append(a)

subSize, parent = [1] * (n + 1), [0] * (n + 1)
stack, done = [1], []
parent[1] = 1
while stack:
    node = stack.pop()
    done.append(node)
    for adj in graph[node]:
        if parent[adj] == 0:
            parent[adj] = node
            stack.append(adj)

while len(done) > 1:
    node = done.pop()
    subSize[parent[node]] += subSize[node]


for _ in range(q):
    t = edges[int(input())]
    child = t[0] if parent[t[0]] == t[1] else t[1]
    print(subSize[child]*(n-subSize[child]))
