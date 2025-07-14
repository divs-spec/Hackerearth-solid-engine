import java.util.*;

public class Main {
    static class Solution {
        List<List<Integer>> graph, trees;
        int[] depths, indices, inTime, low;
        int[][] parents;
        int root;
        int timer;
        int LOG;

        public void init(int n, List<int[]> edges) {
            graph = new ArrayList<>();
            trees = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<>());
                trees.add(new ArrayList<>());
            }

            for (int[] edge : edges) {
                int u = edge[0], v = edge[1];
                graph.get(u).add(v);
                graph.get(v).add(u);
            }

            depths = new int[n];
            indices = new int[n];
            Arrays.fill(indices, -1);
            inTime = new int[n];
            Arrays.fill(inTime, -1);
            low = new int[n];
            Arrays.fill(low, Integer.MAX_VALUE);

            LOG = 1;
            while ((1 << LOG) <= n) LOG++;
            parents = new int[n][LOG];
            for (int[] row : parents)
                Arrays.fill(row, -1);

            root = 0;
            timer = 0;
            List<int[]> bridges = new ArrayList<>();
            dfs(root, -1, bridges);

            for (int[] bridge : bridges) {
                int u = bridge[0], v = bridge[1];
                trees.get(u).add(v);
                trees.get(v).add(u);
            }

            for (int i = 0; i < n; i++) {
                if (indices[i] == -1) {
                    dfs2(i, -1, 0, i);
                }
            }
        }

        public int query(int u, int v) {
            if (indices[u] != indices[v]) return -1;
            int p = lca(u, v);
            return depths[u] + depths[v] - 2 * depths[p];
        }

        private void dfs(int u, int p, List<int[]> bridges) {
            inTime[u] = low[u] = ++timer;
            for (int v : graph.get(u)) {
                if (v == p) continue;
                if (inTime[v] >= 0) {
                    low[u] = Math.min(low[u], inTime[v]);
                } else {
                    dfs(v, u, bridges);
                    low[u] = Math.min(low[u], low[v]);
                    if (low[v] > inTime[u]) {
                        bridges.add(new int[]{u, v});
                    }
                }
            }
        }

        private void dfs2(int u, int p, int d, int id) {
            parents[u][0] = p;
            depths[u] = d;
            indices[u] = id;

            for (int i = 1; i < LOG; i++) {
                int prev = parents[u][i - 1];
                if (prev >= 0) {
                    parents[u][i] = parents[prev][i - 1];
                }
            }

            for (int v : trees.get(u)) {
                if (v == p) continue;
                dfs2(v, u, d + 1, id);
            }
        }

        private int lca(int u, int v) {
            if (u == -1 || v == -1) return u == -1 ? v : u;
            if (u == v) return u;

            if (depths[u] > depths[v]) {
                int temp = u; u = v; v = temp;
            }

            for (int i = LOG - 1; i >= 0; i--) {
                if (parents[v][i] >= 0 && depths[parents[v][i]] >= depths[u]) {
                    v = parents[v][i];
                }
            }

            if (u == v) return u;

            for (int i = LOG - 1; i >= 0; i--) {
                if (parents[u][i] != parents[v][i]) {
                    u = parents[u][i];
                    v = parents[v][i];
                }
            }

            return parents[u][0];
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        List<int[]> edges = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            edges.add(new int[]{u, v});
        }

        Solution sol = new Solution();
        sol.init(n, edges);

        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            System.out.println(sol.query(u, v));
        }

        sc.close();
    }
}
