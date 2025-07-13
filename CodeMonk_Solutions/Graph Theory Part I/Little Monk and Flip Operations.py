visited = [False] * 100005
g = [[] for _ in range(100005)]
a = [0] * 100005
ans = [[0] * 33 for _ in range(100005)]


def dfs(u):
    visited[u] = True
    for v in g[u]:
        if not visited[v]:
            dfs(v)
            for j in range(32):
                ans[u][j] = max(ans[u][j], ans[v][j])
    for i in range(32):
        if a[u] & (1 << i):
            if ans[u][i] % 2 == 0:
                ans[u][i] += 1
        else:
            if ans[u][i] % 2 == 1:
                ans[u][i] += 1


def main():
    N, A = map(int, input().split())
    for _ in range(N - 1):
        u, v = map(int, input().split())
        g[u].append(v)
        g[v].append(u)
    arr = list(map(int, input().split()))
    for i in range(1, N + 1):
        a[i] = int(arr[i - 1])
    dfs(A)
    ret = sum(ans[A])
    print(ret)


if __name__ == "__main__":
    main()
