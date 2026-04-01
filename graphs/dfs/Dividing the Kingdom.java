import java.io.*;
import java.util.*;

public class Main {

    static final int MAXM = 200005;
    static int n;
    static ArrayList<Integer>[] graph = new ArrayList[MAXM];
    static int[][] dp = new int[MAXM][2];

    static void dfs(int src, int par) {
        dp[src][0] = 0;
        dp[src][1] = 0;

        boolean hasChild = false;

        for (int edge : graph[src]) {
            if (edge != par) {
                dfs(edge, src);
                dp[src][0] += Math.max(dp[edge][0], dp[edge][1]);
                hasChild = true;
            }
        }

        if (hasChild) {
            for (int edge : graph[src]) {
                if (edge != par) {
                    int ok = dp[src][0] + 1;

                    if (dp[edge][1] > dp[edge][0]) {
                        ok -= (dp[edge][1] - dp[edge][0]);
                    }

                    dp[src][1] = Math.max(dp[src][1], ok);
                }
            }
        }
    }

    static void solve(BufferedReader br) throws Exception {
        n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            String[] input = br.readLine().split(" ");
            int x = Integer.parseInt(input[0]) - 1;
            int y = Integer.parseInt(input[1]) - 1;

            graph[x].add(y);
            graph[y].add(x);
        }

        dfs(0, -1);

        int ans = Math.max(dp[0][0], dp[0][1]);
        System.out.println(ans);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        solve(br);
    }
}
