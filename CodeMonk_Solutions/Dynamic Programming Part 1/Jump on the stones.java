import java.util.*;

public class Main {
    public static void main(String[] args) {
        final int MOD = 1_000_000_007;
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();

        int[] dp = new int[10017];
        dp[0] = 1;

        for (int i = 1; i < n; i++) {
            for (int j = Math.max(0, i - k); j < i; j++) {
                dp[i] = (dp[i] + dp[j]) % MOD;
            }
        }

        System.out.println(dp[n - 1]);
    }
}
