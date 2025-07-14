import java.util.Scanner;

public class Main {
    static int[] parent = new int[100001];
    static int[] size = new int[100001];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        // Initialize DSU arrays
        for (int i = 0; i <= 100000; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        // Process edges and union the sets
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            if (find(u) != find(v)) {
                unite(u, v);
            }
        }

        // Output size of component for each node (excluding itself)
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(size[find(i)] - 1).append(" ");
        }

        System.out.println(sb);
        sc.close();
    }

    // DSU find with path compression
    static int find(int x) {
        if (x != parent[x]) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // DSU union by size
    static void unite(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) return;

        if (size[rootA] < size[rootB]) {
            int temp = rootA;
            rootA = rootB;
            rootB = temp;
        }

        parent[rootB] = rootA;
        size[rootA] += size[rootB];
    }
}
