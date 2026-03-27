import java.io.*;
import java.util.*;

public class Main {

    static final int N = 1000005;

    static ArrayList<Pair>[] v = new ArrayList[N];
    static int[] par = new int[N];
    static long[][] dp = new long[N][3];
    static long[][] sum = new long[N][3];

    static long c;

    static class Pair {
        int node;
        long w;
        Pair(int n, long w) {
            this.node = n;
            this.w = w;
        }
    }

    static void dfs(int u, int p) {
        par[u] = p;
        for (Pair e : v[u]) {
            if (e.node != p) {
                dfs(e.node, u);
            }
        }
    }

    static long foo(int u, int f) {
        if (dp[u][f] != -1) return dp[u][f];

        long res = (f == 0) ? 1 : 0;

        for (Pair e : v[u]) {
            if (e.node != par[u]) {
                res += foo(e.node, (f - 1 + 3) % 3);
            }
        }
        return dp[u][f] = res;
    }

    static long dfs_sum(int u, int f) {
        if (sum[u][f] != -1) return sum[u][f];

        long res = 0;

        for (Pair e : v[u]) {
            if (e.node != par[u]) {
                int f1 = (f - 1 + 3) % 3;
                res += e.w * dp[e.node][f1] + dfs_sum(e.node, f1);
            }
        }
        return sum[u][f] = res;
    }

    static void dfs_root(int u, int p) {
        long l = sum[u][1] + 2 * sum[u][2];
        c = Math.min(c, l);

        for (Pair e : v[u]) {
            if (e.node != p) {
                int node = e.node;
                long w = e.w;

                // remove child
                dp[u][0] -= dp[node][2];
                dp[u][1] -= dp[node][0];
                dp[u][2] -= dp[node][1];

                sum[u][0] -= w * dp[node][2] + sum[node][2];
                sum[u][1] -= w * dp[node][0] + sum[node][0];
                sum[u][2] -= w * dp[node][1] + sum[node][1];

                // add parent to child
                dp[node][0] += dp[u][2];
                dp[node][1] += dp[u][0];
                dp[node][2] += dp[u][1];

                sum[node][0] += w * dp[u][2] + sum[u][2];
                sum[node][1] += w * dp[u][0] + sum[u][0];
                sum[node][2] += w * dp[u][1] + sum[u][1];

                dfs_root(node, u);

                // rollback
                dp[node][0] -= dp[u][2];
                dp[node][1] -= dp[u][0];
                dp[node][2] -= dp[u][1];

                sum[node][0] -= w * dp[u][2] + sum[u][2];
                sum[node][1] -= w * dp[u][0] + sum[u][0];
                sum[node][2] -= w * dp[u][1] + sum[u][1];

                dp[u][0] += dp[node][2];
                dp[u][1] += dp[node][0];
                dp[u][2] += dp[node][1];

                sum[u][0] += w * dp[node][2] + sum[node][2];
                sum[u][1] += w * dp[node][0] + sum[node][0];
                sum[u][2] += w * dp[node][1] + sum[node][1];
            }
        }
    }

    static void solve(BufferedReader br) throws Exception {
        int n = Integer.parseInt(br.readLine());

        for (int i = 0; i <= n; i++) {
            v[i] = new ArrayList<>();
            par[i] = 0;
            Arrays.fill(dp[i], -1);
            Arrays.fill(sum[i], -1);
        }

        c = (long)2e18;

        for (int i = 0; i < n - 1; i++) {
            String[] parts = br.readLine().split(" ");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            long w = Long.parseLong(parts[2]);

            v[x].add(new Pair(y, w));
            v[y].add(new Pair(x, w));
        }

        dfs(1, 0);

        for (int i = 1; i <= n; i++) {
            for (int f = 0; f < 3; f++) {
                foo(i, f);
                dfs_sum(i, f);
            }
        }

        dfs_root(1, 0);

        System.out.println(c);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            solve(br);
        }
    }
}
