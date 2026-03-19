/*
// Sample code to perform I/O:

fmt.Scanf("%s", &myname)            // Reading input from STDIN
fmt.Println("Hello", myname)        // Writing output to STDOUT

// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail
*/

// Write your code here
package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {

	in := bufio.NewReader(os.Stdin)
	out := bufio.NewWriter(os.Stdout)
	defer out.Flush()

	var N, K int
	fmt.Fscan(in, &N, &K)

	pos := map[int]int{}

	for i := 1; i <= N; i++ {
		var x int
		fmt.Fscan(in, &x)
		pos[x] = i
	}

	var Q int
	fmt.Fscan(in, &Q)

	for ; Q > 0; Q-- {

		var L, R int
		fmt.Fscan(in, &L, &R)

		candidate := K + 1

		for {
			p, ok := pos[candidate]
			if !ok || p < L || p > R {
				break
			}
			candidate++
		}

		fmt.Fprintln(out, candidate)
	}
}
