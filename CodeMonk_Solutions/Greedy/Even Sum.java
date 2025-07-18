import java.io.*;
import java.util.*;

public class Main {
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // Fast I/O
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        long[] a = new long[n + 2];
        long[] sum = new long[n + 2];
        long[] even = new long[n + 2];
        long[] odd = new long[n + 2];

        even[0] = 1;
        long ans = 0;

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            a[i] = Long.parseLong(st.nextToken());

            sum[i] = sum[i - 1] + a[i];

            if ((sum[i] & 1) == 1) {
                odd[i] = odd[i - 1] + 1;
                even[i] = even[i - 1];
            } else {
                even[i] = even[i - 1] + 1;
                odd[i] = odd[i - 1];
            }
        }

        for (int i = 1; i <= n; i++) {
            long odr = odd[n] - odd[i];
            long evr = even[n] - even[i];

            if ((sum[i] & 1) == 1) {
                long right = (odr * (odr + 1) / 2 % MOD + evr * (evr - 1) / 2 % MOD) % MOD;
                ans = (ans + (odd[i - 1] * right) % MOD) % MOD;
            } else {
                long right = (odr * (odr - 1) / 2 % MOD + evr * (evr + 1) / 2 % MOD) % MOD;
                ans = (ans + (even[i - 1] * right) % MOD) % MOD;
            }
        }

        System.out.println(ans);
    }
}
