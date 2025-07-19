import java.util.*;

public class Main {
    public static void main(String[] args) {
        final int MAXN = 100005;
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        if (t < 1 || t > 10) throw new AssertionError();

        int[] A = new int[MAXN];
        int[] dp = new int[MAXN];

        while (t-- > 0) {
            int n = sc.nextInt();
            if (n < 2 || n > 100000) throw new AssertionError();

            for (int i = 1; i <= n; i++) {
                A[i] = sc.nextInt();
                if (A[i] != -1 && A[i] != 1) throw new AssertionError();
                dp[i] = dp[i - 1] + (A[i] == -1 ? 1 : 0);
            }

            int ans = n;
            for (int i = 1; i <= n - 1; i++) {
                ans = Math.min(ans, i - dp[i] + dp[n] - dp[i]);
            }

            System.out.println(ans);
        }
    }
}
