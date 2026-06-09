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
	"container/heap"
	"fmt"
	"os"
)

const INF int64 = 1 << 60

type Edge struct {
	to int
	w  int
}

type Item struct {
	node int
	dist int64
}

type PriorityQueue []Item

func (pq PriorityQueue) Len() int { return len(pq) }
func (pq PriorityQueue) Less(i, j int) bool {
	return pq[i].dist < pq[j].dist
}
func (pq PriorityQueue) Swap(i, j int) {
	pq[i], pq[j] = pq[j], pq[i]
}
func (pq *PriorityQueue) Push(x interface{}) {
	*pq = append(*pq, x.(Item))
}
func (pq *PriorityQueue) Pop() interface{} {
	old := *pq
	n := len(old)
	item := old[n-1]
	*pq = old[:n-1]
	return item
}

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

	n := fs.NextInt()
	m := fs.NextInt()

	g := make([][]Edge, n+1)

	for i := 0; i < m; i++ {
		u := fs.NextInt()
		v := fs.NextInt()

		w := u
		if v > w {
			w = v
		}

		g[u] = append(g[u], Edge{v, w})
		g[v] = append(g[v], Edge{u, w})
	}

	// Sieve of Eratosthenes
	prime := make([]bool, n+1)
	if n >= 2 {
		for i := 2; i <= n; i++ {
			prime[i] = true
		}
		for i := 2; i*i <= n; i++ {
			if prime[i] {
				for j := i * i; j <= n; j += i {
					prime[j] = false
				}
			}
		}
	}

	dist := make([]int64, n+1)
	for i := 1; i <= n; i++ {
		dist[i] = INF
	}

	pq := &PriorityQueue{}
	heap.Init(pq)

	// Multi-source Dijkstra from all prime-numbered villages
	for i := 2; i <= n; i++ {
		if prime[i] {
			dist[i] = 0
			heap.Push(pq, Item{i, 0})
		}
	}

	for pq.Len() > 0 {
		cur := heap.Pop(pq).(Item)

		u := cur.node
		d := cur.dist

		if d != dist[u] {
			continue
		}

		for _, e := range g[u] {
			nd := d + int64(e.w)

			if nd < dist[e.to] {
				dist[e.to] = nd
				heap.Push(pq, Item{e.to, nd})
			}
		}
	}

	for i := 1; i <= n; i++ {
		if i > 1 {
			fmt.Fprint(out, " ")
		}

		if dist[i] == INF {
			fmt.Fprint(out, -1)
		} else {
			fmt.Fprint(out, dist[i])
		}
	}
	fmt.Fprintln(out)
}
