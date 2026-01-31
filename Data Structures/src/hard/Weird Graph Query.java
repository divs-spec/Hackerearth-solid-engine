import java.io.*;
import java.util.*;

public class Solution {

    // ================= CONSTANTS =================
    static final int MAXN = 1000 + 5;
    static final int INF = (int) 1e9;

    // ================= GRAPH =================
    static ArrayList<int[]>[] g = new ArrayList[MAXN];
    static int[][] dist = new int[MAXN][MAXN];
    static ArrayList<int[]>[] allversions = new ArrayList[MAXN];

    // ================= PERSISTENT SEG TREE =================
    static class Node {
        int left, right, data;
        Node() {
            left = -1;
            right = -1;
            data = 0;
        }
    }

    static Node[] buf = new Node[2000 * 1000 * 10];
    static int PTR = 0;

    static int getNode() {
        buf[PTR] = new Node();
        return PTR++;
    }

    static int getNode(int l, int r, int d) {
        buf[PTR] = new Node();
        buf[PTR].left = l;
        buf[PTR].right = r;
        buf[PTR].data = d;
        return PTR++;
    }

    static void expand(int node) {
        if (buf[node].left == -1) buf[node].left = getNode();
        if (buf[node].right == -1) buf[node].right = getNode();
    }

    static int update(int node, int ss, int se, int idx, int val) {
        if (idx < ss || idx > se) return node;
        if (ss == se) {
            return getNode(-1, -1, val);
        }
        expand(node);
        int mid = (ss + se) >> 1;
        int x = update(buf[node].left, ss, mid, idx, val);
        int y = update(buf[node].right, mid + 1, se, idx, val);
        return getNode(x, y, buf[x].data + buf[y].data);
    }

    static int query(int node, int ss, int se, int l, int r) {
        if (node == -1 || r < ss || l > se) return 0;
        if (l <= ss && se <= r) return buf[node].data;
        int mid = (ss + se) >> 1;
        return query(buf[node].left, ss, mid, l, r)
             + query(buf[node].right, mid + 1, se, l, r);
    }

    // ================= DIJKSTRA =================
    static void dijkstra(int source, int n) {
        Arrays.fill(dist[source], INF);
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.add(new int[]{0, source});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int d = cur[0], u = cur[1];
            if (dist[source][u] <= d) continue;
            dist[source][u] = d;
            for (int[] e : g[u]) {
                int v = e[0], w = e[1];
                if (d + w < dist[source][v]) {
                    pq.add(new int[]{d + w, v});
                }
            }
        }
    }

    // ================= BUILD PERSISTENT TREES =================
    static void buildPersSegtree(int x, int n) {
        TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(dist[x][i], k -> new ArrayList<>()).add(i);
        }

        allversions[x].add(new int[]{0, getNode()});

        for (Map.Entry<Integer, ArrayList<Integer>> e : map.entrySet()) {
            int id = allversions[x].get(allversions[x].size() - 1)[1];
            for (int idx : e.getValue()) {
                id = update(id, 0, MAXN, idx, 1);
            }
            allversions[x].add(new int[]{e.getKey(), id});
        }
    }

    static boolean checkAnswer(int nodeU, int l, int r, int id, int k) {
        return query(allversions[nodeU].get(id)[1], 0, MAXN, l, r) >= k;
    }

    static int getAnswer(int node, int l, int r, int k) {
        int low = 0, high = allversions[node].size() - 1;
        int best = allversions[node].get(high)[0];

        while (low <= high) {
            int mid = (low + high) >> 1;
            if (checkAnswer(node, l, r, mid, k)) {
                best = Math.min(best, allversions[node].get(mid)[0]);
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return best;
    }

    // ================= SOLVE =================
    static void solve(FastScanner fs, PrintWriter out) {
        int n = fs.nextInt();
        int m = fs.nextInt();

        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
            allversions[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int a = fs.nextInt() - 1;
            int b = fs.nextInt() - 1;
            int c = fs.nextInt();
            g[a].add(new int[]{b, c});
            g[b].add(new int[]{a, c});
        }

        for (int i = 0; i < n; i++) dijkstra(i, n);
        for (int i = 0; i < n; i++) buildPersSegtree(i, n);

        int q = fs.nextInt();
        while (q-- > 0) {
            int u = fs.nextInt() - 1;
            int l = fs.nextInt() - 1;
            int r = fs.nextInt() - 1;
            int k = fs.nextInt();
            out.println(getAnswer(u, l, r, k));
        }
    }

    // ================= FAST IO =================
    static class FastScanner {
        BufferedInputStream in = new BufferedInputStream(System.in);
        byte[] buffer = new byte[1 << 16];
        int ptr = 0, len = 0;

        int read() {
            if (ptr >= len) {
                try {
                    len = in.read(buffer);
                    ptr = 0;
                    if (len <= 0) return -1;
                } catch (IOException e) {
                    return -1;
                }
            }
            return buffer[ptr++];
        }

        int nextInt() {
            int c, sign = 1, val = 0;
            do c = read(); while (c <= ' ');
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

    public static void main(String[] args) {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        solve(fs, out);
        out.flush();
    }
}
