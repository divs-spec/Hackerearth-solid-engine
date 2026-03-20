/*
// Sample code to perform I/O:

let name = readLine()                           // Reading input from STDIN
print("Hi, ", name!, ".\n", separator: "")      // Writing output to STDOUT

// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail
*/

// Write your code here
import Foundation

let MOD: Int64 = 1_000_000_007

func countValidPermutations(_ n: Int, _ A: [Int]) -> Int64 {
    // Check non-increasing
    for i in 1..<n {
        if A[i] > A[i - 1] {
            return 0
        }
    }

    // Track used elements
    let used = Set(A)
    let remaining = n - used.count

    // Factorial modulo
    var factorial: Int64 = 1
    if remaining > 0 {
        for i in 1...remaining {
            factorial = (factorial * Int64(i)) % MOD
        }
    }

    return factorial
}

// Main
if let tLine = readLine(), let t = Int(tLine) {
    var outputs: [String] = []

    for _ in 0..<t {
        let n = Int(readLine()!)!
        let A = readLine()!.split(separator: " ").map { Int($0)! }

        let result = countValidPermutations(n, A)
        outputs.append(String(result))
    }

    print(outputs.joined(separator: "\n"))
}
