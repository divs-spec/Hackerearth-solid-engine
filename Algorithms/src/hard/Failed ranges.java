import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Reading n and m
        int n = sc.nextInt();
        int m = sc.nextInt();

        // Reading array a of size n
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextLong();
        }

        // Reading array b of size m
        long[] b = new long[m];
        for (int i = 0; i < m; i++) {
            b[i] = sc.nextLong();
        }

        // Reading integers X and Y
        long X = sc.nextLong();
        long Y = sc.nextLong();

        // You can add further logic below this line
        // For example: System.out.println("Input read successfully!");

        sc.close();
    }
}
