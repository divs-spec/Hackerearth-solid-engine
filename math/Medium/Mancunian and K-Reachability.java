import java.io.*;
import java.util.*;

public class Main {

    static final int N = 10000007;
    static int[] arr = new int[N];
    static int k;

    static boolean reachable(int a, int b) {
        int cnt = 0;

        for (int i = 0; i < 8; i++) {
            if (b == 0 && a != 0) return false;

            if (a % 10 != b % 10) cnt++;

            a /= 10;
            b /= 10;
        }

        return cnt <= k;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        for (int i = 1; i < N; i++) {
            arr[i] = reachable(i, n) ? 1 : 0;
        }

        for (int i = 1; i < N; i++) {
            arr[i] += arr[i - 1];
        }

        StringBuilder out = new StringBuilder();

        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            out.append(arr[r] - arr[l - 1]).append('\n');
        }

        System.out.print(out);
    }
}
