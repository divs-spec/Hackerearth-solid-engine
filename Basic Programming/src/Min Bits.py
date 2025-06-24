def countMinSetBits(low, high):
 low -= 1
 non_matching_length = (low ^ high).bit_length()
 # print(non_matching_length, bin(low), bin(high), bin(low ^ high))
 common = low & (-1 << non_matching_length)
 # print(common, bin(common))
 match_low = low ^ common
 x = common | 1 << match_low.bit_length()
 # print(x, bin(x), bin(match_low))
 return x
T = int(input())
for _ in range(T):
 ll=""
 # try:
 ll = input().split()
 l,r = map(int,ll)
 ans = countMinSetBits(l,r)
 k=-1 << 3
 print(ans)
