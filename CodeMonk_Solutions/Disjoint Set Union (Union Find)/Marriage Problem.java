// RUN TIME ERROR : 
import java.util.*;

public class Main {
    static int MAX = 2000005;
    static boolean[] vis = new boolean[MAX];
    static ArrayList<Integer>[] graph = new ArrayList[MAX];
    static long x, men, con, ans = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        x = sc.nextLong();
        long y = sc.nextLong();
        int q1 = sc.nextInt();

        // Initialize adjacency list
        for (int i = 0; i < MAX; i++) {
            graph[i] = new ArrayList<>();
        }

        // Edges between nodes in set 1
        for (int i = 0; i < q1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph[u].add(v);
            graph[v].add(u);
        }

        // Edges between nodes in set 2 (indices shifted by x)
        int q2 = sc.nextInt();
        for (int i = 0; i < q2; i++) {
            int u = sc.nextInt() + (int)x;
            int v = sc.nextInt() + (int)x;
            graph[u].add(v);
            graph[v].add(u);
        }

        // Edges between set 1 and set 2 (cross edges)
        int q3 = sc.nextInt();
        for (int i = 0; i < q3; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt() + (int)x;
            graph[u].add(v);
            graph[v].add(u);
        }

        // DFS to find connected components
        for (int i = 1; i <= x + y; i++) {
            if (!vis[i]) {
                men = 0;
                con = 0;
                dfs(i);
                ans += men * (y - (con - men));
            }
        }

        System.out.println(ans);
        sc.close();
    }

    static void dfs(int v) {
        vis[v] = true;
        if (v <= x) {
            men++;
        }
        con++;
        for (int neighbor : graph[v]) {
            if (!vis[neighbor]) {
                dfs(neighbor);
            }
        }
    }
}

// NO TLE & NO RUN TIME ERROR

import java.util.*;

public class Main {

    static class DisjointSet {
        int[] parent, rank, men, wen;

        public DisjointSet(int n, int t) {
            parent = new int[n];
            rank = new int[n];
            men = new int[n];
            wen = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
                if (i < t) {
                    men[i] = 1;
                } else {
                    wen[i] = 1;
                }
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);  // path compression
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int xid = find(x);
            int yid = find(y);

            if (xid == yid) return;

            if (rank[xid] < rank[yid]) {
                parent[xid] = yid;
                men[yid] += men[xid];
                wen[yid] += wen[xid];
            } else if (rank[xid] > rank[yid]) {
                parent[yid] = xid;
                men[xid] += men[yid];
                wen[xid] += wen[yid];
            } else {
                parent[yid] = xid;
                rank[xid]++;
                men[xid] += men[yid];
                wen[xid] += wen[yid];
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); // men
        int m = sc.nextInt(); // women
        int totalNodes = n + m;

        int q1 = sc.nextInt(); // connections within men
        DisjointSet ds = new DisjointSet(totalNodes, n);

        for (int i = 0; i < q1; i++) {
            int x = sc.nextInt() - 1;
            int y = sc.nextInt() - 1;
            ds.union(x, y);
        }

        int q2 = sc.nextInt(); // connections within women
        for (int i = 0; i < q2; i++) {
            int x = sc.nextInt() - 1 + n;
            int y = sc.nextInt() - 1 + n;
            ds.union(x, y);
        }

        int q3 = sc.nextInt(); // cross connections
        for (int i = 0; i < q3; i++) {
            int x = sc.nextInt() - 1;
            int y = sc.nextInt() - 1 + n;
            ds.union(x, y);
        }

        long total = 0;
        for (int i = 0; i < totalNodes; i++) {
            int root = ds.find(i);
            if (ds.men[root] > 0 || ds.wen[root] > 0) {
                total += (long) ds.men[root] * (m - ds.wen[root]);
                ds.men[root] = 0;
                ds.wen[root] = 0;
            }
        }

        System.out.println(total);
        sc.close();
    }
}

