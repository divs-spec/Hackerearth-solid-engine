import java.io.*;
import java.util.*;

public class Main {
    static List<Integer>[] adj;
    static int[] vis;
    static boolean isBipartite;
    static int count0, count1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            String[] parts = br.readLine().split(" ");
            int a = Integer.parseInt(parts[0]); // number of nodes
            int b = Integer.parseInt(parts[1]); // number of edges

            vis = new int[a + 1];
            Arrays.fill(vis, -1);

            adj = new ArrayList[a + 1];
            for (int i = 0; i <= a; i++) {
                adj[i] = new ArrayList<>();
            }

            for (int i = 0; i < b; i++) {
                parts = br.readLine().split(" ");
                int u = Integer.parseInt(parts[0]);
                int v = Integer.parseInt(parts[1]);
                adj[u].add(v);
                adj[v].add(u);
            }

            int res = 0;
            isBipartite = true;

            for (int i = 1; i <= a; i++) {
                if (vis[i] == -1) {
                    res += bfs(i);
                    if (!isBipartite) break;
                }
            }

            if (isBipartite) {
                System.out.println(res);
            } else {
                System.out.println("NO");
            }
        }
    }

    static int bfs(int start) {
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        vis[start] = 1;
        count0 = 0;
        count1 = 1;

        while (!q.isEmpty()) {
            int node = q.poll();
            for (int neighbor : adj[node]) {
                if (vis[neighbor] == -1) {
                    q.add(neighbor);
                    vis[neighbor] = 1 - vis[node]; // flip color
                    if (vis[neighbor] == 0)
                        count0++;
                    else
                        count1++;
                } else if (vis[neighbor] == vis[node]) {
                    isBipartite = false;
                    return 0;
                }
            }
        }
        return Math.max(count0, count1);
    }
}
