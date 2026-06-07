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

        int N = fs.nextInt();
        int Q = fs.nextInt();

        int[] color = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            color[i] = fs.nextInt();
        }

        ArrayList<Integer>[] g = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();

            g[u].add(v);
            g[v].add(u);
        }

        int[] freq = new int[N + 1];
        for (int i = 0; i < Q; i++) {
            int x = fs.nextInt();
            freq[x]++;
        }

        int[] parent = new int[N + 1];
        int[] order = new int[N];
        int idx = 0;

        int[] stack = new int[N];
        int top = 0;

        stack[top++] = 1;
        parent[1] = -1;

        while (top > 0) {
            int u = stack[--top];
            order[idx++] = u;

            for (int v : g[u]) {
                if (v != parent[u]) {
                    parent[v] = u;
                    stack[top++] = v;
                }
            }
        }

        long[] cnt = new long[N + 1];
        for (int i = 1; i <= N; i++) {
            cnt[i] = freq[i];
        }

        for (int i = N - 1; i > 0; i--) {
            int u = order[i];
            int p = parent[u];
            cnt[p] += cnt[u];
        }

        long ans = 0;

        for (int v = 1; v <= N; v++) {
            int finalColor = color[v] ^ ((int) (cnt[v] & 1));
            if (finalColor == 1) {
                ans++;
            }
        }

        System.out.println(ans);
    }
}
