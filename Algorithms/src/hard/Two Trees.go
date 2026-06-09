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
		c2, err := fs.r.ReadByte()
		if err != nil {
			break
		}
		c = c2
	}

	return sign * val
}

type Pair struct {
	to int
	w  int
}

func main() {
	fs := NewFastScanner()

	n := fs.NextInt()

	g := make([][]Pair, n)

	for i := 1; i < n; i++ {
		u := fs.NextInt() - 1
		v := fs.NextInt() - 1
		wEdge := fs.NextInt()

		g[u] = append(g[u], Pair{v, wEdge})
		g[v] = append(g[v], Pair{u, wEdge})
	}

	visit := make([]bool, n)

	dp := make([][]int, n)
	p := make([][]int, n)

	for i := 0; i < n; i++ {
		dp[i] = make([]int, 20)
		p[i] = make([]int, 20)
	}

	w := make([]int, n)
	a := make([]int, n)
	h := make([]int, n)
	s := make([]int64, n)

	for i := 0; i < n; i++ {
		w[i] = fs.NextInt()
	}

	h[0] = 1

	queue := make([]int, 0)
	queue = append(queue, 0)

	head := 0

	for head < len(queue) {
		u := queue[head]
		head++

		visit[u] = true

		for i := 1; (h[u]-(1<<i)) > 0; i++ {
			m := p[u][i-1]

			p[u][i] = p[m][i-1]

			x := dp[m][i-1]
			y := dp[u][i-1]

			if int64(w[y])+s[y] < int64(w[x])+s[x] {
				dp[u][i] = y
			} else {
				dp[u][i] = x
			}
		}

		for _, edge := range g[u] {
			v := edge.to

			if visit[v] {
				continue
			}

			p[v][0] = u
			h[v] = h[u] + 1
			s[v] = s[u] + int64(edge.w)

			x := a[u]

			if s[v]-s[x]+int64(w[x]) < int64(w[v]) {
				a[v] = x
			} else {
				a[v] = v
			}

			if int64(w[v])+int64(edge.w) < int64(w[u]) {
				dp[v][0] = v
			} else {
				dp[v][0] = u
			}

			queue = append(queue, v)
		}
	}

	Q := fs.NextInt()

	writer := bufio.NewWriterSize(os.Stdout, 1<<20)
	defer writer.Flush()

	for Q > 0 {
		Q--

		u := fs.NextInt() - 1
		v := fs.NextInt() - 1

		ans := (s[u] - s[v]) << 1

		x := a[u]
		y := u
		r := u

		for i := 19; i >= 0; i-- {
			if h[r]-(1<<i) >= h[v] {
				z := dp[r][i]

				if int64(w[z])+s[z] <= int64(w[y])+s[y] {
					y = z
				}

				r = p[r][i]
			}
		}

		if h[y] < h[x] {
			t := int64(w[x]) + int64(w[y]) - (s[x] - s[y])

			if t < 0 {
				ans += t
			}
		}

		fmt.Fprintln(writer, ans)
	}
}
