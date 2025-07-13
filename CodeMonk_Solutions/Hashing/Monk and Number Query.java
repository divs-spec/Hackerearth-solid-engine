import java.util.*;

public class Main {
    static int MAX = 200005;
    static long[] level = new long[MAX];
    static int[] parent = new int[MAX];
    static int[] a = new int[MAX];
    static long[] val = new long[MAX];
    static int n, k, q;
    static long maxLevel = 0;
    static List<Integer>[] edge = new ArrayList[MAX];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        k = sc.nextInt();
        q = sc.nextInt();

        for (int i = 0; i < MAX; i++) {
            edge[i] = new ArrayList<>();
        }

        for (int i = 1; i <= n; i++) {
            a[i] = sc.nextInt();
        }

        for (int i = 1; i < n; i++) {
            int x = sc.nextInt();
            parent[i + 1] = x;
            edge[x].add(i + 1);
        }

        parent[1] = 0;
        bfs(1);

        // Reverse level to match C++ logic
        for (int i = 1; i <= n; i++) {
            level[i] = maxLevel - level[i];
        }

        while (q-- > 0) {
            int x = sc.nextInt();
            int y = sc.nextInt();

            if (x == 1) {
                if (val[y] % k == 0) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            } else {
                int temp = parent[x];
                long power = poww(10, level[temp] - level[y]);
                long result = (val[y] - (val[temp] * power) % k + k) % k;
                if (result == 0) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            }
        }

        sc.close();
    }

    static void bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        level[start] = 0;
        val[start] = a[start];

        while (!queue.isEmpty()) {
            int p = queue.poll();
            for (int it : edge[p]) {
                level[it] = level[p] + 1;
                maxLevel = Math.max(maxLevel, level[it]);
                queue.add(it);
                val[it] = (val[p] * 10 + a[it]) % k;
            }
        }
    }

    static long poww(long base, long exp) {
        long res = 1;
        base %= k;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                res = (res * base) % k;
            }
            base = (base * base) % k;
            exp >>= 1;
        }
        return res;
    }
}

/*

import java.util.*;

public class Main {
    static final int MAX = 200005;
    static long[] level = new long[MAX];
    static int[] parent = new int[MAX];
    static int[] a = new int[MAX];
    static long[] val = new long[MAX];
    static long[] power = new long[MAX];
    static int n, k, q;
    static long maxLevel = 0;
    static List<Integer>[] edge = new ArrayList[MAX];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        k = sc.nextInt();
        q = sc.nextInt();

        for (int i = 0; i < MAX; i++) {
            edge[i] = new ArrayList<>();
        }

        for (int i = 1; i <= n; i++) {
            a[i] = sc.nextInt();
        }

        for (int i = 1; i < n; i++) {
            int x = sc.nextInt();
            parent[i + 1] = x;
            edge[x].add(i + 1);
        }

        parent[1] = 0;

        precomputePowers(); // 🔁 Precompute 10^i % k

        bfs(1); // 🌳 Build tree values and levels

        for (int i = 1; i <= n; i++) {
            level[i] = maxLevel - level[i]; // Flip levels for segment positioning
        }

        while (q-- > 0) {
            int x = sc.nextInt();
            int y = sc.nextInt();

            if (x == 1) {
                System.out.println(val[y] % k == 0 ? "YES" : "NO");
            } else {
                int temp = parent[x];
                int diff = (int) (level[temp] - level[y]);
                long modPart = (val[temp] * power[diff]) % k;
                long result = (val[y] - modPart + k) % k;
                System.out.println(result == 0 ? "YES" : "NO");
            }
        }
        sc.close();
    }

    static void bfs(int root) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(root);
        level[root] = 0;
        val[root] = a[root];

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : edge[u]) {
                level[v] = level[u] + 1;
                maxLevel = Math.max(maxLevel, level[v]);
                val[v] = (val[u] * 10 + a[v]) % k;
                queue.add(v);
            }
        }
    }

    static void precomputePowers() {
        power[0] = 1;
        for (int i = 1; i < MAX; i++) {
            power[i] = (power[i - 1] * 10) % k;
        }
    }
}
*/
