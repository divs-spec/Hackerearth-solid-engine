//Approach no 1:
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final int MOD = 1000000007;

        int n = sc.nextInt();
        sc.nextLine(); // Consume newline

        char[][] c = new char[n][];
        for (int i = 0; i < n; i++) {
            c[i] = sc.nextLine().toCharArray();
        }

        int[][][] Dp = new int[2][n][n];

        // Base case initialization
        for (int i = 0; i < n; i++) {
            Dp[0][i][i] = 1;
        }

        for (int k = 1; k < n; k++) {
            int[][] old = Dp[(k ^ 1)];
            int[][] dp = Dp[k & 1];

            for (int i = 0; i < n - k; i++) {
                for (int j = 0; j < n - k; j++) {
                    if (c[n - k - i - 1][i] == c[n - j - 1][j + k]) {
                        dp[i][j] = (
                            ((old[i][j] + old[i + 1][j]) % MOD
                            + (old[i][j + 1] + old[i + 1][j + 1]) % MOD) % MOD
                        );
                    } else {
                        dp[i][j] = 0;
                    }
                }
            }
        }

        System.out.println(Dp[(n ^ 1) & 1][0][0]);
        sc.close();
    }
}

// Approach no 2:
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final int MOD = 1000000007;

        int n = sc.nextInt();
        sc.nextLine(); // consume the newline

        char[][] table = new char[n][];
        for (int i = 0; i < n; i++) {
            table[i] = sc.nextLine().toCharArray();
        }

        // 3D DP array [2][501][501]
        int[][][] sol = new int[2][501][501];

        // Base case: single cell palindromes
        for (int i = 0; i < n; i++) {
            sol[0][i][i] = 1;
        }

        // k = length of the path from ends toward center
        for (int k = 1; k < n; k++) {
            int[][] old = sol[1 - (k & 1)];
            int[][] dp = sol[k & 1];

            // Clear dp before filling
            for (int[] row : dp) Arrays.fill(row, 0);

            for (int i = 0; i < n - k; i++) {
                for (int j = 0; j < n - k; j++) {
                    if (table[n - k - i - 1][i] == table[n - j - 1][j + k]) {
                        int val = old[i][j];
                        if (i + 1 < n) val = (val + old[i + 1][j]) % MOD;
                        if (j + 1 < n) val = (val + old[i][j + 1]) % MOD;
                        if (i + 1 < n && j + 1 < n) val = (val + old[i + 1][j + 1]) % MOD;
                        dp[i][j] = val;
                    } else {
                        dp[i][j] = 0;
                    }
                }
            }
        }

        int result = sol[1 - (n & 1)][0][0];
        System.out.println(result);
        sc.close();
    }
}
