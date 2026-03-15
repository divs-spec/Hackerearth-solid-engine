type Fancy struct {
    nums []int64
    mul  int64
    add  int64
}

const mod int64 = 1000000007

func Constructor() Fancy {
    return Fancy{
        nums: []int64{},
        mul:  1,
        add:  0,
    }
}

func modPow(base, exp int64) int64 {
    result := int64(1)
    base %= mod

    for exp > 0 {
        if exp&1 == 1 {
            result = (result * base) % mod
        }
        base = (base * base) % mod
        exp >>= 1
    }

    return result
}

func modInverse(x int64) int64 {
    return modPow(x, mod-2)
}

func (this *Fancy) Append(val int) {
    x := (int64(val) - this.add + mod) % mod
    x = (x * modInverse(this.mul)) % mod
    this.nums = append(this.nums, x)
}

func (this *Fancy) AddAll(inc int) {
    this.add = (this.add + int64(inc)) % mod
}

func (this *Fancy) MultAll(m int) {
    this.mul = (this.mul * int64(m)) % mod
    this.add = (this.add * int64(m)) % mod
}

func (this *Fancy) GetIndex(idx int) int {
    if idx >= len(this.nums) {
        return -1
    }

    val := this.nums[idx]
    return int((val*this.mul + this.add) % mod)
}
