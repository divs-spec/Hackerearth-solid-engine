import java.io.*;
import java.util.*;

public class Main {

    static final long INF = (long)1e18;

    static List<List<long[]>> adj;
    static long[] d;
    static int[] p;

    static void dijkstra(int s) {
        Arrays.fill(d, INF);
        Arrays.fill(p, -1);

        d[s] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.add(new long[]{0, s});

        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            long dist = top[0];
            int v = (int)top[1];

            if (dist != d[v]) continue;

            for (long[] edge : adj.get(v)) {
                int to = (int)edge[0];
                long len = edge[1];

                if (d[v] + len < d[to]) {
                    d[to] = d[v] + len;
                    p[to] = v;
                    pq.add(new long[]{d[to], to});
                }
            }
        }
    }

    static void solve(BufferedReader br) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        long[] a = new long[n + 1];
        long[] b = new long[n + 1];
        long[] d1 = new long[n + 1];
        long[] d2 = new long[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) a[i] = Long.parseLong(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) b[i] = Long.parseLong(st.nextToken());

        int[][] edge1 = new int[m][3];
        int[][] edge2 = new int[m][3];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            edge1[i][0] = Integer.parseInt(st.nextToken());
            edge1[i][1] = Integer.parseInt(st.nextToken());
            edge1[i][2] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            edge2[i][0] = Integer.parseInt(st.nextToken());
            edge2[i][1] = Integer.parseInt(st.nextToken());
            edge2[i][2] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        int u = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());

        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        d = new long[n + 1];
        p = new int[n + 1];

        // First graph
        for (int i = 0; i <= n; i++) adj.get(i).clear();
        for (int i = 0; i < m; i++) {
            int x = edge1[i][0], y = edge1[i][1], z = edge1[i][2];
            adj.get(x).add(new long[]{y, z});
            adj.get(y).add(new long[]{x, z});
        }

        dijkstra(u);
        for (int i = 1; i <= n; i++) d1[i] = d[i];

        // Second graph
        for (int i = 0; i <= n; i++) adj.get(i).clear();
        for (int i = 0; i < m; i++) {
            int x = edge2[i][0], y = edge2[i][1], z = edge2[i][2];
            adj.get(x).add(new long[]{y, z});
            adj.get(y).add(new long[]{x, z});
        }

        dijkstra(v);
        for (int i = 1; i <= n; i++) d2[i] = d[i];

        long ans = (long)4e18;

        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j += i) {
                if (d1[i] < INF && d2[j] < INF) {
                    long val = d1[i] + d2[j] + a[i] * b[j];
                    ans = Math.min(ans, val);
                }
            }
        }

        if (ans == (long)4e18) ans = -1;

        System.out.println(ans);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            solve(br);
        }
    }
}
