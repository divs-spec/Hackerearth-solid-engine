import Foundation

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

    func readLong() -> Int64 {
        var num: Int64 = 0
        var sign: Int64 = 1
        while data[idx] == 10 || data[idx] == 13 || data[idx] == 32 { idx += 1 }
        if data[idx] == 45 { sign = -1; idx += 1 }
        while data[idx] >= 48 {
            num = num * 10 + Int64(data[idx] - 48)
            idx += 1
        }
        return num * sign
    }
}

let fs = FastScanner()
let t = fs.readInt()

var output = ""

for _ in 0..<t {
    let k = fs.readLong()
    var n = fs.readLong()

    // Same assertions as C++
    assert(n >= 1 && n <= Int64(1e18))
    assert(k >= 1 && k <= Int64(1e6))

    var ans: Int64 = 0
    while n > 0 {
        ans += 1
        n /= (k + 1)
    }

    output += "\(ans)\n"
}

print(output)
