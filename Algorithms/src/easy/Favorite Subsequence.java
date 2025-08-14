import java.util.*;

public class Main {
    static final long MOD = 1000000007L;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();

        long[] dp = new long[3]; // dp[0] for 'a', dp[1] for 'b', dp[2] for 'c'

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == 'a') {
                dp[0] = (2 * dp[0] + 1) % MOD;
            } 
            else if (ch == 'b') {
                dp[1] = (dp[1] * 2 + dp[0]) % MOD;
            } 
            else if (ch == 'c') {
                dp[2] = (dp[2] * 2 + dp[1]) % MOD;
            }
        }

        System.out.println(dp[2]);
        sc.close();
    }
}
