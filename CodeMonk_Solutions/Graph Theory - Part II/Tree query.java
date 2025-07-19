import java.io.*;
import java.util.*;

public class Main {
    static List<Integer>[] g;
    static int[] in, out;

    public static void main(String[] args) throws IOException {
        // Fast input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");
        int n = Integer.parseInt(parts[0]); // number of nodes
        int m = Integer.parseInt(parts[1]); // number of edges

        g = new ArrayList[n + 1];
        in = new int[n + 1];
        out = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            parts = br.readLine().split(" ");
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);
            out[u]++;
            in[v]++;
            g[u].add(v);
        }

        int c1 = 0, c2 = 0;
        for (int i = 1; i <= n; i++) {
            if (in[i] == 0) c1++;  // source nodes
            if (out[i] == 0) c2++; // sink nodes
        }

        System.out.println(Math.max(c1, c2));
    }
}
