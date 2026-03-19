import java.io.*;
import java.util.*;

public class TestClass {

    static class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        FastScanner(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreElements())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = fs.nextInt();
        int MAX = 100000;

        int[] freq = new int[MAX + 1];

        for (int i = 0; i < N; i++) {
            int h = fs.nextInt();
            freq[h]++;
        }

        // ans[k] = number of heights divisible by k
        int[] ans = new int[MAX + 1];

        // Precompute using multiples
        for (int k = 1; k <= MAX; k++) {
            for (int multiple = k; multiple <= MAX; multiple += k) {
                ans[k] += freq[multiple];
            }
        }

        int q = fs.nextInt();

        while (q-- > 0) {
            int k = fs.nextInt();
            out.println(ans[k]);
        }

        out.flush();
    }
}
