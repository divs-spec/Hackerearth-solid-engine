import Foundation

let N = 10_000_007
var arr = [Int32](repeating: 0, count: N)

var k = 0

@inline(__always)
func reachable(_ a0: Int, _ b0: Int) -> Bool {
    var a = a0
    var b = b0
    var cnt = 0

    for _ in 0..<8 {
        if b == 0 && a != 0 {
            return false
        }

        if (a % 10) != (b % 10) {
            cnt += 1
        }

        a /= 10
        b /= 10
    }

    return cnt <= k
}

// Fast Input
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
}

let input = FastInput()

let n = input.nextInt()
k = input.nextInt()
let q = input.nextInt()

// Precompute
for i in 1..<N {
    arr[i] = reachable(i, n) ? 1 : 0
}

// Prefix sum
for i in 1..<N {
    arr[i] += arr[i - 1]
}

// Queries
var output = String()

for _ in 0..<q {
    let l = input.nextInt()
    let r = input.nextInt()
    output += "\(arr[r] - arr[l - 1])\n"
}

print(output)
