import Foundation

struct Node {
    var cnt: Int64 = 0
    var lcnt: Int64 = 0
    var rcnt: Int64 = 0
    var left: Int = 0
    var right: Int = 0
    var isGood: Bool = true

    init() {}

    init(_ u: Int) {
        let val: Int64 = (u > 0 ? 1 : 0)
        cnt = val
        lcnt = val
        rcnt = val
        left = u
        right = u
        isGood = true
    }

    static func merge(_ a: Node, _ b: Node) -> Node {
        if a.left == 0 { return b }
        if b.left == 0 { return a }

        var f = Node()
        f.cnt = a.cnt + b.cnt

        if a.right <= b.left {
            f.cnt += a.rcnt * b.lcnt
            f.isGood = a.isGood && b.isGood
            f.lcnt = (a.isGood ? 1 : 0) * b.lcnt
            f.rcnt = (b.isGood ? 1 : 0) * a.rcnt
        } else {
            f.isGood = false
        }

        f.lcnt += a.lcnt
        f.rcnt += b.rcnt
        f.left = a.left
        f.right = b.right

        return f
    }
}

final class SegTree {
    let n: Int
    var tree: [Node]

    init(_ s: [Int]) {
        n = s.count
        tree = Array(repeating: Node(), count: 2 * n)

        // build leaves
        for i in 0..<n {
            tree[n + i] = Node(s[i])
        }

        // build internal nodes
        for i in stride(from: n - 1, through: 1, by: -1) {
            tree[i] = Node.merge(tree[i << 1], tree[i << 1 | 1])
        }
    }

    func query(_ l: Int, _ r: Int) -> Node {
        var l = l + n - 1
        var r = r + n - 1

        var leftRes = Node()
        var rightRes = Node()

        while l <= r {
            if (l & 1) == 1 {
                leftRes = Node.merge(leftRes, tree[l])
                l += 1
            }
            if (r & 1) == 0 {
                rightRes = Node.merge(tree[r], rightRes)
                r -= 1
            }
            l >>= 1
            r >>= 1
        }

        return Node.merge(leftRes, rightRes)
    }
}

// Fast IO
final class FastInput {
    private var data: [String] = []
    private var idx = 0

    init() {
        while let line = readLine() {
            data += line.split(separator: " ").map { String($0) }
        }
    }

    func nextInt() -> Int {
        defer { idx += 1 }
        return Int(data[idx])!
    }

    func next() -> String {
        defer { idx += 1 }
        return data[idx]
    }
}

// Main
let input = FastInput()
let t = input.nextInt()

var output = String()

for _ in 0..<t {
    let n = input.nextInt()
    let s = Array(input.next())

    var arr = [Int](repeating: 0, count: n)
    for i in 0..<n {
        arr[i] = Int(s[i].asciiValue! - 96)
    }

    let st = SegTree(arr)

    let q = input.nextInt()

    for _ in 0..<q {
        let l = input.nextInt()
        let r = input.nextInt()
        output += "\(st.query(l, r).cnt)\n"
    }
}

print(output)
