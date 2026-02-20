/*
// Sample code to perform I/O:

let name = readLine()                           // Reading input from STDIN
print("Hi, ", name!, ".\n", separator: "")      // Writing output to STDOUT

// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail
*/

// Write your code here
import Foundation

// ---------- Fast Input ----------
final class FastScanner {
    private var data: [UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
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

let scanner = FastScanner()

// ---------- Input ----------
let n = scanner.readInt()
let x = scanner.readInt()
let totalSpecial = 2 * x

var isSpecial = Array(repeating: 0, count: n + 1)
for _ in 0..<totalSpecial {
    let v = scanner.readInt()
    isSpecial[v] = 1
}

// adjacency list: (neighbor, edge index)
var adj = Array(repeating: [(Int, Int)](), count: n + 1)

for i in 1..<n {
    let u = scanner.readInt()
    let v = scanner.readInt()
    adj[u].append((v, i))
    adj[v].append((u, i))
}

// ---------- DFS ----------
var visited = Array(repeating: false, count: n + 1)
var bestValue: Int64 = -1
var bestIndex: Int = 0

func dfs(_ u: Int) -> Int64 {
    visited[u] = true
    var count: Int64 = Int64(isSpecial[u])
    
    for (v, idx) in adj[u] {
        if !visited[v] {
            let sub = dfs(v)
            let crossings = sub * Int64(totalSpecial - Int(sub))
            
            if crossings > bestValue || (crossings == bestValue && idx > bestIndex) {
                bestValue = crossings
                bestIndex = idx
            }
            count += sub
        }
    }
    return count
}

// Tree is connected, root anywhere (1 is safe)
_ = dfs(1)

// ---------- Output ----------
print(bestIndex)
