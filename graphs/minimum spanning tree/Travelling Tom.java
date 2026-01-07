import java.io.*;
import java.util.*;

public class Main {

    static class Edge {
        int u, v;
        BitSet req;
        Edge(int u, int v, BitSet req) {
            this.u = u;
            this.v = v;
            this.req = req;
        }
    }

    static class DSU {
        int[] parent, rank;
        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }
        void union(int a, int b) {
            a = find(a);
            b = find(b);
            if (a != b) {
                if (rank[a] < rank[b]) parent[a] = b;
                else if (rank[a] > rank[b]) parent[b] = a;
                else {
                    parent[b] = a;
                    rank[a]++;
                }
            }
        }
    }

    static boolean connected(int n, List<Edge> edges, BitSet have) {
        DSU dsu = new DSU(n);
        for (Edge e : edges) {
            BitSet tmp = (BitSet) e.req.clone();
            tmp.andNot(have);
            if (tmp.isEmpty()) {
                dsu.union(e.u, e.v);
            }
        }
        int root = dsu.find(0);
        for (int i = 1; i < n; i++)
            if (dsu.find(i) != root)
                return false;
        return true;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        long[] cost = new long[k];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++)
            cost[i] = Long.parseLong(st.nextToken());

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            int l = Integer.parseInt(st.nextToken());

            BitSet bs = new BitSet(k);
            for (int j = 0; j < l; j++) {
                int t = Integer.parseInt(st.nextToken()) - 1;
                bs.set(t);
            }
            edges.add(new Edge(u, v, bs));
        }

        BitSet have = new BitSet(k);
        have.set(0, k); // start assuming all tokens bought

        for (int i = k - 1; i >= 0; i--) {
            have.clear(i); // try removing token i
            if (!connected(n, edges, have)) {
                have.set(i); // must keep it
            }
        }

        long answer = 0;
        for (int i = 0; i < k; i++)
            if (have.get(i))
                answer += cost[i];

        if (!connected(n, edges, have))
            System.out.println(-1);
        else
            System.out.println(answer);
    }
}
