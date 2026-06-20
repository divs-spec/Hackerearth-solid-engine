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

type FastScanner struct {
	r *bufio.Reader
}

func NewFastScanner() *FastScanner {
	return &FastScanner{bufio.NewReaderSize(os.Stdin, 1<<20)}
}

func (fs *FastScanner) NextInt64() int64 {
	var sign int64 = 1
	var val int64

	c, _ := fs.r.ReadByte()
	for (c < '0' || c > '9') && c != '-' {
		c, _ = fs.r.ReadByte()
	}

	if c == '-' {
		sign = -1
		c, _ = fs.r.ReadByte()
	}

	for c >= '0' && c <= '9' {
		val = val*10 + int64(c-'0')
		nc, err := fs.r.ReadByte()
		if err != nil {
			break
		}
		c = nc
	}

	if c < '0' || c > '9' {
		_ = fs.r.UnreadByte()
	}

	return sign * val
}

func main() {
	fs := NewFastScanner()
	out := bufio.NewWriterSize(os.Stdout, 1<<20)
	defer out.Flush()

	T := int(fs.NextInt64())

	for ; T > 0; T-- {
		N := int(fs.NextInt64())
		K := fs.NextInt64()

		A := make([]int64, N)
		for i := 0; i < N; i++ {
			A[i] = fs.NextInt64()
		}

		if N == 1 {
			fmt.Fprintln(out, 0)
			continue
		}

		base := int64(0)

		w := make([]int64, N)
		start := make([]int64, N)
		end := make([]int64, N)

		for j := 1; j < N; j++ {
			cur := A[j] % A[j-1]
			base += cur
			w[j] = (A[j]+K)%(A[j-1]+K) - cur
		}

		for l := 1; l < N; l++ {
			cur := A[l] % A[l-1]
			start[l] = (A[l]+K)%A[l-1] - cur
		}

		for r := 0; r < N-1; r++ {
			cur := A[r+1] % A[r]
			end[r] = A[r+1]%(A[r]+K) - cur
		}

		pref := make([]int64, N)
		for i := 1; i < N; i++ {
			pref[i] = pref[i-1] + w[i]
		}

		best := int64(0)
		bestLeft := start[0] - pref[0]

		for r := 0; r < N; r++ {
			if start[r]-pref[r] > bestLeft {
				bestLeft = start[r] - pref[r]
			}

			gain := end[r] + pref[r] + bestLeft
			if gain > best {
				best = gain
			}
		}

		fmt.Fprintln(out, base+best)
	}
}
