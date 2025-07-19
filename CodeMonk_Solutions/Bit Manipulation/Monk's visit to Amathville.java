import java.util.*;

public class Main {
    static final int MAX_MASK = 1 << 15;
    static long[] dp = new long[MAX_MASK];
    static int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            int n = sc.nextInt();
            long p = sc.nextLong();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i + 1;

            Arrays.fill(dp, 0);
            dp[0] = 1;

            for (int i = 1; i < n; i++) {
                int val = 0;
                for (int j = 0; j < 15; j++) {
                    if (a[i] % primes[j] == 0) {
                        val |= (1 << j);
                    }
                }
                dp[val] = 1;
                for (int mask = 1; mask < MAX_MASK; mask++) {
                    if ((mask & val) == 0 && dp[mask] == 1) {
                        dp[mask | val] = 1;
                    }
                }
            }

            int bestCount = 0;
            int bestMask = 0;
            for (int mask = 1; mask < MAX_MASK; mask++) {
                if (dp[mask] == 1) {
                    int popCount = Integer.bitCount(mask);
                    if (popCount > bestCount) {
                        bestCount = popCount;
                        bestMask = mask;
                    } else if (popCount == bestCount) {
                        bestMask = Math.min(bestMask, mask);
                    }
                }
            }

            int k = bestCount + 1;
            long[] c = new long[k];
            c[0] = 1;
            int idx = 1;
            for (int i = 0; i < 15; i++) {
                if ((bestMask & (1 << i)) != 0) {
                    c[idx++] = primes[i];
                }
            }

            int size = 1 << k;
            long[][] cost = new long[size][k];
            for (int mask = 0; mask < size; mask++) {
                Arrays.fill(cost[mask], (mask == 0 || Integer.bitCount(mask) <= 1) ? 0 : Long.MAX_VALUE);
            }

            for (int mask = 1; mask < size; mask++) {
                for (int j = 0; j < k; j++) {
                    if ((mask & (1 << j)) != 0) {
                        int prevMask = mask ^ (1 << j);
                        for (int l = 0; l < k; l++) {
                            if ((prevMask & (1 << l)) != 0) {
                                cost[mask][j] = Math.min(cost[mask][j], cost[prevMask][l] + (c[j] * c[l]) % p);
                            }
                        }
                    }
                }
            }

            long ans = Long.MAX_VALUE;
            for (int i = 0; i < k; i++) {
                ans = Math.min(ans, cost[size - 1][i]);
            }

            System.out.println(ans);
        }
    }
}
