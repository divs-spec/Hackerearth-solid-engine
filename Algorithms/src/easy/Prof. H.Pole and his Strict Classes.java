import java.io.*;
import java.util.*;

public class Main {
    static final int MOD = 1_000_000_007;
    static final int MAX = 100000;
    static long[] fact = new long[MAX + 1];

    public static void main(String[] args) throws IOException {
        // Fast I/O setup
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        // Precompute factorials modulo MOD
        fact[0] = 1;
        for (int i = 1; i <= MAX; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }

        // Read number of test cases
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            // Read four integers a, b, c, d
            String[] parts = br.readLine().trim().split("\\s+");
            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);
            int c = Integer.parseInt(parts[2]);
            int d = Integer.parseInt(parts[3]);

            // Compute result
            long res = fact[a] * fact[b] % MOD;
            res = res * fact[c] % MOD;

            // d is unused as in the original Python code
            out.append(res).append("\n");
        }

        // Output all results
        System.out.print(out);
    }
}
