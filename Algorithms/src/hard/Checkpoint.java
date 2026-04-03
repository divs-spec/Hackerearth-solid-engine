import java.io.*;
import java.util.*;

public class Main {

    static final long INF = (long) 1e12;

    static int n, m, p;
    static List<Integer>[] graph;

    static int[] leftSide;
    static int[] rightSide;
    static long[] dist;

    static boolean bfs() {
        Queue<Integer> q = new LinkedList<>();

        for (int i = 1; i <= n; i++) {
            if (leftSide[i] == 0) {
                dist[i] = 0;
                q.offer(i);
            } else {
                dist[i] = INF;
            }
        }

        dist[0] = INF;

        while (!q.isEmpty()) {
            int u = q.poll();

            if (dist[u] < dist[0]) {
                for (int v : graph[u]) {
                    if (dist[rightSide[v]] == INF) {
                        dist[rightSide[v]] = dist[u] + 1;
                        q.offer(rightSide[v]);
                    }
                }
            }
        }

        return dist[0] != INF;
    }

    static boolean dfs(int u) {
        if (u == 0) return true;

        for (int v : graph[u]) {
            if (dist[rightSide[v]] == dist[u] + 1) {
                if (dfs(rightSide[v])) {
                    rightSide[v] = u;
                    leftSide[u] = v;
                    return true;
                }
            }
        }

        dist[u] = INF;
        return false;
    }

    static void solve(FastScanner fs) throws IOException {
        n = fs.nextInt();
        m = fs.nextInt();
        p = fs.nextInt();

        graph = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        leftSide = new int[n + 1];
        rightSide = new int[m + 1];
        dist = new long[n + 1];

        for (int i = 0; i < p; i++) {
            int x = fs.nextInt();
            int y = fs.nextInt();
            graph[x].add(y);
        }

        int matching = 0;

        while (bfs()) {
            for (int i = 1; i <= n; i++) {
                if (leftSide[i] == 0 && dfs(i)) {
                    matching++;
                }
            }
        }

        System.out.println(matching);
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        solve(fs);
    }

    // Fast input (important for CP)
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
