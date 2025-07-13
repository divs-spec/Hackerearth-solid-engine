import java.io.*;
import java.util.*;

public class Main {
    static final int MAXN = (int) 1e6 + 17;
    static int[] a = new int[MAXN];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken()) - 1;
            int r = Integer.parseInt(st.nextToken());

            if (t <= 3) {
                int x = Integer.parseInt(st.nextToken());
                for (int i = l; i < r; i++) {
                    if (t == 1) {
                        a[i] |= x;
                    } else if (t == 2) {
                        a[i] &= x;
                    } else {
                        a[i] ^= x;
                    }
                }
            } else if (t == 4) {
                long sum = 0;
                for (int i = l; i < r; i++) {
                    sum += a[i];
                }
                out.println(sum);
            } else if (t == 5) {
                int xor = 0;
                for (int i = l; i < r; i++) {
                    xor ^= a[i];
                }
                out.println(xor);
            }
        }

        out.flush();
    }
}
