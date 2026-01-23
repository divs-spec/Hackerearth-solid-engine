import java.io.*;
import java.util.*;

public class TestClass {

    static final class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in = System.in;

        int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c, sgn = 1, res = 0;
            do { c = read(); } while (c <= ' ');
            if (c == '-') { sgn = -1; c = read(); }
            while (c > ' ') {
                res = res * 10 + (c - '0');
                c = read();
            }
            return res * sgn;
        }

        void nextRow(byte[] row) throws IOException {
            int c, i = 0;
            do { c = read(); } while (c <= ' ');
            while (c > ' ') {
                row[i++] = (byte)(c - '0');
                c = read();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();

        int n = fs.nextInt();
        int m = fs.nextInt();

        int[][] pref = new int[n + 1][m + 1];
        byte[] row = new byte[m];

        for (int i = 1; i <= n; i++) {
            fs.nextRow(row);
            for (int j = 1; j <= m; j++) {
                pref[i][j] = row[j - 1]
                        + pref[i - 1][j]
                        + pref[i][j - 1]
                        - pref[i - 1][j - 1];
            }
        }

        int ans = Integer.MAX_VALUE;
        int max = Math.max(n, m);

        for (int k = 2; k <= max; ) {

            int bn = (n + k - 1) / k;
            int bm = (m + k - 1) / k;

            int total = 0;

            for (int i = 0; i < bn; i++) {
                int r1 = i * k + 1;
                int r2 = Math.min(n, r1 + k - 1);

                for (int j = 0; j < bm; j++) {
                    int c1 = j * k + 1;
                    int c2 = Math.min(m, c1 + k - 1);

                    int ones = pref[r2][c2]
                             - pref[r1 - 1][c2]
                             - pref[r2][c1 - 1]
                             + pref[r1 - 1][c1 - 1];

                    int area = k * k;
                    total += Math.min(ones, area - ones);

                    if (total >= ans) break;
                }
                if (total >= ans) break;
            }

            ans = Math.min(ans, total);

            // jump k where layout changes
            int nk = Math.min(
                    n / bn == 0 ? max + 1 : n / bn + 1,
                    m / bm == 0 ? max + 1 : m / bm + 1
            );
            k = Math.max(k + 1, nk);
        }

        System.out.println(ans);
    }
}
