/* Approach: Treat every prime-numbered village as a source with distance 0 and run multi-source Dijkstra. 
  Each road (u,v) has weight max(u,v). 
  The resulting shortest distance to each village is the minimum time for a vaccine to arrive there from any village that already has a vaccine.
  Complexity: O((N + M) log N).
*/
import java.io.*;
import java.util.*;

public class Main {

    static final long INF = Long.MAX_VALUE / 4;

    static class Edge {
        int to;
        int w;

        Edge(int to, int w) {
            this.to = to;
            this.w = w;
        }
    }

    static class Node implements Comparable<Node> {
        int vertex;
        long dist;

        Node(int vertex, long dist) {
            this.vertex = vertex;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node other) {
            return Long.compare(this.dist, other.dist);
        }
    }

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

        int n = fs.nextInt();
        int m = fs.nextInt();

        ArrayList<Edge>[] g = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();

            int w = Math.max(u, v);

            g[u].add(new Edge(v, w));
            g[v].add(new Edge(u, w));
        }

        boolean[] prime = new boolean[n + 1];

        if (n >= 2) {
            Arrays.fill(prime, true);
            prime[0] = false;
            prime[1] = false;

            for (int i = 2; i * i <= n; i++) {
                if (prime[i]) {
                    for (int j = i * i; j <= n; j += i) {
                        prime[j] = false;
                    }
                }
            }
        }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);

        PriorityQueue<Node> pq = new PriorityQueue<>();

        // Multi-source Dijkstra
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                dist[i] = 0;
                pq.offer(new Node(i, 0));
            }
        }

        while (!pq.isEmpty()) {

            Node cur = pq.poll();

            int u = cur.vertex;
            long d = cur.dist;

            if (d != dist[u]) {
                continue;
            }

            for (Edge e : g[u]) {

                long nd = d + e.w;

                if (nd < dist[e.to]) {
                    dist[e.to] = nd;
                    pq.offer(new Node(e.to, nd));
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= n; i++) {
            if (i > 1) sb.append(' ');

            if (dist[i] == INF) {
                sb.append(-1);
            } else {
                sb.append(dist[i]);
            }
        }

        System.out.println(sb);
    }
}
