/*import java.io.*;
import java.util.*;

public class TestClass {
    static List<Integer>[] tree;
    static int[] A;
    static long maxSum = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        A = new int[n + 1];
        String[] parts = br.readLine().split(" ");
        for (int i = 1; i <= n; i++) {
            A[i] = Integer.parseInt(parts[i - 1]);
        }

        tree = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) tree[i] = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            String[] edge = br.readLine().split(" ");
            int u = Integer.parseInt(edge[0]);
            int v = Integer.parseInt(edge[1]);
            tree[u].add(v);
            tree[v].add(u);
        }

        List<Integer> path = new ArrayList<>();
        dfs(1, -1, path);
        System.out.println(maxSum);
    }

    static void dfs(int node, int parent, List<Integer> path) {
        path.add(A[node]);

        if (tree[node].size() == 1 && node != 1) {
            // Only process leaf paths
            processPath(path);
        }

        for (int child : tree[node]) {
            if (child != parent) {
                dfs(child, node, path);
            }
        }

        path.remove(path.size() - 1);
    }

    static void processPath(List<Integer> path) {
        int n = path.size();
        long[][] dp = new long[n][5]; // dp[i][k]: max sum of increasing seq of length k ending at i

        for (int i = 0; i < n; i++) {
            dp[i][1] = path.get(i);
            for (int j = 0; j < i; j++) {
                if (path.get(j) < path.get(i)) {
                    for (int k = 1; k < 4; k++) {
                        if (dp[j][k] > 0) {
                            dp[i][k + 1] = Math.max(dp[i][k + 1], dp[j][k] + path.get(i));
                        }
                    }
                }
            }
            maxSum = Math.max(maxSum, dp[i][4]);
        }
    }
}
*/


import java.io.*;
import java.util.*;

public class Main {
    static List<Integer>[] tree;
    static int[] A;
    static long maxSum = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        A = new int[n + 1];
        String[] costs = br.readLine().split(" ");
        for (int i = 1; i <= n; i++) {
            A[i] = Integer.parseInt(costs[i - 1]);
        }

        tree = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            String[] edge = br.readLine().split(" ");
            int u = Integer.parseInt(edge[0]);
            int v = Integer.parseInt(edge[1]);
            tree[u].add(v);
            tree[v].add(u);
        }

        List<Integer> path = new ArrayList<>();
        dfs(1, -1, path);

        System.out.println(maxSum);
    }

    static void dfs(int node, int parent, List<Integer> path) {
        path.add(A[node]);

        if (tree[node].size() == 1 && node != 1) {
            // Leaf node, process the path
            checkIncreasingSubsequences(path);
        }

        for (int neighbor : tree[node]) {
            if (neighbor != parent) {
                dfs(neighbor, node, path);
            }
        }

        path.remove(path.size() - 1);
    }

    static void checkIncreasingSubsequences(List<Integer> path) {
        int n = path.size();
        long[] dp1 = new long[n];
        long[] dp2 = new long[n];
        long[] dp3 = new long[n];
        long[] dp4 = new long[n];
        Arrays.fill(dp1, Long.MIN_VALUE);
        Arrays.fill(dp2, Long.MIN_VALUE);
        Arrays.fill(dp3, Long.MIN_VALUE);
        Arrays.fill(dp4, Long.MIN_VALUE);

        for (int i = 0; i < n; i++) {
            dp1[i] = path.get(i);
            for (int j = 0; j < i; j++) {
                if (path.get(j) < path.get(i)) {
                    dp2[i] = Math.max(dp2[i], dp1[j] + path.get(i));
                    dp3[i] = Math.max(dp3[i], dp2[j] + path.get(i));
                    dp4[i] = Math.max(dp4[i], dp3[j] + path.get(i));
                }
            }
            maxSum = Math.max(maxSum, dp4[i]);
        }
    }
}
