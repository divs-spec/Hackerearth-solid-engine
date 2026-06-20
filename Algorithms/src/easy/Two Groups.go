package main

import (
	"bufio"
	"fmt"
	"os"
)

const MOD int64 = 1000000007

type FastScanner struct {
	r *bufio.Reader
}

func NewFastScanner() *FastScanner {
	return &FastScanner{bufio.NewReaderSize(os.Stdin, 1<<20)}
}

func (fs *FastScanner) NextInt64() int64 {
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
		nc, err := fs.r.ReadByte()
		if err != nil {
			break
		}
		c = nc
	}

	if c < '0' || c > '9' {
		_ = fs.r.UnreadByte()
	}

	return sign * val
}

func modPow(exp int64) int64 {
	base := int64(2)
	res := int64(1)

	for exp > 0 {
		if exp&1 == 1 {
			res = (res * base) % MOD
		}
		base = (base * base) % MOD
		exp >>= 1
	}

	return res
}

func main() {
	fs := NewFastScanner()
	out := bufio.NewWriterSize(os.Stdout, 1<<20)
	defer out.Flush()

	T := int(fs.NextInt64())

	for ; T > 0; T-- {
		n := fs.NextInt64()

		ans := (modPow(n) - 2 + MOD) % MOD
		fmt.Fprintln(out, ans)
	}
}
