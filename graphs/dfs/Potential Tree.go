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
	return &FastScanner{r: bufio.NewReaderSize(os.Stdin, 1<<20)}
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
		nc, err := fs.r.ReadByte()
		if err != nil {
			return sign * val
		}
		c = nc
	}

	_ = fs.r.UnreadByte()
	return sign * val
}

func main() {
	fs := NewFastScanner()
	out := bufio.NewWriterSize(os.Stdout, 1<<20)
	defer out.Flush()

	T := fs.NextInt()

	for ; T > 0; T-- {
		n := fs.NextInt()

		g := make([][]int, n)
		deg := make([]int, n)

		for i := 0; i < n-1; i++ {
			u := fs.NextInt()
			v := fs.NextInt()

			g[u] = append(g[u], v)
			g[v] = append(g[v], u)

			deg[u]++
			deg[v]++
		}

		parent := make([]int, n)
		for i := range parent {
			parent[i] = -1
		}

		order := make([]int, 0, n)
		stack := []int{0}
		parent[0] = -2

		for len(stack) > 0 {
			u := stack[len(stack)-1]
			stack = stack[:len(stack)-1]

			order = append(order, u)

			for _, v := range g[u] {
				if parent[v] == -1 {
					parent[v] = u
					stack = append(stack, v)
				}
			}
		}

		down := make([]bool, n)

		for i := n - 1; i >= 0; i-- {
			u := order[i]

			hasChild := false
			val := false

			for _, v := range g[u] {
				if parent[v] == u {
					hasChild = true
					if down[v] {
						val = true
					}
				}
			}

			if !hasChild {
				down[u] = (u & 1) == 1
			} else {
				down[u] = val
			}
		}

		up := make([]bool, n)
		ans := make([]int, n)

		for _, u := range order {

			totalTrue := 0

			if parent[u] >= 0 && up[u] {
				totalTrue++
			}

			for _, v := range g[u] {
				if parent[v] == u && down[v] {
					totalTrue++
				}
			}

			ans[u] = totalTrue

			for _, v := range g[u] {
				if parent[v] != u {
					continue
				}

				var msg bool

				if deg[u] == 1 {
					msg = (u & 1) == 1
				} else {
					cnt := totalTrue
					if down[v] {
						cnt--
					}
					msg = cnt > 0
				}

				up[v] = msg
			}
		}

		for i := 0; i < n; i++ {
			if i > 0 {
				fmt.Fprint(out, " ")
			}
			fmt.Fprint(out, ans[i])
		}
		fmt.Fprintln(out)
	}
}
