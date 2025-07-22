import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt(); // number of test cases

        while (t-- > 0) {
            int z = sc.nextInt(); // initial value
            int n = sc.nextInt(); // number of elements

            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
            }

            // Apply bitwise AND with all array elements
            for (int i = 0; i < n; i++) {
                z = z & a[i];
            }

            if (z != 0) {
                System.out.println("No");
            } else {
                System.out.println("Yes");
            }
        }

        sc.close();
    }
}
