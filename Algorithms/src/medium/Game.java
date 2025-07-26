import java.util.*;

public class Main {
    static final int N = 3234567;
    static int[] allx = new int[N];
    static int[] ax = new int[N];
    static int[] ay = new int[N];
    static int[] have = new int[N];

    public static void solve(Scanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();

        int cn = 0;
        allx[cn++] = 1;
        allx[cn++] = n;

        for (int i = 0; i < m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            for (int dx = -3; dx <= 3; dx++) {
                int cx = x + dx;
                if (cx >= 1 && cx <= n) {
                    allx[cn++] = cx;
                }
            }
            ax[i] = x;
            ay[i] = y;
        }

        // Sort and unique
        Arrays.sort(allx, 0, cn);
        int[] temp = new int[cn];
        int len = 0;
        temp[len++] = allx[0];
        for (int i = 1; i < cn; i++) {
            if (allx[i] != allx[i - 1]) {
                temp[len++] = allx[i];
            }
        }
        allx = temp;
        cn = len;

        Arrays.fill(have, 0, cn, 0);
        for (int i = 0; i < m; i++) {
            int w = Arrays.binarySearch(allx, 0, cn, ax[i]);
            have[w] |= 1 << (ay[i] - 1);
        }

        int start = 7 ^ have[0];  // 7 = 111b
        int ans = 0;

        for (int i = 0; i < cn; i++) {
            start = ((start << 1) | (start >> 1) | start) & 7;
            start &= 7 ^ have[i];
            if (start != 0) ans = allx[i];
        }

        System.out.println(ans);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            solve(sc);
        }
    }
}
