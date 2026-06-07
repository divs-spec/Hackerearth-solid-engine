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
		c2, err := fs.r.ReadByte()
		if err != nil {
			break
		}
		c = c2
	}

	fs.r.UnreadByte()
	return sign * val
}

func main() {
	fs := NewFastScanner()

	N := fs.NextInt()
	Q := fs.NextInt()

	color := make([]int, N+1)
	for i := 1; i <= N; i++ {
		color[i] = fs.NextInt()
	}

	g := make([][]int, N+1)
	for i := 0; i < N-1; i++ {
		u := fs.NextInt()
		v := fs.NextInt()
		g[u] = append(g[u], v)
		g[v] = append(g[v], u)
	}

	freq := make([]int, N+1)
	for i := 0; i < Q; i++ {
		x := fs.NextInt()
		freq[x]++
	}

	parent := make([]int, N+1)
	order := make([]int, 0, N)

	stack := []int{1}
	parent[1] = -1

	for len(stack) > 0 {
		u := stack[len(stack)-1]
		stack = stack[:len(stack)-1]

		order = append(order, u)

		for _, v := range g[u] {
			if v != parent[u] {
				parent[v] = u
				stack = append(stack, v)
			}
		}
	}

	cnt := make([]int, N+1)
	copy(cnt, freq)

	for i := len(order) - 1; i > 0; i-- {
		u := order[i]
		p := parent[u]
		cnt[p] += cnt[u]
	}

	ans := 0
	for v := 1; v <= N; v++ {
		finalColor := color[v] ^ (cnt[v] & 1)
		if finalColor == 1 {
			ans++
		}
	}

	fmt.Println(ans)
}
