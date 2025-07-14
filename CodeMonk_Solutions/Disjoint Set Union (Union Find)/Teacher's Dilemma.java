import java.util.Scanner;

public class Main {
    static int[] parent = new int[100001];
    static int[] size = new int[100001];
    static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        // Initialization
        for (int i = 0; i <= 100000; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        // Union operations
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            if (find(u) != find(v)) {
                unite(u, v);
            }
        }

        // Calculate product of sizes of connected components
        long mul = 1;
        for (int i = 1; i <= n; i++) {
            if (find(i) == i) {
                mul = (mul * size[i]) % MOD;
            }
        }

        System.out.println(mul);
        sc.close();
    }

    static int find(int u) {
        if (u != parent[u]) {
            parent[u] = find(parent[u]); // Path compression
        }
        return parent[u];
    }

    static void unite(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) return;

        // Union by size
        if (size[rootA] < size[rootB]) {
            int temp = rootA;
            rootA = rootB;
            rootB = temp;
        }

        parent[rootB] = rootA;
        size[rootA] += size[rootB];
    }
}
