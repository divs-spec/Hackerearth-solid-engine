import Foundation

let MAXN = 1000010

class Node {
    var cnt: Int
    var left: Node!
    var right: Node!

    init(_ cnt: Int, _ left: Node?, _ right: Node?) {
        self.cnt = cnt
        self.left = left
        self.right = right
    }

    func insert(_ s: Int, _ e: Int, _ v: Int) -> Node {
        if v < s || v > e {
            return self
        }

        if s == e {
            return Node(self.cnt + 1, nullNode, nullNode)
        }

        let mid = (s + e) >> 1

        return Node(
            self.cnt + 1,
            self.left.insert(s, mid, v),
            self.right.insert(mid + 1, e, v)
        )
    }
}

var nullNode = Node(0, nil, nil)
var root = Array(repeating: Node(0, nil, nil), count: MAXN)
var arr = Array(repeating: 0, count: MAXN)

var k = 0

func query(_ a: Node, _ b: Node, _ s: Int, _ e: Int) -> Int {
    let chk1 = b.cnt - a.cnt
    let chk2 = e - s + 1

    if chk1 == chk2 || e <= k {
        return -1
    }

    if s == e {
        return s
    }

    let mid = (s + e) >> 1

    var ret = query(a.left, b.left, s, mid)
    if ret == -1 {
        ret = query(a.right, b.right, mid + 1, e)
    }

    return ret
}

// Input
let first = readLine()!.split(separator: " ").map { Int($0)! }
let n = first[0]
k = first[1]

let values = readLine()!.split(separator: " ").map { Int($0)! }

for i in 0..<n {
    arr[i] = values[i]
}

nullNode.left = nullNode
nullNode.right = nullNode

root[0] = nullNode.insert(1, 1000001, arr[0])

for i in 1..<n {
    root[i] = root[i - 1].insert(1, 1000001, arr[i])
}

let q = Int(readLine()!)!

for _ in 0..<q {
    let lr = readLine()!.split(separator: " ").map { Int($0)! }
    var l = lr[0] - 1
    var r = lr[1] - 1

    let res = query(
        l == 0 ? nullNode : root[l - 1],
        root[r],
        1,
        1000001
    )

    print(res)
}
