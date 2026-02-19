import Foundation

// MARK: - Fast Input
final class FastScanner {
    private var data: [Substring] = []
    private var index: Int = 0

    func readInt() -> Int64 {
        while index >= data.count {
            data = readLine()!.split(separator: " ")
            index = 0
        }
        let value = Int64(data[index])!
        index += 1
        return value
    }
}

// MARK: - Segment Tree
final class SegTree {
    let n: Int
    var t: [Int64]

    init(_ n: Int) {
        self.n = n
        self.t = Array(repeating: 0, count: 2 * n + 5)
    }

    func build() {
        if n == 0 { return }
        for i in stride(from: n - 1, through: 1, by: -1) {
            t[i] = t[i << 1] + t[i << 1 | 1]
        }
    }

    func modify(_ p: Int, _ value: Int64) {
        var p = p + n
        t[p] = value
        while p > 1 {
            p >>= 1
            t[p] = t[p << 1] + t[p << 1 | 1]
        }
    }

    func query(_ l: Int, _ r: Int) -> Int64 {
        var l = l + n
        var r = r + n + 1
        var res: Int64 = 0

        while l < r {
            if (l & 1) == 1 {
                res += t[l]
                l += 1
            }
            if (r & 1) == 1 {
                r -= 1
                res += t[r]
            }
            l >>= 1
            r >>= 1
        }
        return res
    }
}

// MARK: - Solution
func solve(scanner: FastScanner) {
    let n = Int(scanner.readInt())
    let q = Int(scanner.readInt())

    var a = [Int64](repeating: 0, count: n)
    for i in 0..<n {
        a[i] = scanner.readInt()
    }

    let st = SegTree(n + 5)
    st.build()

    var v: [(Int64, Int)] = []
    for i in 0..<n {
        v.append((a[i], i))
    }
    v.sort { $0.0 > $1.0 }

    var queries: [(Int64, Int, Int)] = []
    for i in 0..<q {
        let l = Int(scanner.readInt())
        let r = Int(scanner.readInt())
        queries.append((a[r], l, i))
    }
    queries.sort { $0.0 > $1.0 }

    var ans = [Int64](repeating: 0, count: q)
    var idx = 0

    for query in queries {
        let value = query.0
        let l = query.1
        let qi = query.2

        while idx < n && v[idx].0 > value {
            st.modify(v[idx].1, v[idx].0)
            idx += 1
        }
        ans[qi] = st.query(0, l)
    }

    print(ans.map(String.init).joined(separator: " "))
}

// MARK: - Main
let scanner = FastScanner()
let T = Int(scanner.readInt())

for _ in 0..<T {
    solve(scanner: scanner)
}
