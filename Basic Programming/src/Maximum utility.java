import java.io.*;
import java.util.*;

public class Main {
    static int N = 200005;
    static int[] a = new int[N];
    static int[] b = new int[N];
    static int n, k;

    static long f(int x) {
        long res = 0;
        for (int i = 1; i <= n; i++) {
            if ((x | a[i]) == x) {
                res += b[i];
            }
        }
        return res;
    }

    static void solve(BufferedReader br) throws IOException {
        String[] nk = br.readLine().split(" ");
        n = Integer.parseInt(nk[0]);
        k = Integer.parseInt(nk[1]);

        String[] aLine = br.readLine().split(" ");
        String[] bLine = br.readLine().split(" ");

        for (int i = 1; i <= n; i++) {
            a[i] = Integer.parseInt(aLine[i - 1]);
        }
        for (int i = 1; i <= n; i++) {
            b[i] = Integer.parseInt(bLine[i - 1]);
        }

        long ans = f(k);
        int p = 0;

        for (int i = 30; i >= 0; i--) {
            int bit = (1 << i);
            if ((k & bit) != 0) {
                ans = Math.max(ans, f(p + bit - 1));
                p += bit;
            }
        }

        System.out.println(ans);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            solve(br);
        }
    }
}
