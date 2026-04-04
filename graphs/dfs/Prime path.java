import java.io.*;
import java.util.*;

public class Main {

    static final int N = 100005;

    static ArrayList<Integer>[] v = new ArrayList[N];
    static int[] prime = new int[N];
    static int[] par = new int[N];
    static int[] sub = new int[N];
    static long[][] dp = new long[N][2];

    static void dfs(int u, int p) {
        par[u] = p;
        sub[u] = 1;

        for (int i : v[u]) {
            if (i != p) {
                dfs(i, u);
                sub[u] += sub[i];
            }
        }
    }

    static long foo(int u, int f) {
        if (prime[u] > f || f < 0) {
            return dp[u][f] = 0;
        }

        if (dp[u][f] != -1) {
            return dp[u][f];
        }

        long c = 1;

        for (int i : v[u]) {
            if (i != par[u]) {
                c += foo(i, f - prime[u]);
            }
        }

        return dp[u][f] = c;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // Initialize adjacency list
        for (int i = 0; i < N; i++) {
            v[i] = new ArrayList<>();
        }

        // Sieve
        for (int i = 2; i < N; i++) {
            prime[i] = 1;
        }

        for (int i = 2; i < N; i++) {
            for (int j = 2 * i; j < N; j += i) {
                prime[j] = 0;
            }
        }

        int n = Integer.parseInt(br.readLine());

        // Reset dp
        for (int i = 0; i <= n; i++) {
            dp[i][0] = dp[i][1] = -1;
        }

        // Read edges
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            v[x].add(y);
            v[y].add(x);
        }

        dfs(1, 0);

        // Compute dp
        for (int i = 1; i <= n; i++) {
            foo(i, 0);
            foo(i, 1);
        }

        for (int i = 1; i <= n; i++) {
            dp[i][1] = dp[i][1] - dp[i][0];
        }

        long c = 0;

        for (int i = 1; i <= n; i++) {
            c += dp[i][1] - prime[i];

            long zero = 0, one = 0;
            long zero_sq = 0, one_sq = 0;

            for (int j : v[i]) {
                if (j == par[i]) continue;

                zero += dp[j][0];
                zero_sq += dp[j][0] * dp[j][0];

                one += dp[j][1];
                one_sq += dp[j][1] * dp[j][1];
            }

            if (prime[i] == 0) {
                for (int j : v[i]) {
                    if (j == par[i]) continue;

                    c += dp[j][0] * (one - dp[j][1]);
                }
            } else {
                c += (zero * zero - zero_sq) / 2;
            }
        }

        System.out.println(c);
    }
}
