from collections import deque

d, k, c = map(int, input().split())
a = [0] * (k + 1)
arr = [int(x) for x in input().split()]
for i in range(1, k + 1):
    a[i] = arr[i-1]
a.sort()


def check(mid):
    dp = [[float('inf')] * (d + 1) for _ in range(k + 1)]
    ans = float('inf')

    for i in range(mid + 1):
        dp[1][i] = abs(a[1] - i)
        if k == 1 and i >= d - mid:
            ans = min(ans, dp[1][i])

    for i in range(2, k + 1):
        dq = deque()
        for j in range(d + 1):
            while dq and dp[i - 1][dq[-1]] >= dp[i - 1][j]:
                dq.pop()
            while dq and dq[0] < j - mid:
                dq.popleft()
            dq.append(j)
            mn = dp[i - 1][dq[0]]
            diff = abs(a[i] - j)
            dp[i][j] = min(dp[i][j], mn + diff)
            if i == k and j >= d - mid:
                ans = min(ans, dp[i][j])

    return ans <= c


low, high, ans = 0, d, d
while low <= high:
    mid = (low + high) // 2
    chk = check(mid)
    if chk:
        high = mid - 1
        ans = mid
    else:
        low = mid + 1

print(ans)
