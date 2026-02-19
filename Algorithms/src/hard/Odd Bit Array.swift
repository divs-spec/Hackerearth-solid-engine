import Foundation

let MOD = 1_000_000_007

final class FastScanner {
    private var data = Array(readLine()!.split(separator: " "))
    private var idx = 0

    func nextInt() -> Int {
        if idx >= data.count {
            data = Array(readLine()!.split(separator: " "))
            idx = 0
        }
        defer { idx += 1 }
        return Int(data[idx])!
    }
}

let scanner = FastScanner()
let T = scanner.nextInt()

for _ in 0..<T {
    let n = scanner.nextInt()
    var a = [Int](repeating: 0, count: n)

    for i in 0..<n {
        let x = scanner.nextInt()
        a[i] = x.nonzeroBitCount & 1
    }

    // dpNext[j] = dp[i+1][j]
    var dpNext = [0, 1]   // dp[n][0]=0, dp[n][1]=1
    var dpCur = [0, 0]

    for i in stride(from: n - 1, through: 0, by: -1) {
        let ai = a[i]

        // j = 0
        dpCur[0] = dpNext[ai]

        // j = 1
        dpCur[1] = dpNext[1 ^ ai]
        dpCur[1] = (dpCur[1] + dpNext[ai]) % MOD

        dpNext = dpCur
    }

    print(dpNext[0])
}
