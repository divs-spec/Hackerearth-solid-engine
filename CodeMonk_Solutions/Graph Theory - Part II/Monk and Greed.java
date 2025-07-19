import java.io.*;
import java.util.*;

public class Main {
    static int[] parent;
    static long[] sum;

    // DSU: Find root with path compression
    static int find(int v) {
        if (parent[v] < 0) return v;
        return parent[v] = find(parent[v]);
    }

    // DSU: Union by size + component sum merge
    static void merge(int x, int y) {
        x = find(x);
        y = find(y);
        if (x == y) return;
        if (parent[y] < parent[x]) {
            int temp = x;
            x = y;
            y = temp;
        }
        parent[x] += parent[y];
        sum[x] += sum[y];
        parent[y] = x;
    }

    public static void main(String[] args) throws IOException {
        // Fast I/O
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n, m;
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        Arrays.fill(parent, -1);
        sum = new long[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            sum[i] = Long.parseLong(st.nextToken());
        }

        // Edge list: (weight, u, v)
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            long w = Long.parseLong(st.nextToken());
            edges.add(new Edge(w, u, v));
        }

        Collections.sort(edges, (a, b) -> Long.compare(b.w, a.w)); // Sort descending by weight

        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            merge(e.u, e.v);
            if (find(a) == find(b)) {
                // Merge rest of edges with the same weight
                long w = e.w;
                for (int j = i + 1; j < edges.size(); j++) {
                    Edge next = edges.get(j);
                    if (next.w != w) break;
                    merge(next.u, next.v);
                }
                int rep = find(a);
                System.out.println(w + " " + sum[rep]);
                break;
            }
        }
    }

    static class Edge {
        long w;
        int u, v;
        Edge(long w, int u, int v) {
            this.w = w;
            this.u = u;
            this.v = v;
        }
    }
}
