import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long res = 0;
        int n = sc.nextInt();
        long[] arr = new long[n];

        for (int i = 0; i < n; ++i)
            arr[i] = sc.nextLong();

        // XOR with second input array
        for (int i = 0; i < n; ++i) {
            long x = sc.nextLong();
            arr[i] ^= x;
        }

        // Process third input array and compute result
        for (int i = 0; i < n; ++i) {
            long x = sc.nextLong();
            if ((~arr[i] & x) != 0) {
                res += (arr[i] ^ (~arr[i] & x));
            } else {
                res += (arr[i] ^ (x & -x));
            }
        }

        System.out.println(res);
    }
}
