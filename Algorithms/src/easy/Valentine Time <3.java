import java.io.*;
import java.util.*;

public class TestClass {
    static final int MAX = 1000001;
    static int[] totalDivisors = new int[MAX];
    static int[] primeDivisors = new int[MAX];

    public static void main(String[] args) throws Exception {
        // Precompute
        precomputeDivisors();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            String[] parts = br.readLine().split(" ");
            int L = Integer.parseInt(parts[0]);
            int R = Integer.parseInt(parts[1]);

            int bestNum = L;
            int maxDiff = totalDivisors[L] - primeDivisors[L];

            for (int i = L + 1; i <= R; i++) {
                int diff = totalDivisors[i] - primeDivisors[i];
                if (diff > maxDiff || (diff == maxDiff && i < bestNum)) {
                    maxDiff = diff;
                    bestNum = i;
                }
            }

            System.out.println(bestNum);
        }
    }

    static void precomputeDivisors() {
        // Total divisors
        for (int i = 1; i < MAX; i++) {
            for (int j = i; j < MAX; j += i) {
                totalDivisors[j]++;
            }
        }

        // Prime divisors using sieve
        boolean[] isPrime = new boolean[MAX];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        for (int i = 2; i < MAX; i++) {
            if (isPrime[i]) {
                for (int j = i; j < MAX; j += i) {
                    primeDivisors[j]++;
                    isPrime[j] = false;
                }
            }
        }
    }
}
