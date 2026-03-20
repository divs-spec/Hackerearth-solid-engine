import java.util.*;

public class Main {
    static List<Integer>[] a = new ArrayList[1000001];
    static List<Integer>[] c = new ArrayList[1000001];
    static int[] v = new int[1000001];
    static int[] b = new int[1000001];
    static int[] d = new int[1000001];

    static void dfs(int s, int k) {
        v[s] = 1;

        if (c[b[s]].size() >= k) {
            int size = c[b[s]].size();
            d[s] = c[b[s]].get(size - k);
        } else {
            d[s] = -1;
        }

        c[b[s]].add(s);

        for (int nei : a[s]) {
            if (v[nei] == 0) {
                dfs(nei, k);
            }
        }

        if (!c[b[s]].isEmpty()) {
            c[b[s]].remove(c[b[s]].size() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            a[i] = new ArrayList<>();
            c[i] = new ArrayList<>();
        }

        for (int i = 1; i <= n; i++) {
            b[i] = sc.nextInt();
        }

        for (int i = 1; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            a[x].add(y);
            a[y].add(x);
        }

        dfs(1, k);

        for (int i = 1; i <= n; i++) {
            System.out.print(d[i] + " ");
        }
    }
}
