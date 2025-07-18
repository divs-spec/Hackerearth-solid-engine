import java.io.*;
import java.util.*;

public class Main {

    static int[] parent = new int[1 << 20];
    static int[] a = new int[1 << 20];
    static int[] b = new int[1 << 20];
    static int[] c = new int[1 << 20];
    static int ans;
    static List<int[]> edges = new ArrayList<>();

    static int find(int x) {
        if (x == parent[x])
            return x;
        return parent[x] = find(parent[x]);
    }

    static void union(int x, int y) {
        parent[x] = y;
    }

    public static void main(String[] args) throws IOException {
        // Fast input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tests = Integer.parseInt(br.readLine());

        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            edges.clear();

            for (int i = 1; i <= m; i++) {
                st = new StringTokenizer(br.readLine());
                a[i] = Integer.parseInt(st.nextToken());
                b[i] = Integer.parseInt(st.nextToken());
                c[i] = Integer.parseInt(st.nextToken());
                edges.add(new int[]{c[i], i}); // store (weight, edge_id)
            }

            // Sort in decreasing order for max spanning tree
            edges.sort((x, y) -> Integer.compare(y[0], x[0]));

            // DSU init
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
            }

            ans = 0;
            for (int[] edge : edges) {
                int id = edge[1];
                int u = find(a[id]);
                int v = find(b[id]);

                if (u != v) {
                    ans += c[id];
                    union(u, v);
                }
            }

            System.out.println(ans);
        }
    }
}

/*
Kruskal's algorithm in reverse (maximum spanning tree) using DSU (Disjoint Set Union) with path compression, applied over multiple test cases.

✅ Notes:
Disjoint Set Union (DSU) is used with path compression for efficiency.

edges list holds (weight, index) pairs so we can sort by weights and still access the original input.

Used BufferedReader and StringTokenizer for fast input (essential in large test cases).

Arrays are declared globally just like in your C++ version (1 << 20 ~ 1 million space).

💡 Optimization Tips:
If you face TLE even with this, switch to a FastReader class using BufferedInputStream for extreme input speed.

Use int[] arrays instead of ArrayList<Integer> to avoid boxing overhead.
*/
