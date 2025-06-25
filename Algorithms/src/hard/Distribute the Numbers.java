import java.util.Scanner;

public class Main {

    // Method to get the maximum of two values
    static long max(long a, long b) {
        return (a > b) ? a : b;
    }

    // Check function as described in the original code
    static boolean check(long n, long n1, long n2, long x, long y) {
        long lcm = lcm(x, y);
        long req1 = max(0, n1 - (n / y) + (n / lcm));
        long req2 = max(0, n2 - (n / x) + (n / lcm));
        long rem = n - (n / x) - (n / y) + (n / lcm);
        return rem >= (req1 + req2);
    }

    // LCM helper using GCD
    static long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    static long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            long n1 = sc.nextLong();
            long n2 = sc.nextLong();
            long x = sc.nextLong();
            long y = sc.nextLong();

            long start = 1, end = 2_000_000_000L;
            long ans = Long.MAX_VALUE;

            while (start <= end) {
                long mid = (start + end) / 2;
                if (check(mid, n1, n2, x, y)) {
                    ans = mid;
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }

            System.out.println(ans);
        }

        sc.close();
    }
}
