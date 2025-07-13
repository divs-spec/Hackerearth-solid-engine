test = int(input())
hashing = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ"
for _ in range(test):
    l = list(map(str, input().split()))
    length = len(l)
    add = 0
    for i in l:
        for j in range(len(i)):
            add += (j + hashing.find(i[j]))
    print(add*length)
