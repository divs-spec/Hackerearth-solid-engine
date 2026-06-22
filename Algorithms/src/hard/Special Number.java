import java.io.*;
import java.util.*;

public class Main {
    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] freq = new int[31];
        for (int i = 0; i < n; i++) {
            freq[Integer.parseInt(st.nextToken())]++;
        }

        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};

        int[] maskOf = new int[31];
        Arrays.fill(maskOf, -1);

        for (int x = 2; x <= 30; x++) {
            boolean squareFree = true;
            int mask = 0;

            for (int i = 0; i < primes.length; i++) {
                int p = primes[i];

                if (x % (p * p) == 0) {
                    squareFree = false;
                    break;
                }

                if (x % p == 0) {
                    mask |= (1 << i);
                }
            }

            if (squareFree) {
                maskOf[x] = mask;
            }
        }

        long[] dp = new long[1 << 10];
        dp[0] = 1;

        for (int x = 2; x <= 30; x++) {
            int cnt = freq[x];
            int m = maskOf[x];

            if (cnt == 0 || m == -1) continue;

            long[] ndp = dp.clone();

            for (int mask = 0; mask < (1 << 10); mask++) {
                if ((mask & m) == 0) {
                    int newMask = mask | m;
                    ndp[newMask] =
                            (ndp[newMask] + dp[mask] * cnt) % MOD;
                }
            }

            dp = ndp;
        }

        long total = 0;
        for (long v : dp) {
            total = (total + v) % MOD;
        }

        long pow2 = 1;
        for (int i = 0; i < freq[1]; i++) {
            pow2 = (pow2 * 2) % MOD;
        }

        total = (total * pow2) % MOD;

        // remove empty subset
        total = (total - 1 + MOD) % MOD;

        System.out.println(total);
    }
}
