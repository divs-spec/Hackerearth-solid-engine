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

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            int res = 0;
            while (c > ' ') {
                res = res * 10 + (c - '0');
                c = read();
            }
            return res * sign;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();

        int N = fs.nextInt();
        int K = fs.nextInt();

        int[] H = new int[N];
        for (int i = 0; i < N; i++) {
            H[i] = fs.nextInt();
        }

        // Already at the destination
        if (N == 1) {
            System.out.println(0);
            return;
        }

        final int INF = 1_000_000_000;

        /*
         * best[i][d] =
         * minimum jumps to reach i when the last jump
         * has height difference >= d.
         *
         * Maximum possible height difference is N.
         */
        int[][] best = new int[N][N + 1];

        for (int i = 0; i < N; i++) {
            Arrays.fill(best[i], INF);
        }

        /*
         * First jump is unrestricted.
         *
         * Treat building 0 as having a previous jump
         * of every possible height difference with cost 0.
         */
        Arrays.fill(best[0], 0);

        for (int i = 1; i < N; i++) {

            int[] exact = new int[N + 1];
            Arrays.fill(exact, INF);

            /*
             * We can come from at most K buildings behind us.
             */
            int left = Math.max(0, i - K);

            for (int j = left; j < i; j++) {

                int diff = Math.abs(H[i] - H[j]);

                /*
                 * Previous jump must have difference >= diff.
                 */
                if (best[j][diff] != INF) {
                    int candidate = best[j][diff] + 1;

                    if (candidate < exact[diff]) {
                        exact[diff] = candidate;
                    }
                }
            }

            /*
             * Convert exact[] into suffix minimum:
             *
             * best[i][d] = min(exact[d], exact[d+1], ...)
             */
            int cur = INF;

            for (int d = N; d >= 0; d--) {
                cur = Math.min(cur, exact[d]);
                best[i][d] = cur;
            }
        }

        int answer = best[N - 1][0];

        System.out.println(answer == INF ? -1 : answer);
    }
}
