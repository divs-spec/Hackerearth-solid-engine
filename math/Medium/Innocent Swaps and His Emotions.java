import java.util.*;
import java.io.*;

public class Main {
    static final int MOD = 1_000_000_007;
    static final int N = 1_000_010;
    static long[] fact = new long[N];

    public static void main(String[] args) throws IOException {
        // Fast I/O
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        // Precompute factorials
        fact[0] = 1;
        for (int i = 1; i < N; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }

        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            String[] line = br.readLine().split(" ");
            int n = Integer.parseInt(line[0]);
            int k = Integer.parseInt(line[1]);

            long a = binExp(2, k, MOD);
            long num = fact[n];
            long den = (fact[n - k] * fact[k]) % MOD;
            long denInv = binExp(den, MOD - 2, MOD); // modular inverse

            long result = (num * denInv) % MOD;
            result = (result * a) % MOD;

            sb.append(result).append("\n");
        }

        System.out.print(sb);
    }

    static long binExp(long a, long b, long m) {
        long res = 1;
        a %= m;
        while (b > 0) {
            if ((b & 1) == 1)
                res = (res * a) % m;
            a = (a * a) % m;
            b >>= 1;
        }
        return res;
    }
}
