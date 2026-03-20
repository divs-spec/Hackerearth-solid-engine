package main

import (
	"fmt"
	"strconv"
)

func checker1(n int64) int64 {
	s := strconv.FormatInt(n, 10)
	var ans int64 = 0
	index := -1

	for i := 0; i < len(s); i++ {
		digit := int(s[i] - '0')
		if ((digit % 2) & 1) == 0 {
			index = i
			break
		}
	}

	if index == -1 {
		return n
	}

	for i := 0; i < index; i++ {
		ans = ans*10 + int64(s[i]-'0')
	}

	ans = ans*10 + int64(s[index]-'0'-1)

	for i := index + 1; i < len(s); i++ {
		ans = ans*10 + 9
	}

	return ans
}

func checker2(n int64) int64 {
	s := strconv.FormatInt(n, 10)
	var ans int64 = 0
	index := -1

	for i := 0; i < len(s); i++ {
		digit := int(s[i] - '0')
		if (digit & 1) == 0 {
			index = i
			break
		}
	}

	if index == -1 {
		return n
	}

	for i := 0; i < index; i++ {
		ans = ans*10 + int64(s[i]-'0')
	}

	ans = ans*10 + int64(s[index]-'0'+1)

	for i := index + 1; i < len(s); i++ {
		ans = ans*10 + 1
	}

	return ans
}

func main() {
	var n int64
	fmt.Scan(&n)

	minBest := checker1(n)
	maxBest := checker2(n)

	if abs(n-minBest) <= abs(n-maxBest) {
		fmt.Println(minBest)
	} else {
		fmt.Println(maxBest)
	}
}

func abs(x int64) int64 {
	if x < 0 {
		return -x
	}
	return x
}
