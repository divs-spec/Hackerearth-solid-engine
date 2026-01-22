import java.io.*;
import java.util.*;

class TestClass {
    static final int MOD = 1_000_000_007;
    static final int MAX_XOR = 2048;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] a = new int[n];
        int maxVal = 0;
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
            maxVal = Math.max(maxVal, a[i]);
        }

        int[] dp = new int[MAX_XOR];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        for (int val : a) {
            int[] next = dp.clone();
            for (int x = 0; x < MAX_XOR; x++) {
                if (dp[x] != -1) {
                    int nx = x ^ val;
                    next[nx] = Math.max(next[nx], dp[x] + 1);
                }
            }
            dp = next;
        }

        long ans = 0;
        long pow = 1; // 31^0

        for (int j = 0; j <= maxVal; j++) {
            if (dp[j] > 0) {
                ans = (ans + dp[j] * pow) % MOD;
            }
            pow = (pow * 31) % MOD;
        }

        System.out.println(ans);
    }
}
