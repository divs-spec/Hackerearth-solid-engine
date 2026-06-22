import java.io.*;
import java.util.*;

public class Main {
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            in = is;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        long nextLong() throws IOException {
            long sign = 1, val = 0;
            int c;
            do {
                c = read();
            } while (c <= ' ');

            if (c == '-') {
                sign = -1;
                c = read();
            }

            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }

    static long countPairs(int n, int x) {
        if (x == 0) return 1L * n * n;

        long res = 0;

        for (int b = x + 1; b <= n; b++) {
            int q = n / b;
            int rem = n % b;

            res += 1L * q * (b - x);

            if (rem >= x) {
                res += (rem - x + 1);
            }
        }

        return res;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int T = fs.nextInt();

        while (T-- > 0) {
            int N = fs.nextInt();
            long R = fs.nextLong();

            long total = 1L * N * N;

            if (R > total) {
                out.append(-1).append('\n');
                continue;
            }

            int lo = 0, hi = N - 1;
            int ans = 0;

            while (lo <= hi) {
                int mid = (lo + hi) >>> 1;

                if (countPairs(N, mid) >= R) {
                    ans = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }

            out.append(ans).append('\n');
        }

        System.out.print(out);
    }
}
