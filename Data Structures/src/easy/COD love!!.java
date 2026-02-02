import java.io.*;
import java.util.*;

public class Main {

    static final int MAX = 210021;

    static int[][] bit = new int[26][MAX];
    static int[][] pos = new int[26][MAX];
    static int[] fq = new int[26];
    static char[] str;

    static void update(int ind1, int ind2, int val) {
        while (ind2 < MAX) {
            bit[ind1][ind2] += val;
            ind2 += ind2 & -ind2;
        }
    }

    static int dis(int ind1, int ind2) {
        int ret = 0;
        while (ind2 > 0) {
            ret += bit[ind1][ind2];
            ind2 -= ind2 & -ind2;
        }
        return ret;
    }

    static int solve(int a, int b) {
        int prev, ans = 0, total = 0;
        do {
            prev = ans;
            ans = dis(a, b + ans);
            total += Math.abs(ans - prev);
        } while (prev != ans);
        return total;
    }

    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner(System.in);

        String s = fs.next();
        int len = s.length();

        str = new char[len + 1];
        for (int i = 1; i <= len; i++) {
            str[i] = s.charAt(i - 1);
            int key = str[i] - 'a';
            pos[key][++fq[key]] = i;
        }

        int q = fs.nextInt();

        while (q-- > 0) {
            int x = fs.nextInt();
            char ch = fs.next().charAt(0);

            int idx = ch - 'a';
            int add = solve(idx, x);

            int realPos = pos[idx][x + add];
            str[realPos] = '\0'; // equivalent to -1 in C
            update(idx, x + add, 1);
        }

        StringBuilder out = new StringBuilder();
        for (int i = 1; i <= len; i++) {
            if (str[i] != '\0') {
                out.append(str[i]);
            }
        }

        System.out.print(out.toString());
    }

    // Fast input
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream in) {
            br = new BufferedReader(new InputStreamReader(in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
