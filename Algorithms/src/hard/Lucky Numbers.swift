import Foundation

func checker1(_ n: Int64) -> Int64 {
    let s = String(n)
    var ans: Int64 = 0
    var index = -1

    let chars = Array(s)

    for i in 0..<chars.count {
        let digit = Int(chars[i].wholeNumberValue!)
        if ((digit % 2) & 1) == 0 {
            index = i
            break
        }
    }

    if index == -1 { return n }

    for i in 0..<index {
        ans = ans * 10 + Int64(chars[i].wholeNumberValue!)
    }

    ans = ans * 10 + Int64(chars[index].wholeNumberValue! - 1)

    for _ in (index + 1)..<chars.count {
        ans = ans * 10 + 9
    }

    return ans
}

func checker2(_ n: Int64) -> Int64 {
    let s = String(n)
    var ans: Int64 = 0
    var index = -1

    let chars = Array(s)

    for i in 0..<chars.count {
        let digit = Int(chars[i].wholeNumberValue!)
        if (digit & 1) == 0 {
            index = i
            break
        }
    }

    if index == -1 { return n }

    for i in 0..<index {
        ans = ans * 10 + Int64(chars[i].wholeNumberValue!)
    }

    ans = ans * 10 + Int64(chars[index].wholeNumberValue! + 1)

    for _ in (index + 1)..<chars.count {
        ans = ans * 10 + 1
    }

    return ans
}

// Main
if let input = readLine(), let n = Int64(input) {
    let minBest = checker1(n)
    let maxBest = checker2(n)

    if abs(n - minBest) <= abs(n - maxBest) {
        print(minBest)
    } else {
        print(maxBest)
    }
}
