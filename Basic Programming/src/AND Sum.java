import java.util.*;

public class Main {
    static final int MOD = (int) 1e9 + 7;

    // Binary Exponentiation
    public static long binaryExponential(long x, long n) {
        if (n == 0) return 1;
        if (n == 1) return x % MOD;

        long res = binaryExponential(x, n / 2) % MOD;

        if ((n & 1) == 1) {
            return ((res * res) % MOD * x) % MOD;
        } else {
            return (res * res) % MOD;
        }
    }

    // Solve function
    public static long solve(List<Long> arr) {
        long sum = 0;

        for (long e : arr) {
            sum = (sum + e % MOD) % MOD;
        }

        for (int i = 0; i < 31; i++) {
            long cnt = 0;
            for (long e : arr) {
                if ((e & (1L << i)) != 0) cnt++;
            }

            long total = ((binaryExponential(2, cnt) - cnt - 1 + MOD) % MOD * binaryExponential(2, i)) % MOD;
            sum = (sum + total) % MOD;
        }

        return sum;
    }

    public static void run(Scanner sc) {
        int n = sc.nextInt();
        List<Long> arr = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            arr.add(sc.nextLong());
        }

        System.out.println(solve(arr));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            run(sc);
        }

        sc.close();
    }
}
