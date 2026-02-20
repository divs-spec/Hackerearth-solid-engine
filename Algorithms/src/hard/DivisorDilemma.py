import sys
from array import array

def main():
    data = sys.stdin.buffer.read().split()
    it = iter(data)
    ni = lambda: int(next(it))

    MAXN = 2_000_001

    # -------- Sum of divisors --------
    sumOfDiv = array('I', [0]) * MAXN
    for i in range(1, MAXN):
        for j in range(i, MAXN, i):
            sumOfDiv[j] += i

    # -------- Bucket by sumOfDiv --------
    maxSum = max(sumOfDiv)
    buckets = [[] for _ in range(maxSum + 1)]
    for i in range(1, MAXN):
        buckets[sumOfDiv[i]].append(i)

    # -------- Build order[] --------
    order = array('I', [0]) * MAXN
    idx = 0
    for s in range(maxSum + 1):
        for v in buckets[s]:
            order[v] = idx
            idx += 1

    # free buckets ASAP
    del buckets

    # -------- Read queries --------
    Q = ni()
    queries = []
    for i in range(Q):
        N = ni()
        M = ni()
        queries.append((N, M, i))

    queries.sort(reverse=True)
    ans = array('Q', [0]) * Q

    # -------- Fenwick Tree --------
    class Fenwick:
        def __init__(self, n):
            self.n = n
            self.bit = array('Q', [0]) * (n + 1)

        def add(self, i, v):
            i += 1
            while i <= self.n:
                self.bit[i] += v
                i += i & -i

        def sum(self, i):
            s = 0
            i += 1
            while i > 0:
                s += self.bit[i]
                i -= i & -i
            return s

        def range_sum(self, l, r):
            return self.sum(r - 1) - self.sum(l - 1)

    fw_count = Fenwick(MAXN)
    fw_sum   = Fenwick(MAXN)

    qi = len(queries) - 1

    # -------- Process --------
    for i in range(1, MAXN):
        p = order[i]
        fw_count.add(p, 1)
        fw_sum.add(p, sumOfDiv[i])

        while qi >= 0 and queries[qi][0] == i:
            _, M, idx = queries[qi]
            qi -= 1

            lo, hi = 0, MAXN - 1
            best = -1
            while lo <= hi:
                mid = (lo + hi) // 2
                if fw_count.range_sum(mid, MAXN) >= M:
                    best = mid
                    lo = mid + 1
                else:
                    hi = mid - 1

            ans[idx] = fw_sum.range_sum(best, MAXN)

    # -------- Output --------
    out = sys.stdout.write
    for v in ans:
        out(str(v) + "\n")

if __name__ == "__main__":
    main()
