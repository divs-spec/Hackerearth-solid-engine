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
            while ((c = read()) <= ' ' && c != -1);
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }

    static class Pair {
        int to, w;
        Pair(int to, int w) {
            this.to = to;
            this.w = w;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();

        int n = fs.nextInt();

        ArrayList<Pair>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) g[i] = new ArrayList<>();

        for (int i = 1; i < n; i++) {
            int u = fs.nextInt() - 1;
            int v = fs.nextInt() - 1;
            int wEdge = fs.nextInt();

            g[u].add(new Pair(v, wEdge));
            g[v].add(new Pair(u, wEdge));
        }

        boolean[] visit = new boolean[n];

        int[][] dp = new int[n][20];
        int[][] p = new int[n][20];

        int[] w = new int[n];
        int[] a = new int[n];
        int[] h = new int[n];
        long[] s = new long[n];

        for (int i = 0; i < n; i++) {
            w[i] = fs.nextInt();
        }

        h[0] = 1;

        Queue<Integer> q = new ArrayDeque<>();
        q.add(0);

        while (!q.isEmpty()) {
            int u = q.poll();

            visit[u] = true;

            for (int i = 1; (h[u] - (1 << i)) > 0; i++) {
                int m = p[u][i - 1];
                p[u][i] = p[m][i - 1];

                int x = dp[m][i - 1];
                int y = dp[u][i - 1];

                if ((long) w[y] + s[y] < (long) w[x] + s[x])
                    dp[u][i] = y;
                else
                    dp[u][i] = x;
            }

            for (Pair edge : g[u]) {
                int v = edge.to;

                if (visit[v]) continue;

                p[v][0] = u;
                h[v] = h[u] + 1;
                s[v] = s[u] + edge.w;

                int x = a[u];

                if ((s[v] - s[x] + w[x]) < w[v])
                    a[v] = x;
                else
                    a[v] = v;

                if ((long) w[v] + edge.w < w[u])
                    dp[v][0] = v;
                else
                    dp[v][0] = u;

                q.add(v);
            }
        }

        int Q = fs.nextInt();

        StringBuilder out = new StringBuilder();

        while (Q-- > 0) {
            int u = fs.nextInt() - 1;
            int v = fs.nextInt() - 1;

            long ans = (s[u] - s[v]) << 1;

            int x = a[u];
            int y = u;
            int r = u;

            for (int i = 19; i >= 0; i--) {
                if ((h[r] - (1 << i)) >= h[v]) {
                    int z = dp[r][i];

                    if ((long) w[z] + s[z] <= (long) w[y] + s[y])
                        y = z;

                    r = p[r][i];
                }
            }

            if (h[y] < h[x]) {
                long t = (long) w[x] + w[y] - (s[x] - s[y]);
                if (t < 0) ans += t;
            }

            out.append(ans).append('\n');
        }

        System.out.print(out);
    }
}
