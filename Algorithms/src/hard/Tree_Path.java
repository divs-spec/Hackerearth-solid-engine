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

    static int N, K;
    static int[] W;
    static ArrayList<Integer>[] g;

    static int[] dist;
    static boolean[] visitedComp;
    static int[] queue;

    static class Pair {
        int node, dist;
        Pair(int n, int d) {
            node = n;
            dist = d;
        }
    }

    static Pair bfs(int start, int limit, boolean markComponent) {

        int head = 0, tail = 0;
        queue[tail++] = start;

        Arrays.fill(dist, -1);
        dist[start] = 0;

        if (markComponent) visitedComp[start] = true;

        int farNode = start;
        int farDist = 0;

        while (head < tail) {
            int u = queue[head++];

            if (dist[u] > farDist) {
                farDist = dist[u];
                farNode = u;
            }

            for (int v : g[u]) {

                if (W[v] > limit || dist[v] != -1)
                    continue;

                dist[v] = dist[u] + 1;
                queue[tail++] = v;

                if (markComponent)
                    visitedComp[v] = true;
            }
        }

        return new Pair(farNode, farDist);
    }

    static boolean check(int limit) {

        Arrays.fill(visitedComp, false);

        for (int i = 1; i <= N; i++) {

            if (W[i] > limit || visitedComp[i])
                continue;

            Pair p1 = bfs(i, limit, true);

            Pair p2 = bfs(p1.node, limit, false);

            if (p2.dist >= K)
                return true;
        }

        return false;
    }

    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner();

        N = fs.nextInt();
        K = fs.nextInt();

        W = new int[N + 1];

        int maxW = 0;

        for (int i = 1; i <= N; i++) {
            W[i] = fs.nextInt();
            maxW = Math.max(maxW, W[i]);
        }

        g = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)
            g[i] = new ArrayList<>();

        for (int i = 0; i < N - 1; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();

            g[u].add(v);
            g[v].add(u);
        }

        dist = new int[N + 1];
        visitedComp = new boolean[N + 1];
        queue = new int[N + 5];

        int low = 1;
        int high = maxW;
        int ans = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (check(mid)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        System.out.println(ans);
    }
}
