import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();
        
        while (tc-- > 0) {
            int n = sc.nextInt();
            long[] c = new long[n + 1];
            long[] l = new long[n + 1];

            for (int i = 1; i <= n; i++) {
                c[i] = sc.nextLong();
            }
            for (int i = 1; i <= n; i++) {
                l[i] = sc.nextLong();
            }

            long mi = 1_000_000L;
            long ans = 0;

            for (int i = 1; i <= n; i++) {
                mi = Math.min(mi, c[i]);
                ans += mi * l[i];
            }

            System.out.println(ans);
        }

        sc.close();
    }
}
