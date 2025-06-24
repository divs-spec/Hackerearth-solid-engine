import java.io.*;
import java.util.*;

public class Main {
    static final long MOD = 1_000_000_007;

    // Fast Doubling Fibonacci: returns long[]{F(n), F(n+1)}
    static long[] fib(long n) {
        if (n == 0) return new long[]{0, 1};
        long[] ab = fib(n >> 1);
        long a = ab[0], b = ab[1];
        long c = (a * ((2 * b % MOD - a + MOD) % MOD)) % MOD;
        long d = (a * a % MOD + b * b % MOD) % MOD;
        if ((n & 1) == 1) return new long[]{d, (c + d) % MOD};
        else return new long[]{c, d};
    }

    // Euclidean Algorithm
    static long gcd(long a, long b) {
        while (b != 0) {
            long tmp = b;
            b = a % b;
            a = tmp;
        }
        return a;
    }

    public static void main(String[] args) throws Exception {
        new Thread(null, () -> {
            try {
                solve();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "solve", 1 << 26).start();
    }

    public static void solve() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            String[] parts = br.readLine().split(" ");
            long M = Long.parseLong(parts[0]);
            long N = Long.parseLong(parts[1]);
            long G = gcd(M, N);
            sb.append(fib(G)[0]).append('\n');  // F(G)
        }

        System.out.print(sb);
    }
}
