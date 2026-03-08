import java.util.*;

public class Main {

    static final long MOD = 1000000007;

    static void mulmat(long[][] a, long[][] b) {
        long[][] c = new long[2][2];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                c[i][j] = 0;
                for (int k = 0; k < 2; k++) {
                    c[i][j] += (a[i][k] * b[k][j]) % MOD;
                    if (c[i][j] >= MOD) c[i][j] -= MOD;
                    if (c[i][j] < 0) c[i][j] += MOD;
                }
            }
        }

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                a[i][j] = c[i][j];
    }

    static long fib(long n) {
        long[][] res = { {1,0},{0,1} };
        long[][] X = { {1,1},{1,0} };

        while (n > 0) {
            if ((n & 1) == 1)
                mulmat(res, X);

            n >>= 1;
            mulmat(X, X);
        }

        return res[0][1];
    }

    static long sum(long n) {
        if (n <= 0) return 0;

        long ret;
        if ((n & 1) == 1)
            ret = (fib(n - 1) + 1 + MOD) % MOD;
        else
            ret = (-fib(n - 1) + 1 + MOD) % MOD;

        return ret;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();

        while (t-- > 0) {
            long l = sc.nextLong();
            long r = sc.nextLong();

            long ans = (sum(r) - sum(l - 1) + MOD) % MOD;
            System.out.println(ans);
        }
    }
}
