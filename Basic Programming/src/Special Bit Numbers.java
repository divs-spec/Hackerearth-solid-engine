// APPROACH NO 1:
// TLE
import java.util.*;

public class Main {
    static final int N = 100010;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int q = sc.nextInt();

        int[] specialBits = new int[N];
        int prev = 0;

        for (int i = 0; i < n; ++i) {
            int x = sc.nextInt();
            boolean isSpecial = false;

            for (int j = 0; j < 32; ++j) {
                if ((x & (1 << j)) != 0) {
                    if (prev == 1) {
                        specialBits[i + 1] = 1;
                        prev = 0;
                        isSpecial = true;
                        break;
                    }
                    prev = 1;
                } else {
                    prev = 0;
                }
            }

            if (!isSpecial) {
                specialBits[i + 1] = 0;
            }
        }

        while (q-- > 0) {
            int l = sc.nextInt();
            int r = sc.nextInt();
            int ans = 0;

            for (int i = l; i <= r; ++i) {
                if (specialBits[i] == 1) {
                    ans++;
                }
            }

            System.out.println(ans);
        }

        sc.close();
    }
}


// APPROACH NO 2 :
// NO TLE
import java.util.*;

public class Main {
    static final int N = 100010;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int q = sc.nextInt();

        int[] specialBits = new int[n + 2];
        int[] pre = new int[n + 2];

        int prev = 0;

        for (int i = 0; i < n; ++i) {
            int x = sc.nextInt();

            for (int j = 0; j < 32; ++j) {
                if ((x & (1 << j)) != 0) {
                    if (prev == 1) {
                        specialBits[i + 1] = 1;
                        prev = 0;
                        break;
                    }
                    prev = 1;
                } else {
                    prev = 0;
                }
            }
        }

        // Precompute prefix sums
        for (int i = 1; i <= n; ++i) {
            pre[i] = pre[i - 1] + specialBits[i];
        }

        // Answer queries in O(1) each
        while (q-- > 0) {
            int l = sc.nextInt();
            int r = sc.nextInt();

            System.out.println(pre[r] - pre[l - 1]);
        }

        sc.close();
    }
}
