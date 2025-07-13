import java.util.*;

public class Main {
    static final int MAXN = 100005;
    static boolean[] visited = new boolean[MAXN];
    static ArrayList<Integer>[] g = new ArrayList[MAXN];
    static int[] a = new int[MAXN];
    static int[][] ans = new int[MAXN][33];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int A = sc.nextInt();

        for (int i = 0; i < MAXN; i++) {
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            g[u].add(v);
            g[v].add(u);
        }

        for (int i = 1; i <= n; i++) {
            a[i] = sc.nextInt();
        }

        dfs(A);

        int ret = 0;
        for (int i = 0; i < 32; i++) {
            ret += ans[A][i];
        }

        System.out.println(ret);
        sc.close();
    }

    static void dfs(int u) {
        visited[u] = true;

        for (int v : g[u]) {
            if (!visited[v]) {
                dfs(v);
                for (int j = 0; j < 32; j++) {
                    ans[u][j] = Math.max(ans[u][j], ans[v][j]);
                }
            }
        }

        for (int i = 0; i < 32; i++) {
            if ((a[u] & (1 << i)) != 0) {
                if (ans[u][i] % 2 == 0) ans[u][i]++;
            } else {
                if (ans[u][i] % 2 == 1) ans[u][i]++;
            }
        }
    }
}
