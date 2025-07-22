import java.util.*;

public class TestClass {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();  // Read the size of array
        long[] a = new long[n];

        // Read the array elements
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextLong();
        }

        // Initialize result with the first element
        long ans = a[0];

        // Compute bitwise OR of all elements
        for (int i = 1; i < n; i++) {
            ans |= a[i];
        }

        System.out.println(ans);
        sc.close();
    }
}
