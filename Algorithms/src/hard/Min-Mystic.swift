import Foundation

let N = 1000005

var v = Array(repeating: [(Int, Int64)](), count: N)
var par = Array(repeating: 0, count: N)
var dp = Array(repeating: Array(repeating: Int64(-1), count: 3), count: N)
var sumArr = Array(repeating: Array(repeating: Int64(-1), count: 3), count: N)

var c: Int64 = 0

func dfs(_ u: Int, _ p: Int) {
    par[u] = p
    for (node, _) in v[u] {
        if node != p {
            dfs(node, u)
        }
    }
}

func foo(_ u: Int, _ f: Int) -> Int64 {
    if dp[u][f] != -1 {
        return dp[u][f]
    }

    var res: Int64 = (f == 0) ? 1 : 0

    for (node, _) in v[u] {
        if node != par[u] {
            res += foo(node, (f - 1 + 3) % 3)
        }
    }

    dp[u][f] = res
    return res
}

func dfs_sum(_ u: Int, _ f: Int) -> Int64 {
    if sumArr[u][f] != -1 {
        return sumArr[u][f]
    }

    var res: Int64 = 0

    for (node, w) in v[u] {
        if node != par[u] {
            let f1 = (f - 1 + 3) % 3
            res += w * dp[node][f1] + dfs_sum(node, f1)
        }
    }

    sumArr[u][f] = res
    return res
}

func dfs_root(_ u: Int, _ p: Int) {
    let l = sumArr[u][1] + 2 * sumArr[u][2]
    c = min(c, l)

    for (node, w) in v[u] {
        if node != p {

            dp[u][0] -= dp[node][2]
            dp[u][1] -= dp[node][0]
            dp[u][2] -= dp[node][1]

            sumArr[u][0] -= w * dp[node][2] + sumArr[node][2]
            sumArr[u][1] -= w * dp[node][0] + sumArr[node][0]
            sumArr[u][2] -= w * dp[node][1] + sumArr[node][1]

            dp[node][0] += dp[u][2]
            dp[node][1] += dp[u][0]
            dp[node][2] += dp[u][1]

            sumArr[node][0] += w * dp[u][2] + sumArr[u][2]
            sumArr[node][1] += w * dp[u][0] + sumArr[u][0]
            sumArr[node][2] += w * dp[u][1] + sumArr[u][1]

            dfs_root(node, u)

            // rollback
            dp[node][0] -= dp[u][2]
            dp[node][1] -= dp[u][0]
            dp[node][2] -= dp[u][1]

            sumArr[node][0] -= w * dp[u][2] + sumArr[u][2]
            sumArr[node][1] -= w * dp[u][0] + sumArr[u][0]
            sumArr[node][2] -= w * dp[u][1] + sumArr[u][1]

            dp[u][0] += dp[node][2]
            dp[u][1] += dp[node][0]
            dp[u][2] += dp[node][1]

            sumArr[u][0] += w * dp[node][2] + sumArr[node][2]
            sumArr[u][1] += w * dp[node][0] + sumArr[node][0]
            sumArr[u][2] += w * dp[node][1] + sumArr[node][1]
        }
    }
}

// ---------- MAIN ----------

let T = Int(readLine()!)!

for _ in 0..<T {
    let n = Int(readLine()!)!

    for i in 0...n {
        v[i].removeAll()
        par[i] = 0
        dp[i] = [-1, -1, -1]
        sumArr[i] = [-1, -1, -1]
    }

    c = Int64(2e18)

    for _ in 0..<(n - 1) {
        let input = readLine()!.split(separator: " ").map { Int($0)! }
        let x = input[0], y = input[1]
        let w = Int64(input[2])

        v[x].append((y, w))
        v[y].append((x, w))
    }

    dfs(1, 0)

    for i in 1...n {
        for f in 0..<3 {
            _ = foo(i, f)
            _ = dfs_sum(i, f)
        }
    }

    dfs_root(1, 0)

    print(c)
}
