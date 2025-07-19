import java.util.*;

public class Main {
    static class Node {
        int n;
        int endswith;
        Node(int n, int endswith) {
            this.n = n;
            this.endswith = endswith;
        }
    }

    static class Pair {
        long first, second;
        Pair(long f, long s) {
            this.first = f;
            this.second = s;
        }
    }

    static Pair[][] dist;
    static List<List<Pair>> adj;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        long INF = (long) 1e18;
        dist = new Pair[n + 1][2];
        for (int i = 0; i <= n; i++) {
            dist[i][0] = new Pair(INF, INF);
            dist[i][1] = new Pair(INF, INF);
        }

        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt(), b = sc.nextInt(), w = sc.nextInt();
            adj.get(a).add(new Pair(b, w));
            adj.get(b).add(new Pair(a, w));
        }

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> {
            Pair da = dist[a.n][a.endswith];
            Pair db = dist[b.n][b.endswith];
            if (da.first != db.first) return Long.compare(da.first, db.first);
            return Long.compare(da.second, db.second);
        });

        dist[1][0] = new Pair(0, 0);
        dist[1][1] = new Pair(0, 0);
        pq.offer(new Node(1, 0));
        pq.offer(new Node(1, 1));

        while (!pq.isEmpty()) {
            Node tp = pq.poll();
            int u = tp.n;
            int ew = tp.endswith;
            long d = dist[u][ew].first;
            long h = dist[u][ew].second;

            for (Pair p : adj.get(u)) {
                int v = (int) p.first;
                int w = (int) p.second;
                int endswith = w % 2;
                long hps = h;
                if ((endswith ^ ew) == 1) hps++;

                long newDist = d + w;
                Pair curr = dist[v][endswith];

                if (curr.first > newDist || (curr.first == newDist && curr.second > hps)) {
                    dist[v][endswith] = new Pair(newDist, hps);
                    pq.offer(new Node(v, endswith));
                }
            }
        }

        Pair ans;
        if (dist[n][0].first < dist[n][1].first) {
            ans = dist[n][0];
        } else if (dist[n][0].first == dist[n][1].first) {
            ans = (dist[n][0].second > dist[n][1].second) ? dist[n][0] : dist[n][1];
        } else {
            ans = dist[n][1];
        }

        System.out.println(ans.first + " " + ans.second);
    }
}
