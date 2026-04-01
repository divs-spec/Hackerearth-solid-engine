import Foundation

let MAXM = 200005
var graph = Array(repeating: [Int](), count: MAXM)
var dp = Array(repeating: [0, 0], count: MAXM)
var n = 0

func dfs(_ src: Int, _ par: Int) {
    dp[src][0] = 0
    dp[src][1] = 0

    var hasChild = false

    for edge in graph[src] {
        if edge != par {
            dfs(edge, src)
            dp[src][0] += max(dp[edge][0], dp[edge][1])
            hasChild = true
        }
    }

    if hasChild {
        for edge in graph[src] {
            if edge != par {
                var ok = dp[src][0] + 1

                if dp[edge][1] > dp[edge][0] {
                    ok -= (dp[edge][1] - dp[edge][0])
                }

                dp[src][1] = max(dp[src][1], ok)
            }
        }
    }
}

func solve() {
    guard let line = readLine(), let nVal = Int(line) else { return }
    n = nVal

    for i in 0..<n {
        graph[i].removeAll()
    }

    for _ in 0..<(n - 1) {
        let input = readLine()!.split(separator: " ")
        let x = Int(input[0])! - 1
        let y = Int(input[1])! - 1

        graph[x].append(y)
        graph[y].append(x)
    }

    dfs(0, -1)

    let ans = max(dp[0][0], dp[0][1])
    print(ans)
}

solve()
