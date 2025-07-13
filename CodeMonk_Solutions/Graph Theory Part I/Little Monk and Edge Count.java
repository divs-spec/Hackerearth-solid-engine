import java.util.*;

public class Main {
    static final int MAX = 100005;
    static boolean[] vis = new boolean[MAX];
    static int[] parent = new int[MAX];
    static int[] sz = new int[MAX];
    static ArrayList<Integer>[] adj = new ArrayList[MAX];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int q = sc.nextInt();

        int[] a = new int[MAX];
        int[] b = new int[MAX];

        for (int i = 0; i < MAX; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            a[i] = sc.nextInt();
            b[i] = sc.nextInt();
            adj[a[i]].add(b[i]);
            adj[b[i]].add(a[i]);
        }

        parent[1] = -1;
        for (int i = 1; i <= n; i++) {
            sz[i] = 1;
        }

        dfs(1);

        while (q-- > 0) {
            int x = sc.nextInt();
            long ans;
            if (parent[a[x]] == b[x]) {
                ans = (long) sz[a[x]] * (n - sz[a[x]]);
            } else {
                ans = (long) sz[b[x]] * (n - sz[b[x]]);
            }
            System.out.println(ans);
        }

        sc.close();
    }

    static void dfs(int x) {
        vis[x] = true;
        for (int y : adj[x]) {
            if (!vis[y]) {
                parent[y] = x;
                dfs(y);
                sz[x] += sz[y];
            }
        }
    }
}
