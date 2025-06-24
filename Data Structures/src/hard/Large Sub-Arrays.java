import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();

        while (tc-- > 0) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int k = sc.nextInt();
            int[] ar = new int[n];
            for (int i = 0; i < n; i++) {
                ar[i] = sc.nextInt();
            }

            int ans = 0;

            for (int i = 0; i < n * m; i++) {
                int val = ar[i % n];
                if (val > k) continue;

                int sum = val;
                ans++; // count the single element

                for (int j = i + 1; j < n * m; j++) {
                    sum += ar[j % n];
                    if (sum <= k) ans++;
                    else break;
                }
            }

            System.out.println(ans);
        }

        sc.close();
    }
}
