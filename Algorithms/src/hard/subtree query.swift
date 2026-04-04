import Foundation

let N = 100005

var v = Array(repeating: [Int](), count: N)
var a = Array(repeating: 0, count: N)
var pos = Array(repeating: 0, count: N)
var sub = Array(repeating: 0, count: N)
var order = [Int]()

class SegTree {
    var n: Int
    var tree: [Int]
    var lazy: [[Int]]

    init(_ arr: [Int]) {
        self.n = arr.count
        self.tree = Array(repeating: 0, count: 4 * n)
        self.lazy = Array(repeating: [0, 0, 0], count: 4 * n)
        build(arr, 1, 0, n - 1)
    }

    func build(_ arr: [Int], _ node: Int, _ start: Int, _ end: Int) {
        if start == end {
            tree[node] = arr[start]
        } else {
            let mid = (start + end) / 2
            build(arr, node * 2, start, mid)
            build(arr, node * 2 + 1, mid + 1, end)
            tree[node] = tree[node * 2] + tree[node * 2 + 1]
        }
    }

    func push(_ node: Int, _ start: Int, _ end: Int) {
        if lazy[node][2] == 1 {
            tree[node] = (end - start + 1) * lazy[node][1]
        }
        if lazy[node][0] == 1 {
            tree[node] = (end - start + 1) - tree[node]
        }

        if start != end {
            if lazy[node][2] == 1 {
                lazy[node * 2] = lazy[node]
                lazy[node * 2 + 1] = lazy[node]
            } else {
                lazy[node * 2][0] ^= lazy[node][0]
                lazy[node * 2 + 1][0] ^= lazy[node][0]
            }
        }

        lazy[node][0] = 0
        lazy[node][2] = 0
    }

    func flipRange(_ node: Int, _ start: Int, _ end: Int, _ l: Int, _ r: Int) {
        if start > r || end < l { return }

        push(node, start, end)

        if start >= l && end <= r {
            lazy[node][0] = 1
            return
        }

        let mid = (start + end) / 2
        flipRange(node * 2, start, mid, l, r)
        flipRange(node * 2 + 1, mid + 1, end, l, r)

        push(node * 2, start, mid)
        push(node * 2 + 1, mid + 1, end)

        tree[node] = tree[node * 2] + tree[node * 2 + 1]
    }

    func getSum(_ node: Int, _ start: Int, _ end: Int, _ l: Int, _ r: Int) -> Int {
        if start > r || end < l { return 0 }

        push(node, start, end)

        if start >= l && end <= r {
            return tree[node]
        }

        let mid = (start + end) / 2
        return getSum(node * 2, start, mid, l, r) +
               getSum(node * 2 + 1, mid + 1, end, l, r)
    }

    func flipRange(_ l: Int, _ r: Int) {
        flipRange(1, 0, n - 1, l, r)
    }

    func getSum(_ l: Int, _ r: Int) -> Int {
        return getSum(1, 0, n - 1, l, r)
    }
}

func dfs(_ u: Int, _ p: Int) {
    sub[u] = 1
    order.append(u)

    for i in v[u] {
        if i != p {
            dfs(i, u)
            sub[u] += sub[i]
        }
    }
}

// -------- MAIN --------

let n = Int(readLine()!)!

for _ in 0..<(n - 1) {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    let x = input[0], y = input[1]
    v[x].append(y)
    v[y].append(x)
}

let arrInput = readLine()!.split(separator: " ").map { Int($0)! }
for i in 1...n {
    a[i] = arrInput[i - 1]
}

dfs(1, 0)

for i in 0..<n {
    pos[order[i]] = i
}

var sg = [SegTree]()

for i in 0..<20 {
    var arr = Array(repeating: 0, count: n)
    for j in 0..<n {
        if (a[order[j]] & (1 << i)) != 0 {
            arr[j] = 1
        }
    }
    sg.append(SegTree(arr))
}

let q = Int(readLine()!)!

for _ in 0..<q {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    let t = input[0]

    if t == 1 {
        let node = input[1]
        let l = pos[node]
        let r = l + sub[node] - 1

        var c: Int64 = 0

        for i in 0..<20 {
            let sum = sg[i].getSum(l, r)
            c += Int64(sum) * (1 << i)
        }

        print(c)

    } else {
        let node = input[1]
        let x = input[2]

        let l = pos[node]
        let r = l + sub[node] - 1

        for i in 0..<20 {
            if (x & (1 << i)) != 0 {
                sg[i].flipRange(l, r)
            }
        }
    }
}
