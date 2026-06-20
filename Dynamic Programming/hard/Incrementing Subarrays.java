import java.io.*;
import java.util.*;

public class Main {

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
        StringBuilder out = new StringBuilder();

        int T = fs.nextInt();

        while (T-- > 0) {
            int N = fs.nextInt();
            long K = fs.nextLong();

            long[] A = new long[N];
            for (int i = 0; i < N; i++) A[i] = fs.nextLong();

            if (N == 1) {
                out.append(0).append('\n');
                continue;
            }

            long base = 0;
            long[] w = new long[N];
            long[] start = new long[N];
            long[] end = new long[N];

            for (int j = 1; j < N; j++) {
                long cur = A[j] % A[j - 1];
                base += cur;

                w[j] = (A[j] + K) % (A[j - 1] + K) - cur;
            }

            start[0] = 0;
            for (int l = 1; l < N; l++) {
                long cur = A[l] % A[l - 1];
                start[l] = (A[l] + K) % A[l - 1] - cur;
            }

            end[N - 1] = 0;
            for (int r = 0; r < N - 1; r++) {
                long cur = A[r + 1] % A[r];
                end[r] = A[r + 1] % (A[r] + K) - cur;
            }

            long[] pref = new long[N];
            for (int i = 1; i < N; i++) {
                pref[i] = pref[i - 1] + w[i];
            }

            long best = 0;
            long bestLeft = start[0] - pref[0];

            for (int r = 0; r < N; r++) {
                bestLeft = Math.max(bestLeft, start[r] - pref[r]);

                long gain = end[r] + pref[r] + bestLeft;
                best = Math.max(best, gain);
            }

            out.append(base + best).append('\n');
        }

        System.out.print(out);
    }
}
