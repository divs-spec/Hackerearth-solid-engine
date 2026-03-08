/*
// Sample code to perform I/O:

fmt.Scanf("%s", &myname)            // Reading input from STDIN
fmt.Println("Hello", myname)        // Writing output to STDOUT

// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail
*/

// Write your code here
package main

import (
	"fmt"
)

const MOD int64 = 1000000007

func mulmat(a *[2][2]int64, b *[2][2]int64) {
	var c [2][2]int64

	for i := 0; i < 2; i++ {
		for j := 0; j < 2; j++ {
			c[i][j] = 0
			for k := 0; k < 2; k++ {
				c[i][j] += (a[i][k] * b[k][j]) % MOD
				if c[i][j] >= MOD {
					c[i][j] -= MOD
				}
				if c[i][j] < 0 {
					c[i][j] += MOD
				}
			}
		}
	}

	*a = c
}

func fib(n int64) int64 {
	res := [2][2]int64{{1, 0}, {0, 1}}
	X := [2][2]int64{{1, 1}, {1, 0}}

	for n > 0 {
		if n&1 == 1 {
			mulmat(&res, &X)
		}

		n >>= 1
		mulmat(&X, &X)
	}

	return res[0][1]
}

func sum(n int64) int64 {
	if n <= 0 {
		return 0
	}

	var ret int64
	if n&1 == 1 {
		ret = (fib(n-1) + 1 + MOD) % MOD
	} else {
		ret = (-fib(n-1) + 1 + MOD) % MOD
	}

	return ret
}

func main() {
	var t int
	fmt.Scan(&t)

	for t > 0 {
		var l, r int64
		fmt.Scan(&l, &r)

		ans := (sum(r) - sum(l-1) + MOD) % MOD
		fmt.Println(ans)

		t--
	}
}
