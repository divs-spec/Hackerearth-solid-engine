import java.io.*;
import java.util.*;

public class Main {

    /** parity of number-of-set-bits: 0 = even, 1 = odd */
    private static int parity(int x) {
        return Integer.bitCount(x) & 1;
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int T = fs.nextInt();
        while (T-- > 0) {
            int N = fs.nextInt();
            int[] a = new int[N];
            for (int i = 0; i < N; ++i) a[i] = fs.nextInt();

            /* ---------- suffix OR ---------- */
            int[] suf = new int[N + 1];           // suf[N] = 0
            for (int i = N - 1; i >= 0; --i)
                suf[i] = suf[i + 1] | a[i];

            long ans = 0;

            /* ---------- main counting loop ---------- */
            for (int i = 0; i < N; ++i) {
                int curOR = 0;
                int j = i;
                while (true) {
                    curOR |= a[j];
                    if (parity(curOR) == 1)            // odd -> count
                        ++ans;

                    /* will the OR ever change again? */
                    if ( (curOR | suf[j + 1]) == curOR ) {
                        if (parity(curOR) == 1)
                            ans += (N - 1) - j;        // all further ends
                        break;                         // done for this i
                    }
                    ++j;                               // 30 times total
                }
            }

            out.append(ans).append('\n');
        }
        System.out.print(out);
    }

    /* -------- fast input helper -------- */
    private static class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int len = 0, ptr = 0;
        private final InputStream in;
        FastScanner(InputStream in) { this.in = in; }
        private int readByte() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        int nextInt() throws IOException {
            int c, s = 1, x = 0;
            do { c = readByte(); } while (c <= ' ');
            if (c == '-') { s = -1; c = readByte(); }
            while (c > ' ') { x = x * 10 + (c - '0'); c = readByte(); }
            return x * s;
        }
    }
}
