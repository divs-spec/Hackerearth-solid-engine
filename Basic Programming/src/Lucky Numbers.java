import java.util.*;
import java.io.*;

public class Main {
    static final long MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases

        while (t-- > 0) {
            long n = sc.nextLong();
            long sum = 0;

            // length = floor(log2(n))
            int length = (int)(Math.log(n) / Math.log(2));

            for (int i = 0; i < length; i++) {
                for (int j = i + 1; j <= length; j++) {
                    long num = (1L << i) | (1L << j);
                    if (num <= n) {
                        sum = (sum + num) % MOD;
                    }
                }
            }

            System.out.println(sum);
        }
    }
}
