import Foundation

// MARK: - Fast Scanner
final class FastScanner {
    private var data: [UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
    private var idx: Int = 0

    func readInt() -> Int {
        var num = 0
        var sign = 1
        while data[idx] == 10 || data[idx] == 13 || data[idx] == 32 { idx += 1 }
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

// MARK: - Segment Tree Item
struct SegTreeItem {
    var element: Int64
}

// MARK: - Segment Tree
final class SegTree {
    private var nodes: [SegTreeItem]
    private let nullItem = SegTreeItem(element: 0)
    private let size: Int

    init(_ n: Int) {
        self.size = n
        self.nodes = Array(repeating: nullItem, count: 4 * n + 5)
    }

    private func merge(_ a: SegTreeItem, _ b: SegTreeItem) -> SegTreeItem {
        return SegTreeItem(element: a.element + b.element)
    }

    private func pointUpdate(_ x: Int, _ val: SegTreeItem, _ index: Int, _ l: Int, _ r: Int) {
        if x < l || x >= r { return }
        if l + 1 == r {
            nodes[index] = val
            return
        }
        let mid = (l + r) >> 1
        pointUpdate(x, val, index << 1, l, mid)
        pointUpdate(x, val, index << 1 | 1, mid, r)
        nodes[index] = merge(nodes[index << 1], nodes[index << 1 | 1])
    }

    private func query(_ x: Int, _ y: Int, _ index: Int, _ l: Int, _ r: Int) -> SegTreeItem {
        if y <= l || x >= r { return nullItem }
        if x <= l && r <= y { return nodes[index] }
        let mid = (l + r) >> 1
        return merge(
            query(x, y, index << 1, l, mid),
            query(x, y, index << 1 | 1, mid, r)
        )
    }

    func pointUpdate(_ x: Int, _ val: Int) {
        pointUpdate(x, SegTreeItem(element: Int64(val)), 1, 0, size)
    }

    func query(_ x: Int, _ y: Int) -> Int64 {
        return query(x, y, 1, 0, size).element
    }
}

// MARK: - Core Logic
func curiousQueries(_ n: Int, _ a: [Int], _ q: Int, _ queries: [[Int]]) -> [Int64] {

    var mx = 0
    for v in a { mx = max(mx, v) }

    var groupsA = Array(repeating: [Int](), count: mx + 1)
    for i in 0..<n {
        groupsA[a[i]].append(i)
    }

    var groupsQ = Array(repeating: [Int](), count: mx + 1)
    for i in 0..<q {
        let r = queries[i][1]
        groupsQ[a[r]].append(i)
    }

    let st = SegTree(n)
    var result = Array(repeating: Int64(0), count: q)

    for value in stride(from: mx, through: 1, by: -1) {

        for id in groupsQ[value] {
            let l = queries[id][0]
            result[id] = st.query(0, l + 1)
        }

        for idx in groupsA[value] {
            st.pointUpdate(idx, a[idx])
        }
    }

    return result
}

// MARK: - Main
let scanner = FastScanner()
let t = scanner.readInt()

for _ in 0..<t {
    let n = scanner.readInt()
    let q = scanner.readInt()

    var a = [Int]()
    for _ in 0..<n {
        a.append(scanner.readInt())
    }

    var queries = Array(repeating: [0, 0], count: q)
    for i in 0..<q {
        queries[i][0] = scanner.readInt()
        queries[i][1] = scanner.readInt()
    }

    let ans = curiousQueries(n, a, q, queries)
    print(ans.map(String.init).joined(separator: " "))
}
