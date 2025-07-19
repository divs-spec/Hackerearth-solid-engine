import java.util.*;

public class Main {
    static final int MAX_N = 111;
    static final int MAX_VAL = 1000001;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int l = sc.nextInt();
        int n = sc.nextInt();
        int x = sc.nextInt();
        int y = sc.nextInt();

        boolean[][] dp = new boolean[MAX_N][MAX_VAL];
        dp[0][l] = true;

        int[] A = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            A[i] = sc.nextInt();
        }

        for (int i = 1; i <= n; i++) {
            for (int j = x; j <= y; j++) {
                if (j - A[i] >= x && dp[i - 1][j - A[i]]) {
                    dp[i][j] = true;
                }
                if (j + A[i] <= y && dp[i - 1][j + A[i]]) {
                    dp[i][j] = true;
                }
            }
        }

        int mn = Integer.MAX_VALUE, mx = Integer.MIN_VALUE;
        for (int j = x; j <= y; j++) {
            if (dp[n][j]) {
                mn = Math.min(mn, j);
                mx = Math.max(mx, j);
            }
        }

        int q = sc.nextInt();
        while (q-- > 0) {
            char op = sc.next().charAt(0);
            int m = sc.nextInt();

            if (op == '<') {
                System.out.println(m <= mn ? "NO" : "YES");
            } else {
                System.out.println(m >= mx ? "NO" : "YES");
            }
        }

        sc.close();
    }
}
