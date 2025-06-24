import java.io.*;
import java.util.*;

public class Main {
    static int t, n, k;
    static String[] arr = new String[510];
    static int[][] dp = new int[510][1 << 10];

    static int rec(int index, int mask) {
        if (index == n) return 0;

        if (dp[index][mask] != -1) return dp[index][mask];

        int ans = (int) 1e9;
        for (int i = 0; i < arr[index].length(); i++) {
            if (arr[index].charAt(i) == '0') continue;

            if ((mask & (1 << i)) != 0) {
                ans = Math.min(ans, rec(index + 1, mask));
            } else {
                ans = Math.min(ans, 1 + rec(index + 1, mask | (1 << i)));
            }
        }

        return dp[index][mask] = ans;
    }

    public static void main(String[] args) throws IOException {
        FastReader sc = new FastReader();
        t = sc.nextInt();

        while (t-- > 0) {
            for (int[] row : dp) Arrays.fill(row, -1);

            n = sc.nextInt();
            k = sc.nextInt(); // unused directly

            for (int i = 0; i < n; i++) arr[i] = sc.next();

            System.out.println(rec(0, 0));
        }
    }

    // FastReader class for efficient input
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }
}
