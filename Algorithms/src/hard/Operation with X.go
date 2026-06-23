package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

func main() {
	in := bufio.NewReaderSize(os.Stdin, 1<<20)

	var n int
	var x int64
	fmt.Fscan(in, &n, &x)

	a := make([]int64, n)
	for i := 0; i < n; i++ {
		fmt.Fscan(in, &a[i])
	}

	if n <= 1 {
		fmt.Println(0)
		return
	}

	sort.Slice(a, func(i, j int) bool {
		return a[i] < a[j]
	})

	ans := a[n-1] - a[0]

	for i := 0; i < n-1; i++ {
		high := max(a[i]+x, a[n-1]-x)
		low := min(a[0]+x, a[i+1]-x)

		if high-low < ans {
			ans = high - low
		}
	}

	fmt.Println(ans)
}

func max(a, b int64) int64 {
	if a > b {
		return a
	}
	return b
}

func min(a, b int64) int64 {
	if a < b {
		return a
	}
	return b
}
