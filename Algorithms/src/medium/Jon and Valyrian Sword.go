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
	return &FastScanner{
		r: bufio.NewReaderSize(os.Stdin, 1<<20),
	}
}

func (fs *FastScanner) NextInt() int {
	sign, val := 1, 0
	c, _ := fs.r.ReadByte()
	for (c < '0' || c > '9') && c != '-' {
		c, _ = fs.r.ReadByte()
	}
	if c == '-' {
		sign = -1
		c, _ = fs.r.ReadByte()
	}
	for c >= '0' && c <= '9' {
		val = val*10 + int(c-'0')
		c, _ = fs.r.ReadByte()
	}
	return val * sign
}

type Edge struct {
	to   int
	idx  int
}

func main() {
	in := NewFastScanner()
	out := bufio.NewWriter(os.Stdout)
	defer out.Flush()

	n := in.NextInt()
	x := in.NextInt()
	totalSpecial := 2 * x

	isSpecial := make([]int, n+1)
	for i := 0; i < totalSpecial; i++ {
		v := in.NextInt()
		isSpecial[v] = 1
	}

	adj := make([][]Edge, n+1)
	for i := 1; i < n; i++ {
		u := in.NextInt()
		v := in.NextInt()
		adj[u] = append(adj[u], Edge{v, i})
		adj[v] = append(adj[v], Edge{u, i})
	}

	visited := make([]bool, n+1)
	var bestValue int64 = -1
	bestIndex := 0

	var dfs func(int) int64
	dfs = func(u int) int64 {
		visited[u] = true
		count := int64(isSpecial[u])

		for _, e := range adj[u] {
			v := e.to
			if !visited[v] {
				sub := dfs(v)
				cross := sub * int64(totalSpecial-int(sub))

				if cross > bestValue || (cross == bestValue && e.idx > bestIndex) {
					bestValue = cross
					bestIndex = e.idx
				}
				count += sub
			}
		}
		return count
	}

	dfs(1)
	fmt.Fprintln(out, bestIndex)
}
