import java.io.*;
import java.util.*;

public class Main {

    // --------- Fast Input ----------
    static class FastScanner {
        private final byte[] data;
        private int idx = 0;

        FastScanner() throws IOException {
            data = System.in.readAllBytes();
        }

        int nextInt() {
            int num = 0;
            int sign = 1;
            while (idx < data.length && (data[idx] == ' ' || data[idx] == '\n' || data[idx] == '\r')) idx++;
            if (data[idx] == '-') {
                sign = -1;
                idx++;
            }
            while (idx < data.length && data[idx] >= '0') {
                num = num * 10 + (data[idx] - '0');
                idx++;
            }
            return num * sign;
        }
    }

    // --------- Globals ----------
    static int n, m;
    static ArrayList<Edge>[] edges;
    static boolean[] visited;
    static int[] tin, low;
    static int timer;
    static ArrayList<Long> bridgeLen;

    static class Edge {
        int to;
        long w;
        Edge(int to, long w) {
            this.to = to;
            this.w = w;
        }
    }

    // --------- DFS for Bridges ----------
    static void dfs(int v, int parent) {
        visited[v] = true;
        tin[v] = low[v] = timer++;

        for (Edge e : edges[v]) {
            if (e.to == parent) continue;

            if (visited[e.to]) {
                low[v] = Math.min(low[v], tin[e.to]);
            } else {
                dfs(e.to, v);
                low[v] = Math.min(low[v], low[e.to]);
                if (low[e.to] > tin[v]) {
                    bridgeLen.add(e.w);
                }
            }
        }
    }

    static void findBridges() {
        timer = 0;
        Arrays.fill(visited, false);
        Arrays.fill(tin, -1);
        Arrays.fill(low, -1);

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, -1);
            }
        }
    }

    // --------- Solve ----------
    static void solve(FastScanner fs) {
        n = fs.nextInt();
        m = fs.nextInt();

        edges = new ArrayList[n];
        for (int i = 0; i < n; i++) edges[i] = new ArrayList<>();

        visited = new boolean[n];
        tin = new int[n];
        low = new int[n];
        bridgeLen = new ArrayList<>();

        long[] weight = new long[m];
        for (int i = 0; i < m; i++) {
            weight[i] = fs.nextInt();
        }

        for (int i = 0; i < m; i++) {
            int x = fs.nextInt() - 1;
            int y = fs.nextInt() - 1;
            edges[x].add(new Edge(y, weight[i]));
            edges[y].add(new Edge(x, weight[i]));
        }

        findBridges();

        Collections.sort(bridgeLen);

        long min1 = 0, min2 = 0;
        boolean toggle = false;
        for (long x : bridgeLen) {
            if (!toggle) min1 += x;
            else min2 += x;
            toggle = !toggle;
        }
        long minAns = Math.min(min1, min2);

        long max1 = 0, max2 = 0;
        toggle = false;
        for (int i = bridgeLen.size() - 1; i >= 0; i--) {
            long x = bridgeLen.get(i);
            if (!toggle) max1 += x;
            else max2 += x;
            toggle = !toggle;
        }
        long maxAns = Math.max(max1, max2);

        System.out.println(maxAns + " " + minAns);
    }

    // --------- Main ----------
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        solve(fs);
    }
}
