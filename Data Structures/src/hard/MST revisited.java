import java.util.*;

class UnionFind {
    int[] parent, size;

    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for(int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int x) {
        if(parent[x] != x)
            parent[x] = find(parent[x]); // path compression
        return parent[x];
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if(rootX != rootY) {
            if(size[rootX] < size[rootY]) {
                int temp = rootX;
                rootX = rootY;
                rootY = temp;
            }

            parent[rootY] = rootX;
            size[rootX] += size[rootY];
        }
    }
}

class Edge {
    int u, v, w;
    Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}

public class KruskalDynamic {
    public static int kruskal(List<Edge> edges, int n) {
        Collections.sort(edges, Comparator.comparingInt(e -> e.w));
        UnionFind uf = new UnionFind(n);
        int mstWeight = 0;
        int count = 0;

        for(Edge edge : edges) {
            if(uf.find(edge.u) != uf.find(edge.v)) {
                uf.union(edge.u, edge.v);
                mstWeight += edge.w;
                count++;
                if(count == n - 1) break; // MST complete
            }
        }

        return mstWeight;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int q = sc.nextInt();

        List<Edge> edges = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int c = sc.nextInt();
            edges.add(new Edge(u, v, c));
        }

        int ans = 0;

        while(q-- > 0) {
            int x1 = sc.nextInt();
            int x2 = sc.nextInt();
            int x3 = sc.nextInt();
            int x4 = sc.nextInt();
            int x5 = sc.nextInt();

            int a = (x1 + ans) % 100;
            int b = (x2 + ans) % 100;
            int u = (x3 + ans) % 100;
            int v = (x4 + ans) % 100;
            int c = (x5 + ans) % 100;

            // Remove edge (a-1, b-1) or (b-1, a-1)
            Iterator<Edge> it = edges.iterator();
            while(it.hasNext()) {
                Edge edge = it.next();
                if((edge.u == a - 1 && edge.v == b - 1) || (edge.u == b - 1 && edge.v == a - 1)) {
                    it.remove();
                    break;
                }
            }

            // Add new edge (u-1, v-1, c)
            edges.add(new Edge(u - 1, v - 1, c));

            ans = kruskal(edges, n);
            System.out.println(ans);
        }

        sc.close();
    }
}
