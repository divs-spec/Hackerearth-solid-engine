import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();
        while (t-- > 0) {
            String predict = sc.next();  // "Odd" or "Even"
            int n = sc.nextInt();
            int[] arr = new int[16];  // max 16 elements
            for (int i = 0; i < n; ++i) {
                arr[i] = sc.nextInt();
            }

            int maxVal = 0;
            for (int mask = 0; mask < (1 << n); ++mask) {
                int ans = 0, k = 0;
                for (int j = 0; j < n; ++j) {
                    if ((mask & (1 << j)) != 0) {
                        if (k % 3 == 0) {
                            ans += arr[j];
                        } else if (k % 3 == 1) {
                            ans |= arr[j];
                        } else {
                            ans ^= arr[j];
                        }
                        k++;
                    }
                }
                maxVal = Math.max(maxVal, ans);
            }

            boolean isOdd = (maxVal & 1) == 1;
            boolean predictOdd = predict.equals("Odd");
            System.out.println((isOdd ^ predictOdd) ? "Mariam" : "Monk");
        }

        sc.close();
    }
}
