import java.io.*;
import java.util.*;

class TestClass {
    static final int MAX = 1000;
    static final long MOD = 1_000_000_007;

    static long[][] nCr = new long[MAX + 1][MAX + 1];
    static long[][] prod = new long[MAX + 1][MAX + 1];

    static long modExp(long a, long b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) res = res * a % MOD;
            a = a * a % MOD;
            b >>= 1;
        }
        return res;
    }

    static long modInv(long x) {
        return modExp(x, MOD - 2);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Step 1: nCr
        for (int i = 0; i <= MAX; i++) {
            nCr[i][0] = nCr[i][i] = 1;
            for (int j = 1; j < i; j++) {
                nCr[i][j] = (nCr[i - 1][j - 1] + nCr[i - 1][j]) % MOD;
            }
        }

        // Step 2: prod[i][j]
        for (int i = 0; i <= MAX; i++) {
            prod[i][0] = 1;
            for (int j = 1; j <= MAX; j++) {
                long f;
                if (i >= j) f = nCr[i][j];
                else f = (long)i * j % MOD;

                prod[i][j] = prod[i][j - 1] * f % MOD;
            }
        }

        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (q-- > 0) {
            String[] parts = br.readLine().split(" ");
            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);
            int c = Integer.parseInt(parts[2]);
            int d = Integer.parseInt(parts[3]);

            long ans = 0;

            for (int i = a; i <= b; i++) {
                long val = prod[i][d];
                if (c > 1) {
                    val = val * modInv(prod[i][c - 1]) % MOD;
                }
                ans = (ans + val) % MOD;
            }

            sb.append(ans).append("\n");
        }

        System.out.print(sb);
    }
}
