import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        long[] a = new long[n];
        long[] b = new long[n];

        for (int i = 0; i < n; i++) {
            a[i] = sc.nextLong();
            b[i] = a[i];
        }

        // Compute prefix OR for a[]
        for (int i = 1; i < n; i++) {
            a[i] |= a[i - 1];
        }

        boolean done = false;

        for (int j = 0; j < k; j++) {
            // Right-to-left adjacent OR
            for (int i = n - 1; i > 0; i--) {
                b[i] |= b[i - 1];
            }

            // Check if b equals a
            boolean equal = true;
            for (int i = 0; i < n; i++) {
                if (b[i] != a[i]) {
                    equal = false;
                    break;
                }
            }

            if (equal) {
                for (long num : a) System.out.print(num + " ");
                done = true;
                break;
            }
        }

        if (!done) {
            for (long num : b) System.out.print(num + " ");
        }
    }
}
