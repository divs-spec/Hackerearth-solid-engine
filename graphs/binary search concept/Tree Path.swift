import Foundation

final class FastScanner {
    private var data:[UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
    private var idx: Int = 0

    func nextInt() -> Int {
        while data[idx] == 10 || data[idx] == 13 || data[idx] == 32 || data[idx] == 9 {
            idx += 1
        }

        var sign = 1
        if data[idx] == 45 {
            sign = -1
            idx += 1
        }

        var num = 0
        while data[idx] >= 48 && data[idx] <= 57 {
            num = num * 10 + Int(data[idx] - 48)
            idx += 1
        }

        return num * sign
    }
}

struct Pair {
    var node: Int
    var dist: Int
}

let fs = FastScanner()

let N = fs.nextInt()
let K = fs.nextInt()

var W = Array(repeating: 0, count: N + 1)
var maxW = 0

for i in 1...N {
    W[i] = fs.nextInt()
    maxW = max(maxW, W[i])
}

var g = Array(repeating: [Int](), count: N + 1)

if N > 1 {
    for _ in 0..<(N - 1) {
        let u = fs.nextInt()
        let v = fs.nextInt()

        g[u].append(v)
        g[v].append(u)
    }
}

var dist = Array(repeating: -1, count: N + 1)
var visitedComp = Array(repeating: false, count: N + 1)
var queue = Array(repeating: 0, count: N + 5)

func bfs(_ start: Int, _ limit: Int, _ markComponent: Bool) -> Pair {

    for i in 1...N {
        dist[i] = -1
    }

    var head = 0
    var tail = 0

    queue[tail] = start
    tail += 1

    dist[start] = 0

    if markComponent {
        visitedComp[start] = true
    }

    var farNode = start
    var farDist = 0

    while head < tail {

        let u = queue[head]
        head += 1

        if dist[u] > farDist {
            farDist = dist[u]
            farNode = u
        }

        for v in g[u] {

            if W[v] > limit || dist[v] != -1 {
                continue
            }

            dist[v] = dist[u] + 1

            queue[tail] = v
            tail += 1

            if markComponent {
                visitedComp[v] = true
            }
        }
    }

    return Pair(node: farNode, dist: farDist)
}

func check(_ limit: Int) -> Bool {

    for i in 1...N {
        visitedComp[i] = false
    }

    for i in 1...N {

        if W[i] > limit || visitedComp[i] {
            continue
        }

        let p1 = bfs(i, limit, true)
        let p2 = bfs(p1.node, limit, false)

        if p2.dist >= K {
            return true
        }
    }

    return false
}

var low = 1
var high = maxW
var ans = -1

while low <= high {

    let mid = low + (high - low) / 2

    if check(mid) {
        ans = mid
        high = mid - 1
    } else {
        low = mid + 1
    }
}

print(ans)
