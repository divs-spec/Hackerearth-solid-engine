import java.io.*;
import java.util.*;

public class Main {

    static class FastScanner {
        private BufferedReader br;
        private StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int t = fs.nextInt();

        while (t-- > 0) {

            int n = fs.nextInt();
            int[] A = new int[n];

            for (int i = 0; i < n; i++) {
                A[i] = fs.nextInt();
            }

            int c = 0;

            for (int i = 0; i < n; i++) {
                while (A[i] % 2 == 0) {
                    c++;
                    A[i] /= 2;
                }
            }

            if (c % 2 == 0)
                out.println("Alan");
            else
                out.println("Charlie");
        }

        out.flush();
    }
}
