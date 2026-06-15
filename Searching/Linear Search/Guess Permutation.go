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
	return &FastScanner{bufio.NewReaderSize(os.Stdin, 1<<20)}
}

func (fs *FastScanner) NextInt() int64 {
	var sign int64 = 1
	var val int64
	c, _ := fs.r.ReadByte()

	for (c < '0' || c > '9') && c != '-' {
		c, _ = fs.r.ReadByte()
	}

	if c == '-' {
		sign = -1
		c, _ = fs.r.ReadByte()
	}

	for c >= '0' && c <= '9' {
		val = val*10 + int64(c-'0')
		c2, err := fs.r.ReadByte()
		if err != nil {
			break
		}
		c = c2
	}

	if c < '0' || c > '9' {
		_ = fs.r.UnreadByte()
	}

	return sign * val
}

func main() {
	fs := NewFastScanner()
	out := bufio.NewWriterSize(os.Stdout, 1<<20)
	defer out.Flush()

	T := int(fs.NextInt())

	for ; T > 0; T-- {
		N := int(fs.NextInt())

		prefix := make([]int64, N+1)
		seen := make(map[int64]bool, N+1)

		var mn, mx int64 = 0, 0
		seen[0] = true

		ok := true

		for i := 1; i <= N; i++ {
			a := fs.NextInt()
			prefix[i] = prefix[i-1] + a

			if seen[prefix[i]] {
				ok = false
			}
			seen[prefix[i]] = true

			if prefix[i] < mn {
				mn = prefix[i]
			}
			if prefix[i] > mx {
				mx = prefix[i]
			}
		}

		if !ok || mx-mn != int64(N) {
			fmt.Fprintln(out, -1)
			continue
		}

		shift := int64(1) - mn

		for i := 0; i <= N; i++ {
			if i > 0 {
				fmt.Fprint(out, " ")
			}
			fmt.Fprint(out, shift+prefix[i])
		}
		fmt.Fprintln(out)
	}
}
