// Disjoint Set Union (DSU), map-based merging, and offline edge processing with rollback.

import java.util.*;

public class Main {
    static int[] parent = new int[100011];
    static int[] size = new int[100011];
    static int[] A = new int[100011];
    static long[] pt = new long[100011];
    static long ans = 0;
    static int D;
    static Map<Long, Long>[] map = new HashMap[100011];

    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    static void join(int u, int v) {
        u = find(u);
        v = find(v);
        if (u == v) return;
        if (size[u] < size[v]) {
            int tmp = u;
            u = v;
            v = tmp;
        }
        parent[v] = u;
        size[u] += size[v];

        for (Map.Entry<Long, Long> entry : map[v].entrySet()) {
            long key = entry.getKey();
            long val = entry.getValue();

            for (int j = -D; j <= D; j++) {
                long neighbor = key + j;
                if (map[u].containsKey(neighbor)) {
                    ans += val * map[u].get(neighbor);
                }
            }
        }

        for (Map.Entry<Long, Long> entry : map[v].entrySet()) {
            long key = entry.getKey();
            long val = entry.getValue();
            map[u].put(key, map[u].getOrDefault(key, 0L) + val);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        D = sc.nextInt();

        for (int i = 1; i <= N; i++) {
            A[i] = sc.nextInt();
            parent[i] = i;
            size[i] = 1;
            map[i] = new HashMap<>();
            map[i].put((long) A[i], 1L);
        }

        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < N - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            edges.add(new int[]{u, v});
        }

        int[] s = new int[N - 1];
        for (int i = 0; i < N - 1; i++) {
            s[i] = sc.nextInt() - 1;
        }

        for (int i = s.length - 1; i >= 0; i--) {
            pt[i] = ans;
            int[] edge = edges.get(s[i]);
            join(edge[0], edge[1]);
        }

        for (int i = 0; i < N - 1; i++) {
            System.out.println(pt[i]);
        }
    }
}
