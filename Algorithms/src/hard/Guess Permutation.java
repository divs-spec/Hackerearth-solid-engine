import java.io.*;
import java.util.*;

class TestClass {

    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

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

            long sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            long val = 0;
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

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder sb = new StringBuilder();

        int T = fs.nextInt();

        while (T-- > 0) {
            int N = fs.nextInt();

            long[] prefix = new long[N + 1];
            HashSet<Long> seen = new HashSet<>(2 * (N + 1));

            long mn = 0, mx = 0;
            seen.add(0L);

            boolean ok = true;

            for (int i = 1; i <= N; i++) {
                long a = fs.nextLong();
                prefix[i] = prefix[i - 1] + a;

                if (!seen.add(prefix[i])) {
                    ok = false;
                }

                mn = Math.min(mn, prefix[i]);
                mx = Math.max(mx, prefix[i]);
            }

            if (!ok || mx - mn != N) {
                sb.append("-1\n");
                continue;
            }

            long shift = 1 - mn;

            for (int i = 0; i <= N; i++) {
                if (i > 0) sb.append(' ');
                sb.append(shift + prefix[i]);
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
