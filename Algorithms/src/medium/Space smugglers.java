import java.util.*;

class iPair {
    int first, second;
    public iPair(int first, int second) {
        this.first = first;
        this.second = second;
    }
}

class Graph {
    int V;
    List<List<iPair>> adj;

    public Graph(int V) {
        this.V = V;
        adj = new ArrayList<>();
        for (int i = 0; i < V; i++)
            adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new iPair(v, w)); // corrected to u -> v
    }

    public void shortestPath(int src, List<Integer> dist) {
        PriorityQueue<iPair> pq = new PriorityQueue<>(Comparator.comparingInt(ip -> ip.first));
        pq.add(new iPair(0, src));
        dist.set(src, 0);

        while (!pq.isEmpty()) {
            iPair curr = pq.poll();
            int u = curr.second;
            if (curr.first > dist.get(u)) continue; // skip if outdated

            for (iPair edge : adj.get(u)) {
                int v = edge.first;
                int weight = edge.second;
                if (dist.get(v) > dist.get(u) + weight) {
                    dist.set(v, dist.get(u) + weight);
                    pq.add(new iPair(dist.get(v), v));
                }
            }
        }
    }
}

public class Main {
    static final int INF = Integer.MAX_VALUE;

    public static int minPath(int V, int s, int t, Graph g, Graph r) {
        List<Integer> dist_s_to_all = new ArrayList<>(Collections.nCopies(V, INF));
        List<Integer> dist_t_to_all = new ArrayList<>(Collections.nCopies(V, INF));
        List<Integer> dist_all_to_s = new ArrayList<>(Collections.nCopies(V, INF));
        List<Integer> dist_all_to_t = new ArrayList<>(Collections.nCopies(V, INF));

        g.shortestPath(s, dist_s_to_all);
        g.shortestPath(t, dist_t_to_all);
        r.shortestPath(s, dist_all_to_s);
        r.shortestPath(t, dist_all_to_t);

        int ans = INF;
        for (int i = 0; i < V; i++) {
            if (i == s || i == t) continue; // can't store at s or t
            if (dist_s_to_all.get(i) == INF || dist_t_to_all.get(i) == INF ||
                dist_all_to_s.get(i) == INF || dist_all_to_t.get(i) == INF)
                continue; // skip if any path is unreachable

            int cost = dist_s_to_all.get(i) + dist_t_to_all.get(i)
                     + dist_all_to_t.get(i) + dist_all_to_s.get(i);
            ans = Math.min(ans, cost);
        }

        return (ans == INF ? -1 : ans); // -1 if no valid storage planet exists
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // planets
        int m = sc.nextInt(); // tunnels
        int s = sc.nextInt() - 1; // base planet (0-based)
        int t = sc.nextInt() - 1; // Ariel (0-based)

        Graph g = new Graph(n);
        Graph r = new Graph(n);

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int w = sc.nextInt();
            g.addEdge(u, v, w); // forward graph
            r.addEdge(v, u, w); // reverse graph
        }

        System.out.println(minPath(n, s, t, g, r));
    }
}
