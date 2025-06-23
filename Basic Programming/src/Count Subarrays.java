import java.io.*;
import java.util.*;

public class Main {

    // parity of number-of-set-bits: 0 = even, 1 = odd
    private static int parity(int x) {
        // Java 17: popcount is Integer.bitCount
        return Integer.bitCount(x) & 1;
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int T = fs.nextInt();
        while (T-- > 0) {
            int N = fs.nextInt();

            long ans = 0;
            long[] cnt = {1, 0};          // prefix 0 occurs once (empty prefix)
            int pref = 0;                 // current prefix XOR parity

            for (int i = 0; i < N; ++i) {
                int ai = fs.nextInt();
                pref ^= parity(ai);       // update prefix parity (0/1)

                ans += cnt[1 - pref];     // sub-arrays ending here with XOR = 1
                ++cnt[pref];              // record this prefix
            }
            out.append(ans).append('\n');
        }
        System.out.print(out);
    }

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
            int c, sign = 1, val = 0;
            do { c = readByte(); } while (c <= ' ');
            if (c == '-') { sign = -1; c = readByte(); }
            for (; c > ' '; c = readByte())
                val = val * 10 + (c - '0');
            return val * sign;
        }
    }
}
