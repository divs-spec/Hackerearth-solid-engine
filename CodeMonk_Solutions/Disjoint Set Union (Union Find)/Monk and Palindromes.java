import java.util.Scanner;

public class Main {
    static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] v = new int[n];
        for (int i = 0; i < n; i++) {
            v[i] = -1;
        }

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            if (a == b) continue;

            int s = a - 1;
            int e = b - 1;
            while (s < e) {
                int t1 = find(v, s);
                int t2 = find(v, e);
                if (t1 != t2) {
                    v[t1] = t2;
                }
                s++;
                e--;
            }
        }

        long total = 1;
        for (int i = 0; i < n; i++) {
            if (v[i] == -1) {
                total = (total * 10) % MOD;
            }
        }

        System.out.println(total);
        sc.close();
    }

    static int find(int[] v, int i) {
        while (v[i] != -1) {
            i = v[i];
        }
        return i;
    }
}
