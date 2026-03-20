package main

import (
	"bufio"
	"fmt"
	"os"
)

const MAXN = 1000001

var a [MAXN][]int
var c [MAXN][]int
var v [MAXN]int
var b [MAXN]int
var d [MAXN]int

func dfs(s, k int) {
	v[s] = 1

	if len(c[b[s]]) >= k {
		size := len(c[b[s]])
		d[s] = c[b[s]][size-k]
	} else {
		d[s] = -1
	}

	c[b[s]] = append(c[b[s]], s)

	for _, nei := range a[s] {
		if v[nei] == 0 {
			dfs(nei, k)
		}
	}

	if len(c[b[s]]) > 0 {
		c[b[s]] = c[b[s]][:len(c[b[s]])-1]
	}
}

func main() {
	in := bufio.NewReader(os.Stdin)
	out := bufio.NewWriter(os.Stdout)
	defer out.Flush()

	var n, k int
	fmt.Fscan(in, &n, &k)

	for i := 1; i <= n; i++ {
		fmt.Fscan(in, &b[i])
	}

	for i := 1; i < n; i++ {
		var x, y int
		fmt.Fscan(in, &x, &y)
		a[x] = append(a[x], y)
		a[y] = append(a[y], x)
	}

	dfs(1, k)

	for i := 1; i <= n; i++ {
		fmt.Fprint(out, d[i], " ")
	}
}
