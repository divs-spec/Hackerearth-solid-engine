import java.io.*;
import java.util.*;

public class TestClass {

    static class Edge {
        int to, w;

        Edge(int to, int w) {
            this.to = to;
            this.w = w;
        }
    }

    static class State {
        int node;
        int val;

        State(int node, int val) {
            this.node = node;
            this.val = val;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedInputStream in = new BufferedInputStream(System.in);

        int n = nextInt(in);

        ArrayList<Edge>[] g = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            int u = nextInt(in);
            int v = nextInt(in);

            int w = u + v;

            g[u].add(new Edge(v, w));
            g[v].add(new Edge(u, w));
        }

        int xorAll = 0;

        boolean[] vis = new boolean[n + 1];

        ArrayDeque<State> stack = new ArrayDeque<>();
        stack.push(new State(1, 0));
        vis[1] = true;

        while (!stack.isEmpty()) {
            State cur = stack.pop();

            xorAll ^= cur.val;

            for (Edge e : g[cur.node]) {
                if (!vis[e.to]) {
                    vis[e.to] = true;
                    stack.push(new State(e.to, cur.val ^ e.w));
                }
            }
        }

        if ((n & 1) == 0) {
            System.out.println(xorAll);
        } else {
            System.out.println(0);
        }
    }

    static int nextInt(InputStream in) throws IOException {
        int c;
        do {
            c = in.read();
        } while (c <= ' ');

        int sign = 1;
        if (c == '-') {
            sign = -1;
            c = in.read();
        }

        int val = 0;
        while (c > ' ') {
            val = val * 10 + (c - '0');
            c = in.read();
        }

        return val * sign;
    }
}
