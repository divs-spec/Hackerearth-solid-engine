import Foundation

let MAXN = 1000001

var a = Array(repeating: [Int](), count: MAXN)
var c = Array(repeating: [Int](), count: MAXN)
var v = Array(repeating: 0, count: MAXN)
var b = Array(repeating: 0, count: MAXN)
var d = Array(repeating: 0, count: MAXN)

func dfs(_ s: Int, _ k: Int) {
    v[s] = 1

    if c[b[s]].count >= k {
        let size = c[b[s]].count
        d[s] = c[b[s]][size - k]
    } else {
        d[s] = -1
    }

    c[b[s]].append(s)

    for nei in a[s] {
        if v[nei] == 0 {
            dfs(nei, k)
        }
    }

    if !c[b[s]].isEmpty {
        c[b[s]].removeLast()
    }
}

// Input
let input = readLine()!.split(separator: " ").map { Int($0)! }
let n = input[0], k = input[1]

let values = readLine()!.split(separator: " ").map { Int($0)! }
for i in 1...n {
    b[i] = values[i - 1]
}

for _ in 1..<n {
    let edge = readLine()!.split(separator: " ").map { Int($0)! }
    let x = edge[0], y = edge[1]
    a[x].append(y)
    a[y].append(x)
}

dfs(1, k)

for i in 1...n {
    print(d[i], terminator: " ")
}
