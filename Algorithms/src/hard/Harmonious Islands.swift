import Foundation

let INF: Int64 = Int64(1e18)

var adj: [[(Int, Int64)]] = []
var d: [Int64] = []
var p: [Int] = []

func dijkstra(_ s: Int) {
    d = Array(repeating: INF, count: d.count)
    p = Array(repeating: -1, count: p.count)

    d[s] = 0
    var pq = [(Int64, Int)]()
    pq.append((0, s))

    while !pq.isEmpty {
        pq.sort { $0.0 < $1.0 }
        let (dist, v) = pq.removeFirst()

        if dist != d[v] { continue }

        for (to, len) in adj[v] {
            if d[v] + len < d[to] {
                d[to] = d[v] + len
                p[to] = v
                pq.append((d[to], to))
            }
        }
    }
}

func solve() {
    guard let nm = readLine()?.split(separator: " "),
          let n = Int(nm[0]),
          let m = Int(nm[1]) else { return }

    var a = Array(repeating: Int64(0), count: n+1)
    var b = Array(repeating: Int64(0), count: n+1)
    var d1 = Array(repeating: Int64(0), count: n+1)
    var d2 = Array(repeating: Int64(0), count: n+1)

    let aInput = readLine()!.split(separator: " ")
    for i in 1...n { a[i] = Int64(aInput[i-1])! }

    let bInput = readLine()!.split(separator: " ")
    for i in 1...n { b[i] = Int64(bInput[i-1])! }

    var edge1 = [[Int]](repeating: [0,0,0], count: m)
    var edge2 = [[Int]](repeating: [0,0,0], count: m)

    for i in 0..<m {
        let line = readLine()!.split(separator: " ")
        edge1[i] = [Int(line[0])!, Int(line[1])!, Int(line[2])!]
    }

    for i in 0..<m {
        let line = readLine()!.split(separator: " ")
        edge2[i] = [Int(line[0])!, Int(line[1])!, Int(line[2])!]
    }

    let uv = readLine()!.split(separator: " ")
    let u = Int(uv[0])!
    let v = Int(uv[1])!

    adj = Array(repeating: [], count: n+1)
    d = Array(repeating: INF, count: n+1)
    p = Array(repeating: -1, count: n+1)

    // Graph 1
    for i in 0..<m {
        let (x,y,z) = (edge1[i][0], edge1[i][1], edge1[i][2])
        adj[x].append((y, Int64(z)))
        adj[y].append((x, Int64(z)))
    }

    dijkstra(u)
    for i in 1...n { d1[i] = d[i] }

    // Graph 2
    adj = Array(repeating: [], count: n+1)
    for i in 0..<m {
        let (x,y,z) = (edge2[i][0], edge2[i][1], edge2[i][2])
        adj[x].append((y, Int64(z)))
        adj[y].append((x, Int64(z)))
    }

    dijkstra(v)
    for i in 1...n { d2[i] = d[i] }

    var ans: Int64 = Int64(4e18)

    for i in 1...n {
        var j = i
        while j <= n {
            if d1[i] < INF && d2[j] < INF {
                let val = d1[i] + d2[j] + a[i] * b[j]
                ans = min(ans, val)
            }
            j += i
        }
    }

    if ans == Int64(4e18) { ans = -1 }
    print(ans)
}

// Main
if let tLine = readLine(), let T = Int(tLine) {
    for _ in 0..<T {
        solve()
    }
}
