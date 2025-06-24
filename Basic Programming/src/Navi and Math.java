import java.io.*;
import java.util.*;

public class Main {
    static final long MOD = 1_000_000_007;
    static final long MAXM = (long) 1e7;

    // Fast exponentiation (a^b mod m)
    static long power(long base, long exponent, long modulus) {
        long result = 1;
        base %= modulus;
        while (exponent > 0) {
            if ((exponent & 1) == 1)
                result = (result * base) % modulus;
            base = (base * base) % modulus;
            exponent >>= 1;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        FastReader sc = new FastReader();
        int t = sc.nextInt();
        if (t < 1 || t > 10) throw new IllegalArgumentException("t out of bounds");

        for (int tt = 1; tt <= t; tt++) {
            int n = sc.nextInt();
            if (n < 2 || n > 16) throw new IllegalArgumentException("n out of bounds");

            long[] a = new long[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextLong();
                if (a[i] < 1 || a[i] > MAXM) throw new IllegalArgumentException("a[i] out of bounds");
            }

            long ans = Long.MIN_VALUE;
            int totalMasks = 1 << n;

            for (int mask = 1; mask < totalMasks; mask++) {
                long sum = 0, product = 1;
                int count = 0;

                for (int i = 0; i < n; i++) {
                    if ((mask & (1 << i)) != 0) {
                        count++;
                        sum = (sum + a[i]) % MOD;
                        product = (product * a[i]) % MOD;
                    }
                }

                if (count >= 2) {
                    long inv = power(sum, MOD - 2, MOD); // modular inverse
                    long value = (product * inv) % MOD;
                    ans = Math.max(ans, value);
                }
            }

            System.out.println("Case #" + tt + ": " + ans);
        }
    }

    // Fast input reader
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;
        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}
