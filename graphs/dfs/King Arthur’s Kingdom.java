import java.io.*;
import java.util.*;

public class Main {

    static int N, K;
    static long[] val;
    static List<Integer>[] adj;

    static boolean isConnected(int mask) {
        int start = -1;
        for (int i = 0; i < N; i++) {
            if ((mask & (1 << i)) != 0) {
                start = i;
                break;
            }
        }

        boolean[] visited = new boolean[N];
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        visited[start] = true;
        int count = 1;

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj[u]) {
                if (!visited[v] && (mask & (1 << v)) != 0) {
                    visited[v] = true;
                    q.add(v);
                    count++;
                }
            }
        }

        return count == Integer.bitCount(mask);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        for (int caseNo = 1; caseNo <= T; caseNo++) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            val = new long[N];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) val[i] = Long.parseLong(st.nextToken());

            adj = new ArrayList[N];
            for (int i = 0; i < N; i++) adj[i] = new ArrayList<>();

            for (int i = 0; i < N - 1; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken()) - 1;
                int v = Integer.parseInt(st.nextToken()) - 1;
                adj[u].add(v);
                adj[v].add(u);
            }

            long answer = 0;
            int total = 1 << N;

            for (int mask = 1; mask < total; mask++) {
                HashSet<Long> distinct = new HashSet<>();
                for (int i = 0; i < N; i++) {
                    if ((mask & (1 << i)) != 0) {
                        distinct.add(val[i]);
                        if (distinct.size() > K) break;
                    }
                }
                if (distinct.size() > K) continue;

                if (isConnected(mask)) {
                    answer++;
                }
            }

            out.append("Case ").append(caseNo).append(": ").append(answer).append('\n');
        }

        System.out.print(out.toString());
    }
}
