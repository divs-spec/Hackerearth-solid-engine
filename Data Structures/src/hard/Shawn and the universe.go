package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

func existsInRange(arr []int, L, R int) bool {
	i := sort.SearchInts(arr, L)
	return i < len(arr) && arr[i] <= R
}

func main() {
	in := bufio.NewReader(os.Stdin)
	out := bufio.NewWriter(os.Stdout)
	defer out.Flush()

	var N, K int
	fmt.Fscan(in, &N, &K)

	A := make([]int, N+1)
	pos := map[int][]int{}

	for i := 1; i <= N; i++ {
		fmt.Fscan(in, &A[i])
		pos[A[i]] = append(pos[A[i]], i)
	}

	var Q int
	fmt.Fscan(in, &Q)

	for ; Q > 0; Q-- {
		var L, R int
		fmt.Fscan(in, &L, &R)

		candidate := K + 1

		for {
			list, ok := pos[candidate]
			if !ok {
				break
			}

			if existsInRange(list, L, R) {
				candidate++
			} else {
				break
			}
		}

		fmt.Fprintln(out, candidate)
	}
}
