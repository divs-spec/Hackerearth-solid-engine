import java.util.*;

public class Main {
    static final int SZ = 100005;

    static int N, K;
    static int[] A = new int[SZ];
    static long ans = 0;
    static HashMap<Integer, Long> cnt = new HashMap<>();
    static ArrayList<Integer>[] edge = new ArrayList[SZ];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        K = sc.nextInt();

        for (int i = 1; i <= N; i++) {
            A[i] = sc.nextInt();
            edge[i] = new ArrayList<>();
        }

        for (int i = 2; i <= N; i++) {
            int x = sc.nextInt();
            edge[x].add(i);
        }

        cnt.put(0, 1L);  // Initial path XOR is 0
        dfs(1, 0);

        System.out.println(ans);
        sc.close();
    }

    static void dfs(int u, int x) {
        x ^= A[u];  // update XOR path
        ans += cnt.getOrDefault(x ^ K, 0L);  // count how many prefix XORs match (x ^ K)
        cnt.put(x, cnt.getOrDefault(x, 0L) + 1);

        for (int v : edge[u]) {
            dfs(v, x);
        }

        // Backtrack
        cnt.put(x, cnt.get(x) - 1);
    }
}
