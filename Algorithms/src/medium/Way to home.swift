import Foundation

let MOD: Int64 = 1000000007
let N = 1004

var fact = Array(repeating: Int64(0), count: N)
var invfact = Array(repeating: Int64(0), count: N)

func fastExp(_ num: Int64, _ p: Int64) -> Int64 {
    if p == 0 { return 1 }
    let res = fastExp(num, p / 2)
    if p % 2 == 0 {
        return (res * res) % MOD
    } else {
        return ((res * res) % MOD * num) % MOD
    }
}

if let input = readLine()?.split(separator: " "),
   let n = Int(input[0]),
   let m = Int(input[1]) {

    let h = n - 1
    let r = m - 1

    if h + 1 < r {
        print(0)
    } else {
        fact[0] = 1
        invfact[0] = 1

        for i in 1..<N {
            fact[i] = (fact[i - 1] * Int64(i)) % MOD
            invfact[i] = fastExp(fact[i], MOD - 2)
        }

        let ans = (((fact[h + 1] * invfact[r]) % MOD) * invfact[h + 1 - r]) % MOD

        print(ans)
    }
}
