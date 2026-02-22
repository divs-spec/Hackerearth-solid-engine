import java.io.*;
import java.util.*;

public class Main {

    static double[][][] dp = new double[101][11][251];
    static boolean[][][] vis = new boolean[101][11][251];
    static int TARGET;

    static double solve(int a, int l, int s) {
        if (s >= TARGET) return 1.0;
        if (a == 100 || l == 0) return 0.0;

        if (vis[a][l][s]) return dp[a][l][s];
        vis[a][l][s] = true;

        double res = 0.0;

        // Rolls 1 to 4
        for (int i = 1; i <= 4; i++) {
            res += (1.0 / 6.0) * solve(a + 1, l, Math.min(s + i, TARGET));
        }

        // Green (extra roll)
        res += (1.0 / 6.0) * solve(a, l, Math.min(s + 5, TARGET));

        // Red (lose life)
        res += (1.0 / 6.0) * solve(a + 1, l - 1, s);

        return dp[a][l][s] = res;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int T = fs.nextInt();

        while (T-- > 0) {
            int a = fs.nextInt();
            int l = fs.nextInt();
            int c = fs.nextInt();
            int t = fs.nextInt();

            TARGET = t;

            // reset visited array
            for (int i = 0; i <= 100; i++)
                for (int j = 0; j <= 10; j++)
                    Arrays.fill(vis[i][j], false);

            double ans = solve(a, 10 - l, c) * 100.0;

            // truncate to 2 decimals (NOT rounding)
            ans = Math.floor(ans * 100.0) / 100.0;

            out.append(String.format(Locale.US, "%.2f%n", ans));
        }

        System.out.print(out.toString());
    }

    // Fast input
    static class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in;

        FastScanner(InputStream in) {
            this.in = in;
        }

        int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c, sign = 1, val = 0;
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
    }
}
