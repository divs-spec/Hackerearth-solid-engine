jhooli = [False] * 26


def match_string(a):
    match = [False] * 26
    for char in a:
        if 'a' <= char <= 'z':
            match[ord(char) - ord('a')] = True
        elif 'A' <= char <= 'Z':
            match[ord(char) - ord('A')] = True
    ans = sum(1 for i in range(26) if match[i] and jhooli[i])
    return ans


if __name__ == "__main__":
    t = int(input())
    jhooli[ord('l') - ord('a')] = True
    jhooli[ord('i') - ord('a')] = True
    jhooli[ord('t') - ord('a')] = True
    jhooli[ord('e') - ord('a')] = True
    jhooli[ord('j') - ord('a')] = True
    jhooli[ord('h') - ord('a')] = True
    jhooli[ord('o') - ord('a')] = True

    for _ in range(t):
        n, k = map(int, input().split())
        data = input().split()
        v = []
        s = []
        for i in range(n):
            a = data[i]
            s.append(a)
            x = match_string(a)
            v.append((x, i))
        v.sort()
        print(" ".join(s[v[i][1]] for i in range(k)))
