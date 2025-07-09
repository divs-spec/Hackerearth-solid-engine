import java.util.*;
import java.io.*;

public class Main {
    static final int MOD = 1_000_000_007;
    static final int MAXN = 500_005;

    static int[] A = new int[MAXN];
    static Map<Integer, List<Integer>> allPrimes = new HashMap<>();
    static Map<Integer, int[]> prefixExp = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; ++i) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        // Prime factorizations of numbers from 1 to 10
        Map<Integer, List<int[]>> primeFactors = new HashMap<>();
        primeFactors.put(1, new ArrayList<>());
        primeFactors.put(2, List.of(new int[]{2, 1}));
        primeFactors.put(3, List.of(new int[]{3, 1}));
        primeFactors.put(4, List.of(new int[]{2, 2}));
        primeFactors.put(5, List.of(new int[]{5, 1}));
        primeFactors.put(6, List.of(new int[]{2, 1}, new int[]{3, 1}));
        primeFactors.put(7, List.of(new int[]{7, 1}));
        primeFactors.put(8, List.of(new int[]{2, 3}));
        primeFactors.put(9, List.of(new int[]{3, 2}));
        primeFactors.put(10, List.of(new int[]{2, 1}, new int[]{5, 1}));

        // Collect all primes
        Set<Integer> primes = new HashSet<>();
        for (List<int[]> factors : primeFactors.values()) {
            for (int[] pair : factors) {
                primes.add(pair[0]);
            }
        }

        // Initialize prefix sums for each prime
        for (int p : primes) {
            prefixExp.put(p, new int[N + 1]);
        }

        // Build prefix sums
        for (int i = 1; i <= N; ++i) {
            for (int p : primes) {
                prefixExp.get(p)[i] = prefixExp.get(p)[i - 1];
            }
            for (int[] factor : primeFactors.get(A[i])) {
                int p = factor[0], e = factor[1];
                prefixExp.get(p)[i] += e;
            }
        }

        // Answer queries
        StringBuilder output = new StringBuilder();
        for (int qi = 0; qi < M; qi++) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            long result = 1;
            for (int p : primes) {
                int[] prefix = prefixExp.get(p);
                int exp = prefix[r] - prefix[l - 1];
                result = (result * (exp + 1)) % MOD;
            }
            output.append(result).append("\n");
        }

        System.out.print(output);
    }
}
