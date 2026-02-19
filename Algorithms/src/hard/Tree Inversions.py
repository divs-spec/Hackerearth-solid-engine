import sys
import math
sys.setrecursionlimit(1 << 25)
data = sys.stdin.buffer.read().split()
it = iter(data)

def ni():
    return int(next(it))

def solve_case():
    n = ni()
    q = ni()

    # read node values (1-based)
    a_vals = [0] * (n + 1)
    for i in range(1, n + 1):
        a_vals[i] = ni()

    # graph
    g = [[] for _ in range(n + 1)]
    for _ in range(n - 1):
        x = ni(); y = ni()
        g[x].append(y)
        g[y].append(x)

    # prepare arrays for Euler tour and LCA
    # we'll determine LG from n
    LG = max(1, (n).bit_length() + 1)
    P = [[-1] * LG for _ in range(n + 1)]
    lvl = [0] * (n + 1)
    IN = [0] * (n + 1)
    OUT = [0] * (n + 1)
    ID = []  # 1-based indexing for positions; we'll add a dummy at front to align with C++ indices
    ID.append(0)  # dummy so ID[1] corresponds to first entry
    cur = 1

    def dfs(u, par):
        nonlocal cur
        # enter
        ID.append(u)
        IN[u] = cur
        cur += 1
        lvl[u] = lvl[par] + 1 if par != 0 else 1
        P[u][0] = par
        for i in range(1, LG):
            pp = P[u][i-1]
            P[u][i] = P[pp][i-1] if (pp != -1 and pp != 0) else (0 if pp != -1 else -1)
        for v in g[u]:
            if v == par: continue
            dfs(v, u)
        # exit
        ID.append(u)
        OUT[u] = cur
        cur += 1

    # run dfs from node 1, parent 0
    dfs(1, 0)
    # now cur is next unused index; valid positions are 1..cur-1
    total_positions = cur - 1

    # Fix P table: some entries may be 0 for parent 0; keep 0 as valid parent sentinel
    # (we used 0 for root parent, consistent with C++ using 0 in dfs)

    def lca(u, v):
        if lvl[u] < lvl[v]:
            u, v = v, u
        # lift u up to same level as v
        diff = lvl[u] - lvl[v]
        i = 0
        while diff:
            if diff & 1:
                u = P[u][i]
            diff >>= 1
            i += 1
        if u == v:
            return u
        # binary lift both
        for i in range(LG - 1, -1, -1):
            if P[u][i] != -1 and P[u][i] != P[v][i]:
                u = P[u][i]
                v = P[v][i]
        return P[u][0]

    def dis(u, v):
        w = lca(u, v)
        # number of nodes on path inclusive (same as C++: lvl[u] + lvl[v] - 2*lvl[w] + 1)
        return lvl[u] + lvl[v] - 2*lvl[w] + 1

    # BLOCK decomposition over Euler positions 1..total_positions
    sz = int(math.sqrt(total_positions)) or 1
    BL = [0] * (total_positions + 1)
    for i in range(1, total_positions + 1):
        BL[i] = (i - 1) // sz + 1

    # read queries and map to [l, r] on Euler tour like in C++ code
    class Qobj:
        __slots__ = ('id', 'l', 'r', 'lc')
        def __init__(self, _id, l, r, lc):
            self.id = _id; self.l = l; self.r = r; self.lc = lc

    queries = []
    for qi in range(q):
        x = ni(); y = ni()
        lc = lca(x, y)
        if IN[x] > IN[y]:
            x, y = y, x
        if lc == x:
            l = IN[x]; r = IN[y]
        else:
            l = OUT[x]; r = IN[y]
        queries.append(Qobj(qi, l, r, lc))

    # sort queries in Mo order (block of l, then r)
    queries.sort(key=lambda qq: (BL[qq.l], qq.r))

    # compress values of a to small range for freq array
    vals = sorted({a_vals[i] for i in range(1, n+1)})
    comp = {v:i+1 for i, v in enumerate(vals)}  # 1-based compressed values
    a = [0] * (n + 1)
    for i in range(1, n+1):
        a[i] = comp[a_vals[i]]
    max_val = len(vals) + 5

    # structures used by check()
    freq = [0] * (max_val)
    VIS = [0] * (n + 1)  # VIS[node] whether node currently included
    cur_sum = 0  # counts number of unordered equal-value pairs among included nodes (sum in C++)

    def check(node):
        # toggles node inclusion, updates cur_sum and freq
        nonlocal cur_sum
        val = a[node]
        # remove old contribution
        cur_sum -= (freq[val] * (freq[val] - 1)) // 2
        if VIS[node]:
            freq[val] -= 1
            VIS[node] = 0
        else:
            freq[val] += 1
            VIS[node] = 1
        # add new contribution
        cur_sum += (freq[val] * (freq[val] - 1)) // 2

    if q == 0:
        return []

    # initialize pointers like in C++: curL = Q[0].l, curR = Q[0].l - 1
    curL = queries[0].l
    curR = curL - 1
    answers = [0] * q

    for qq in queries:
        L = qq.l; R = qq.r
        while curL < L:
            check(ID[curL]); curL += 1
        while curL > L:
            curL -= 1; check(ID[curL])
        while curR < R:
            curR += 1; check(ID[curR])
        while curR > R:
            check(ID[curR]); curR -= 1

        xnode = ID[curL]; ynode = ID[curR]

        # if lca is not already inside the segment, include it temporarily
        if qq.lc != xnode and qq.lc != ynode:
            check(qq.lc)

        length = dis(xnode, ynode)
        # total unordered pairs among nodes on path = comb(len, 2) = len*(len-1)//2
        answers[qq.id] = (length * (length - 1)) // 2 - cur_sum

        if qq.lc != xnode and qq.lc != ynode:
            check(qq.lc)

    return answers

def main():
    T = ni()
    out_lines = []
    for _ in range(T):
        ans = solve_case()
        for v in ans:
            out_lines.append(str(v))
    sys.stdout.write("\n".join(out_lines))

if __name__ == "__main__":
    main()
