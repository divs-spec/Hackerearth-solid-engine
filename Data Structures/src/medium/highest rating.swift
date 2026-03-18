import Foundation

let input1 = readLine()!.split(separator: " ").map { Int($0)! }
let m = input1[0]
let q = input1[1]
let n = input1[2]

let a = readLine()!.split(separator: " ").map { Int($0)! }

var mx = 0

for i in 0..<n {
    mx = max(mx, a[i])
}

// Initialize count array
var cnt = Array(repeating: 0, count: mx + m * q + 1)
var ans = 0

for i in 0..<n {
    cnt[a[i]] += 1
    
    for j in 1...q {
        let plusIndex = a[i] + j * m
        cnt[plusIndex] += 1
        ans = max(ans, cnt[plusIndex])
        
        let minusIndex = a[i] - j * m
        if minusIndex >= 0 {
            cnt[minusIndex] += 1
            ans = max(ans, cnt[minusIndex])
        }
    }
}

print(ans)
