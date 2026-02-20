import Foundation

// Fast IO
final class FastScanner {
    private var data:[UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
    private var idx: Int = 0
    func readInt() -> Int {
        var num = 0
        var sign = 1
        while data[idx] == 10 || data[idx] == 13 || data[idx] == 32 { idx += 1 }
        if data[idx] == 45 { sign = -1; idx += 1 }
        while data[idx] >= 48 {
            num = num * 10 + Int(data[idx] - 48)
            idx += 1
        }
        return num * sign
    }
}

// Iterative Segment Tree
final class SegmentTree {
    let n: Int
    var t: [Int64]

    init(_ n: Int) {
        self.n = n
        self.t = Array(repeating: 0, count: 2 * n)
    }

    func update(_ pos: Int, _ val: Int64) {
        var p = pos + n
        t[p] = val
        while p > 1 {
            p >>= 1
            t[p] = t[p << 1] + t[p << 1 | 1]
        }
    }

    func query(_ l: Int, _ r: Int) -> Int64 {
        var l = l + n
        var r = r + n
        var res: Int64 = 0
        while l < r {
            if (l & 1) == 1 { res += t[l]; l += 1 }
            if (r & 1) == 1 { r -= 1; res += t[r] }
            l >>= 1
            r >>= 1
        }
        return res
    }
}

// Main
let scanner = FastScanner()
let MAXN = 2_000_001

// Sum of divisors
var sumOfDiv = Array(repeating: Int64(0), count: MAXN)
for i in 1..<MAXN {
    var j = i
    while j < MAXN {
        sumOfDiv[j] += Int64(i)
        j += i
    }
}

// Order by sum of divisors
var orderArr = [(Int64, Int)]()
orderArr.reserveCapacity(MAXN)
for i in 1..<MAXN {
    orderArr.append((sumOfDiv[i], i))
}
orderArr.sort { $0.0 < $1.0 }

var order = Array(repeating: 0, count: MAXN)
for i in 0..<orderArr.count {
    order[orderArr[i].1] = i
}

// Read queries
let Q = scanner.readInt()
var queries = [(Int, Int, Int)]()   // (N, M, index)
queries.reserveCapacity(Q)

for i in 0..<Q {
    let N = scanner.readInt()
    let M = scanner.readInt()
    queries.append((N, M, i))
}

// Sort descending by N
queries.sort { $0.0 > $1.0 }

let segCount = SegmentTree(MAXN)
let segSum   = SegmentTree(MAXN)

var ans = Array(repeating: Int64(0), count: Q)

var qi = queries.count - 1

for i in 1..<MAXN {
    let pos = order[i]
    segCount.update(pos, 1)
    segSum.update(pos, sumOfDiv[i])

    while qi >= 0 && queries[qi].0 == i {
        let (_, M, idx) = queries[qi]
        qi -= 1

        var low = 0
        var high = MAXN - 1
        var best = -1

        while low <= high {
            let mid = (low + high) >> 1
            if segCount.query(mid, MAXN) >= Int64(M) {
                best = mid
                low = mid + 1
            } else {
                high = mid - 1
            }
        }

        ans[idx] = segSum.query(best, MAXN)
    }
}

// Output
var output = ""
for v in ans {
    output += "\(v)\n"
}
print(output, terminator: "")
