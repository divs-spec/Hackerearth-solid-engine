import Foundation

final class FastScanner {
    private var data:[UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
    private var idx: Int = 0
    
    func readInt() -> Int {
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
    
    func readLong() -> Int64 {
        while data[idx] == 10 || data[idx] == 13 || data[idx] == 32 || data[idx] == 9 {
            idx += 1
        }
        
        var sign: Int64 = 1
        if data[idx] == 45 {
            sign = -1
            idx += 1
        }
        
        var num: Int64 = 0
        while data[idx] >= 48 && data[idx] <= 57 {
            num = num * 10 + Int64(data[idx] - 48)
            idx += 1
        }
        
        return num * sign
    }
}

func countPairs(_ n: Int, _ x: Int) -> Int64 {
    if x == 0 {
        return Int64(n) * Int64(n)
    }
    
    var res: Int64 = 0
    
    if x + 1 > n {
        return 0
    }
    
    for b in (x + 1)...n {
        let q = n / b
        let rem = n % b
        
        res += Int64(q) * Int64(b - x)
        
        if rem >= x {
            res += Int64(rem - x + 1)
        }
    }
    
    return res
}

let fs = FastScanner()
let t = fs.readInt()

var answer = String()

for _ in 0..<t {
    let n = fs.readInt()
    let r = fs.readLong()
    
    let total = Int64(n) * Int64(n)
    
    if r > total {
        answer += "-1\n"
        continue
    }
    
    var lo = 0
    var hi = n - 1
    var ans = 0
    
    while lo <= hi {
        let mid = (lo + hi) >> 1
        
        if countPairs(n, mid) >= r {
            ans = mid
            lo = mid + 1
        } else {
            hi = mid - 1
        }
    }
    
    answer += "\(ans)\n"
}

print(answer, terminator: "")
