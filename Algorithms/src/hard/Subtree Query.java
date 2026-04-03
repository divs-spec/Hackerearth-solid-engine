import java.io.*;
import java.util.*;

public class Main {

    static final int N = 100005;

    static List<Integer>[] graph = new ArrayList[N];
    static int[] a = new int[N];
    static int[] pos = new int[N];
    static int[] sub = new int[N];
    static List<Integer> order = new ArrayList<>();

    // -------- SEGMENT TREE --------
    static class SegTree {
        int n;
        int[] tree;
        int[][] lazy;

        SegTree(int[] arr) {
            this.n = arr.length;
            tree = new int[4 * n];
            lazy = new int[4 * n][3];
            build(1, 0, n - 1, arr);
        }

        void build(int node, int start, int end, int[] arr) {
            if (start == end) {
                tree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                build(2 * node, start, mid, arr);
                build(2 * node + 1, mid + 1, end, arr);
                tree[node] = tree[2 * node] + tree[2 * node + 1];
            }
        }

        void push(int node, int start, int end) {
            if (lazy[node][2] == 1) {
                tree[node] = (end - start + 1) * lazy[node][1];
            }
            if (lazy[node][0] == 1) {
                tree[node] = (end - start + 1) - tree[node];
            }

            if (start != end) {
                if (lazy[node][2] == 1) {
                    lazy[2 * node] = lazy[node].clone();
                    lazy[2 * node + 1] = lazy[node].clone();
                } else {
                    lazy[2 * node][0] ^= lazy[node][0];
                    lazy[2 * node + 1][0] ^= lazy[node][0];
                }
            }

            lazy[node][0] = 0;
            lazy[node][2] = 0;
        }

        void flipRange(int node, int start, int end, int l, int r) {
            if (start > r || end < l) return;

            push(node, start, end);

            if (start >= l && end <= r) {
                lazy[node][0] = 1;
                push(node, start, end);
                return;
            }

            int mid = (start + end) / 2;
            flipRange(2 * node, start, mid, l, r);
            flipRange(2 * node + 1, mid + 1, end, l, r);

            tree[node] = tree[2 * node] + tree[2 * node + 1];
        }

        int getSum(int node, int start, int end, int l, int r) {
            if (start > r || end < l) return 0;

            push(node, start, end);

            if (start >= l && end <= r) {
                return tree[node];
            }

            int mid = (start + end) / 2;
            return getSum(2 * node, start, mid, l, r)
                    + getSum(2 * node + 1, mid + 1, end, l, r);
        }

        void flipRange(int l, int r) {
            flipRange(1, 0, n - 1, l, r);
        }

        int getSum(int l, int r) {
            return getSum(1, 0, n - 1, l, r);
        }
    }

    // -------- DFS --------
    static void dfs(int u, int parent) {
        sub[u] = 1;
        order.add(u);

        for (int v : graph[u]) {
            if (v != parent) {
                dfs(v, u);
                sub[u] += sub[v];
            }
        }
    }

    // -------- SOLVE --------
    static void solve(FastScanner fs) throws Exception {
        int n = fs.nextInt();

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            int x = fs.nextInt();
            int y = fs.nextInt();
            graph[x].add(y);
            graph[y].add(x);
        }

        for (int i = 1; i <= n; i++) {
            a[i] = fs.nextInt();
        }

        dfs(1, 0);

        for (int i = 0; i < n; i++) {
            pos[order.get(i)] = i;
        }

        // 20 segment trees (bitwise)
        SegTree[] seg = new SegTree[20];

        for (int i = 0; i < 20; i++) {
            int[] arr = new int[n];

            for (int j = 0; j < n; j++) {
                if ((a[order.get(j)] & (1 << i)) != 0) {
                    arr[j] = 1;
                }
            }

            seg[i] = new SegTree(arr);
        }

        int q = fs.nextInt();

        while (q-- > 0) {
            int t = fs.nextInt();

            if (t == 1) {
                int node = fs.nextInt();

                int l = pos[node];
                int r = l + sub[node] - 1;

                long res = 0;

                for (int i = 0; i < 20; i++) {
                    long sum = seg[i].getSum(l, r);
                    res += sum * (1L << i);
                }

                System.out.println(res);
            } else {
                int node = fs.nextInt();
                int x = fs.nextInt();

                int l = pos[node];
                int r = l + sub[node] - 1;

                for (int i = 0; i < 20; i++) {
                    if ((x & (1 << i)) != 0) {
                        seg[i].flipRange(l, r);
                    }
                }
            }
        }
    }

    // -------- FAST SCANNER --------
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }

        String next() throws Exception {
            while (st == null || !st.hasMoreTokens()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        int nextInt() throws Exception {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        solve(fs);
    }
}
