import Foundation

let t = Int(readLine()!)!

for _ in 0..<t {

    let n = Int(readLine()!)!
    var A = readLine()!.split(separator: " ").map { Int($0)! }

    var c = 0

    for i in 0..<n {
        var x = A[i]
        while x % 2 == 0 {
            c += 1
            x /= 2
        }
    }

    if c % 2 == 0 {
        print("Alan")
    } else {
        print("Charlie")
    }
}
