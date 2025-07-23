import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt(); // Number of test cases

        while (T-- > 0) {
            int n = sc.nextInt(); // Size of array
            int[] arr = new int[n];

            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            long ans = 0;
            long carry = 0;

            for (int i = 0; i < n; i++) {
                ans += (arr[i] + carry) % 2;
                carry = (arr[i] + carry) / 2;
            }

            while (carry > 0) {
                ans += carry % 2;
                carry /= 2;
            }

            System.out.println(ans);
        }
    }
}
