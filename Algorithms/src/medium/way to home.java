import java.util.*;

public class Main {
    static final long MOD = 1000000007;
    static final int N = 1004;

    static long[] fact = new long[N];
    static long[] invfact = new long[N];

    static long fastExp(long num, long p) {
        if (p == 0) return 1;
        long res = fastExp(num, p / 2);
        if (p % 2 == 0)
            return (res * res) % MOD;
        else
            return (((res * res) % MOD) * num) % MOD;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        int h = n - 1;
        int r = m - 1;

        if (h + 1 < r) {
            System.out.println(0);
            return;
        }

        fact[0] = 1;
        invfact[0] = 1;

        for (int i = 1; i < N; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
            invfact[i] = fastExp(fact[i], MOD - 2);
        }

        long ans = (((fact[h + 1] * invfact[r]) % MOD) * invfact[h + 1 - r]) % MOD;

        System.out.println(ans);
    }
}
