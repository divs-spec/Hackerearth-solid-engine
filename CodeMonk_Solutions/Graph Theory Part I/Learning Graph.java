import java.util.*;

public class Main {
    static final int SZ = 1005;

    static int N, M, k;
    static int[] val = new int[SZ];
    static ArrayList<Pair>[] edge = new ArrayList[SZ];

    // Custom class to represent a pair of (value, node)
    static class Pair {
        int value, node;
        Pair(int value, int node) {
            this.value = value;
            this.node = node;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();
        k = sc.nextInt();

        for (int i = 1; i <= N; i++) {
            val[i] = sc.nextInt();
            edge[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            int X = sc.nextInt();
            int Y = sc.nextInt();

            // Add neighboring nodes with their values
            edge[Y].add(new Pair(val[X], X));
            edge[X].add(new Pair(val[Y], Y));
        }

        for (int i = 1; i <= N; i++) {
            Collections.sort(edge[i], (a, b) -> {
                if (a.value == b.value) return b.node - a.node; // second descending
                return b.value - a.value; // first descending
            });

            if (edge[i].size() < k) {
                System.out.println(-1);
            } else {
                System.out.println(edge[i].get(k - 1).node);
            }
        }

        sc.close();
    }
}
