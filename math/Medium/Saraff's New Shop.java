import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();
        while (t-- > 0) {

            long k = fs.nextLong();
            long n = fs.nextLong();

            // Same assertions as C++
            assert n >= 1 && n <= (long)1e18;
            assert k >= 1 && k <= (long)1e6;

            long ans = 0;
            while (n > 0) {
                ans++;
                n /= (k + 1);
            }

            out.append(ans).append('\n');
        }

        System.out.print(out.toString());
    }

    // Fast input (faster than Scanner)
    static class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in;

        FastScanner(InputStream in) {
            this.in = in;
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
            int c;
            do {
                c = read();
            } while (c <= ' ');

            boolean neg = false;
            if (c == '-') {
                neg = true;
                c = read();
            }

            long val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return neg ? -val : val;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}
