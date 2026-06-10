package main

import (
	"bufio"
	"fmt"
	"os"
)

type Edge struct {
	to int
	w  int
}

func main() {
	in := bufio.NewReaderSize(os.Stdin, 1<<20)

	var n int
	fmt.Fscan(in, &n)

	g := make([][]Edge, n+1)

	for i := 0; i < n-1; i++ {
		var u, v int
		fmt.Fscan(in, &u, &v)

		w := u + v
		g[u] = append(g[u], Edge{v, w})
		g[v] = append(g[v], Edge{u, w})
	}

	xorAll := 0

	vis := make([]bool, n+1)
	type State struct {
		node int
		val  int
	}

	stack := []State{{1, 0}}
	vis[1] = true

	for len(stack) > 0 {
		cur := stack[len(stack)-1]
		stack = stack[:len(stack)-1]

		xorAll ^= cur.val

		for _, e := range g[cur.node] {
			if !vis[e.to] {
				vis[e.to] = true
				stack = append(stack, State{
					node: e.to,
					val:  cur.val ^ e.w,
				})
			}
		}
	}

	if n%2 == 0 {
		fmt.Println(xorAll)
	} else {
		fmt.Println(0)
	}
}
