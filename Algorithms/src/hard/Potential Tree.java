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

            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }

            return val * sign;
        }
    }

    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int T = fs.nextInt();

        while (T-- > 0) {

            int n = fs.nextInt();

            ArrayList<Integer>[] g = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                g[i] = new ArrayList<>();
            }

            int[] deg = new int[n];

            for (int i = 0; i < n - 1; i++) {
                int u = fs.nextInt();
                int v = fs.nextInt();

                g[u].add(v);
                g[v].add(u);

                deg[u]++;
                deg[v]++;
            }

            int[] parent = new int[n];
            Arrays.fill(parent, -1);

            int[] order = new int[n];
            int orderSize = 0;

            int[] stack = new int[n];
            int top = 0;

            stack[top++] = 0;
            parent[0] = -2;

            while (top > 0) {
                int u = stack[--top];

                order[orderSize++] = u;

                for (int v : g[u]) {
                    if (parent[v] == -1) {
                        parent[v] = u;
                        stack[top++] = v;
                    }
                }
            }

            boolean[] down = new boolean[n];

            for (int idx = n - 1; idx >= 0; idx--) {

                int u = order[idx];

                boolean hasChild = false;
                boolean val = false;

                for (int v : g[u]) {
                    if (parent[v] == u) {
                        hasChild = true;
                        if (down[v]) {
                            val = true;
                        }
                    }
                }

                if (!hasChild) {
                    down[u] = (u & 1) == 1;
                } else {
                    down[u] = val;
                }
            }

            boolean[] up = new boolean[n];
            int[] ans = new int[n];

            for (int idx = 0; idx < n; idx++) {

                int u = order[idx];

                int totalTrue = 0;

                if (parent[u] >= 0 && up[u]) {
                    totalTrue++;
                }

                for (int v : g[u]) {
                    if (parent[v] == u && down[v]) {
                        totalTrue++;
                    }
                }

                ans[u] = totalTrue;

                for (int v : g[u]) {

                    if (parent[v] != u) {
                        continue;
                    }

                    boolean msg;

                    if (deg[u] == 1) {
                        msg = (u & 1) == 1;
                    } else {
                        int cnt = totalTrue;

                        if (down[v]) {
                            cnt--;
                        }

                        msg = cnt > 0;
                    }

                    up[v] = msg;
                }
            }

            for (int i = 0; i < n; i++) {
                if (i > 0) out.append(' ');
                out.append(ans[i]);
            }
            out.append('\n');
        }

        System.out.print(out);
    }
}
