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

		nc, err := fs.r.ReadByte()
		if err != nil {
			break
		}
		c = nc
	}

	fs.r.UnreadByte()
	return sign * val
}

type Pair struct {
	node int
	dist int
}

var (
	N, K        int
	W           []int
	g           [][]int
	dist        []int
	visitedComp []bool
	queue       []int
)

func bfs(start, limit int, markComponent bool) Pair {

	head, tail := 0, 0
	queue[tail] = start
	tail++

	for i := 1; i <= N; i++ {
		dist[i] = -1
	}

	dist[start] = 0

	if markComponent {
		visitedComp[start] = true
	}

	farNode := start
	farDist := 0

	for head < tail {
		u := queue[head]
		head++

		if dist[u] > farDist {
			farDist = dist[u]
			farNode = u
		}

		for _, v := range g[u] {

			if W[v] > limit || dist[v] != -1 {
				continue
			}

			dist[v] = dist[u] + 1
			queue[tail] = v
			tail++

			if markComponent {
				visitedComp[v] = true
			}
		}
	}

	return Pair{farNode, farDist}
}

func check(limit int) bool {

	for i := 1; i <= N; i++ {
		visitedComp[i] = false
	}

	for i := 1; i <= N; i++ {

		if W[i] > limit || visitedComp[i] {
			continue
		}

		p1 := bfs(i, limit, true)
		p2 := bfs(p1.node, limit, false)

		if p2.dist >= K {
			return true
		}
	}

	return false
}

func main() {

	fs := NewFastScanner()

	N = fs.NextInt()
	K = fs.NextInt()

	W = make([]int, N+1)

	maxW := 0

	for i := 1; i <= N; i++ {
		W[i] = fs.NextInt()
		if W[i] > maxW {
			maxW = W[i]
		}
	}

	g = make([][]int, N+1)

	for i := 0; i < N-1; i++ {
		u := fs.NextInt()
		v := fs.NextInt()

		g[u] = append(g[u], v)
		g[v] = append(g[v], u)
	}

	dist = make([]int, N+1)
	visitedComp = make([]bool, N+1)
	queue = make([]int, N+5)

	low, high := 1, maxW
	ans := -1

	for low <= high {

		mid := low + (high-low)/2

		if check(mid) {
			ans = mid
			high = mid - 1
		} else {
			low = mid + 1
		}
	}

	fmt.Println(ans)
}
