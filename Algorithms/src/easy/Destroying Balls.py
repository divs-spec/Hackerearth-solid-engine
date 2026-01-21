import sys
import time
from collections import defaultdict

def main():
    start_time = time.time()

    input_data = sys.stdin.read().strip().split()
    idx = 0

    T = int(input_data[idx])
    idx += 1

    out = []

    for _ in range(T):
        n = int(input_data[idx])
        idx += 1
        original_n = n

        freq = defaultdict(int)

        for _ in range(original_n):
            x = int(input_data[idx])
            idx += 1
            freq[x] += 1

        possible = True

        while n > 0:
            if freq[n] > 0:
                n -= freq[n]
            else:
                possible = False
                break

        out.append("YES" if possible else "NO")

    sys.stdout.write("\n".join(out))

    end_time = time.time()
    duration = end_time - start_time
    sys.stderr.write(f"Time Taken : {duration:.6f} secs\n")

if __name__ == "__main__":
    main()
