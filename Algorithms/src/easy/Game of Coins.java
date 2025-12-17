import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int T = fs.nextInt();
        StringBuilder out = new StringBuilder();

        while (T-- > 0) {
            int N = fs.nextInt();
            int X = fs.nextInt();
            int xor = 0;

            for (int i = 0; i < N; i++) {
                int a = fs.nextInt();
                xor ^= (a % (X + 1));
            }

            out.append(xor == 0 ? "Bob" : "Alice").append('\n');
        }
        System.out.print(out);
    }

    // Fast input
    static class FastScanner {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int nextInt() throws IOException {
            while (st == null || !st.hasMoreElements())
                st = new StringTokenizer(br.readLine());
            return Integer.parseInt(st.nextToken());
        }
    }
}
