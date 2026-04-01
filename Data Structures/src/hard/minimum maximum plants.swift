import Foundation

func solve() {
    guard let first = readLine()?.split(separator: " "),
          first.count >= 2,
          let n = Int(first[0]),
          let m = Int(first[1]) else {
        return
    }

    var v = Array(repeating: Array(repeating: 0, count: m), count: n)

    guard let qLine = readLine(),
          let q = Int(qLine) else {
        return
    }

    for _ in 0..<q {
        guard let line = readLine() else { break }
        let parts = line.split(separator: " ")
        if parts.count < 2 { continue }

        if let i = Int(parts[0]), let j = Int(parts[1]) {
            if i >= 0 && i < n && j >= 0 && j < m {
                v[i][j] = 1
            }
        }
    }

    var c1 = 0
    var c2 = 0

    for i in 0..<n {
        var length = 0

        for j in 0..<m {
            if v[i][j] == 0 {
                length += 1
            } else {
                c1 += (length + 1) / 2
                c2 += (length + 2) / 3
                length = 0
            }
        }

        // remaining segment
        c1 += (length + 1) / 2
        c2 += (length + 2) / 3
    }

    print("\(c1) \(c2)")
}

solve()
