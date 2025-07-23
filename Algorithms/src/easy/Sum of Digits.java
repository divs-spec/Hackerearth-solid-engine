import java.io.*;
import java.util.*;

public class Main {
    static final int MOD = 1_000_000_007;

    static int digitSum(long n) {
        int sum = 0;
        while (n > 0) {
            sum += (n % 10);
            n /= 10;
        }
        return sum;
    }

    static long power(long base, long exp, long mod) {
        long result = 1;
        base = base % mod;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = (result * base) % mod;
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String[] parts = br.readLine().split(" ");
        int[] digitVals = new int[N];
        
        for (int i = 0; i < N; i++) {
            long val = Long.parseLong(parts[i]);
            digitVals[i] = digitSum(val);
        }

        Arrays.sort(digitVals);

        long[] pow2 = new long[N + 1];
        pow2[0] = 1;
        for (int i = 1; i <= N; i++) {
            pow2[i] = (pow2[i - 1] * 2) % MOD;
        }

        long result = 0;
        for (int i = 0; i < N; i++) {
            // Each digit value contributes 2^i times as max in subsets
            result = (result + digitVals[i] * pow2[i]) % MOD;
        }

        System.out.println(result);
    }
}
