import java.io.*;
import java.util.*;

public class MatrixExponentiation {
    static final int MOD = 1000000009;
    static int[][] M = {
        {0, 99, -10, 2},
        {1, 0, 0, 0},
        {0, 1, 0, 0},
        {0, 0, 0, 10}
    };

    public static void multiply(int[][] a, int[][] b) {
        int[][] mul = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    mul[i][j] = (int) ((mul[i][j] + 1L * a[i][k] * b[k][j]) % MOD);
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            System.arraycopy(mul[i], 0, a[i], 0, 4);
        }
    }

    public static void power(int[][] a, int n) {
        if (n == 0 || n == 1) return;
        power(a, n / 2);
        multiply(a, a);
        if (n % 2 != 0) multiply(a, M);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            if (n == 0) {
                System.out.println(1);
            } else if (n == 1) {
                System.out.println(10);
            } else if (n == 2) {
                System.out.println(99);
            } else if (n == 3) {
                System.out.println(980);
            } else {
                int[][] a = {
                    {0, 99, -10, 2},
                    {1, 0, 0, 0},
                    {0, 1, 0, 0},
                    {0, 0, 0, 10}
                };
                power(a, n - 3);
                long p = (1L * a[3][3] * 1000) % MOD;
                long k = (1L * a[0][0] * 20 + a[0][1] + 1L * 100 * a[0][3]) % MOD;
                long ans = (p - k + MOD) % MOD;
                System.out.println(ans);
            }
        }
    }
}
