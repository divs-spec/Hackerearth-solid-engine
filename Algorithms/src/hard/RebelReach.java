import java.io.*;
import java.util.*;

public class Main {

    static final class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int readByte() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c, sgn = 1, res = 0;
            do c = readByte(); while (c <= ' ');
            if (c == '-') {
                sgn = -1;
                c = readByte();
            }
            while (c > ' ') {
                res = res * 10 + (c - '0');
                c = readByte();
            }
            return res * sgn;
        }

        long nextLong() throws IOException {
            int c, sgn = 1;
            long res = 0;
            do c = readByte(); while (c <= ' ');
            if (c == '-') {
                sgn = -1;
                c = readByte();
            }
            while (c > ' ') {
                res = res * 10 + (c - '0');
                c = readByte();
            }
            return res * sgn;
        }
    }

    static int N;
    static ArrayList<Integer>[] tree;
    static long[] guards;
    static int[][] par;
    static long[][] vals;
    static int LOG;

    static void dfs(int u, int p) {
        int curP = p;
        int i = 0;
        long temp = guards[u];

        while (curP != -1 && i < LOG) {
            par[u][i] = curP;
            vals[u][i] = temp;

            temp += vals[curP][i];
            curP = par[curP][i];
            i++;
        }

        for (int v : tree[u]) {
            if (v != p) {
                dfs(v, u);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int test = fs.nextInt();
        if (test < 1 || test > 100_000) return;

        int totalN = 0;
        int totalQ = 0;

        while (test-- > 0) {
            N = fs.nextInt();
            if (N < 3 || N > 1_000_000) return;
            totalN += N;

            tree = new ArrayList[N];
            for (int i = 0; i < N; i++) tree[i] = new ArrayList<>();

            for (int i = 0; i < N - 1; i++) {
                int u = fs.nextInt() - 1;
                int v = fs.nextInt() - 1;
                if (u < 0 || v < 0 || u >= N || v >= N) return;
                tree[u].add(v);
                tree[v].add(u);
            }

            guards = new long[N];
            for (int i = 0; i < N; i++) {
                guards[i] = fs.nextLong();
                if (guards[i] < 1 || guards[i] > 1_000_000_000L) return;
            }

            LOG = (int) (Math.log(N) / Math.log(2)) + 2;
            par = new int[N][LOG];
            vals = new long[N][LOG];

            for (int i = 0; i < N; i++) {
                Arrays.fill(par[i], -1);
            }

            dfs(0, -1);

            int Q = fs.nextInt();
            if (Q < 1 || Q > 1_000_000) return;
            totalQ += Q;

            while (Q-- > 0) {
                int u = fs.nextInt() - 1;
                long x = fs.nextLong();

                if (u < 0 || u >= N || x < 1 || x > 1_000_000_000_000_000L)
                    return;

                while (x > 0 && u != 0) {
                    int p = -1;
                    long used = 0;

                    for (int i = 0; i < LOG; i++) {
                        if (par[u][i] == -1) break;
                        if (vals[u][i] >= x) break;

                        p = par[u][i];
                        used = vals[u][i];
                    }

                    if (p == -1) break;

                    x -= used;
                    u = p;
                }

                out.append(u + 1).append('\n');
            }
        }

        if (totalN > 1_000_000 || totalQ > 1_000_000) return;

        System.out.print(out.toString());
    }
}
