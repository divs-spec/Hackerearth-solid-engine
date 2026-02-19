import Foundation

// MARK: - Fast Input
final class FastScanner {
    private var data: [UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
    private var idx: Int = 0

    func readInt() -> Int {
        var num = 0
        var sign = 1
        while data[idx] == 10 || data[idx] == 13 || data[idx] == 32 {
            idx += 1
        }
        if data[idx] == 45 {
            sign = -1
            idx += 1
        }
        while data[idx] >= 48 {
            num = num * 10 + Int(data[idx] - 48)
            idx += 1
        }
        return num * sign
    }
}

// MARK: - Globals
let MAXM = 100_002
var primes = Array(repeating: 0, count: MAXM)
var a: [Int] = []

func gcd(_ x: Int, _ y: Int) -> Int {
    var a = x, b = y
    while b != 0 {
        let t = a % b
        a = b
        b = t
    }
    return a
}

// MARK: - Sieve Precomputation
func precomp() {
    for i in 0..<MAXM {
        primes[i] = i
    }
    for i in 2..<MAXM {
        if primes[i] == i {
            var j = i * 2
            while j < MAXM {
                primes[j] = i
                j += i
            }
        }
    }
}

// MARK: - Unique Prime Factors (Inclusion–Exclusion)
func uniqueFactors(_ x: Int) -> [(Int, Int)] {
    var used: [Int: Bool] = [:]
    var factors: [Int] = []
    var v = x

    while v > 1 {
        let p = primes[v]
        if used[p] == nil {
            used[p] = true
            factors.append(p)
        }
        v /= p
    }

    let m = 1 << factors.count
    var result: [(Int, Int)] = []

    for mask in 1..<m {
        var prod = 1
        var bits = 0
        for i in 0..<factors.count {
            if (mask & (1 << i)) != 0 {
                prod *= factors[i]
                bits += 1
            }
        }
        result.append((prod, bits & 1 == 1 ? 1 : -1))
    }
    return result
}

// MARK: - Segment Tree Node
final class Items {
    var comps: Int = 0
    var ok: [Int: Int] = [:]

    func initVal(_ val: Int) {
        comps = 1
        ok.removeAll()
        for f in uniqueFactors(val) {
            ok[f.0, default: 0] += 1
        }
    }

    func check(_ val: Int) -> Bool {
        var cnt = comps
        for f in uniqueFactors(val) {
            cnt -= (ok[f.0] ?? 0) * f.1
        }
        return cnt > 0
    }
}

// MARK: - Segment Tree
final class SegTree {
    var nodes: [Items]
    var arr: [Int]

    init(_ input: [Int]) {
        self.arr = input
        self.nodes = Array(repeating: Items(), count: 4 * input.count + 5)
        build(0, input.count, 1)
    }

    func build(_ l: Int, _ r: Int, _ k: Int) {
        if l + 1 == r {
            nodes[k].initVal(arr[l])
            return
        }
        let mid = (l + r) >> 1
        build(l, mid, k << 1)
        build(mid, r, k << 1 | 1)

        for (key, val) in nodes[k << 1].ok {
            nodes[k].ok[key, default: 0] += val
        }
        for (key, val) in nodes[k << 1 | 1].ok {
            nodes[k].ok[key, default: 0] += val
        }
        nodes[k].comps = nodes[k << 1].comps + nodes[k << 1 | 1].comps
    }

    func query(_ l: Int, _ r: Int, _ k: Int, _ pos: Int) -> Int {
        if r - l == 1 {
            if l >= pos { return -1 }
            if gcd(a[pos], a[l]) != 1 { return -1 }
            return l
        }
        let mid = (l + r) >> 1

        if mid < pos && nodes[k << 1 | 1].check(arr[pos]) {
            let res = query(mid, r, k << 1 | 1, pos)
            if res != -1 { return res }
        }
        return query(l, mid, k << 1, pos)
    }
}

// MARK: - Solve
func solve(_ scanner: FastScanner) {
    let n = scanner.readInt()
    a = Array(repeating: 0, count: n)
    for i in 0..<n {
        a[i] = scanner.readInt()
    }

    let tree = SegTree(a)
    var output = ""
    for i in 0..<n {
        output += "\(tree.query(0, n, 1, i)) "
    }
    print(output)
}

// MARK: - Main
let scanner = FastScanner()
precomp()
let t = scanner.readInt()
for _ in 0..<t {
    solve(scanner)
}
