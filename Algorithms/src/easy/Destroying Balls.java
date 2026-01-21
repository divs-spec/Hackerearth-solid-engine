import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        long startTime = System.nanoTime();

        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        long T = fs.nextLong();

        while (T-- > 0) {

            long n = fs.nextLong();
            long originalN = n;

            HashMap<Long, Long> freq = new HashMap<>();

            for (int i = 0; i < originalN; i++) {
                long x = fs.nextLong();
                freq.put(x, freq.getOrDefault(x, 0L) + 1);
            }

            boolean possible = true;

            while (n > 0) {
                if (freq.containsKey(n)) {
                    n -= freq.get(n);
                } else {
                    possible = false;
                    break;
                }
            }

            out.append(possible ? "YES\n" : "NO\n");
        }

        System.out.print(out.toString());

        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1e9;
        System.err.printf("Time Taken : %.6f secs%n", duration);
    }

    // Fast input reader (equivalent to fast I/O in C++)
    static class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        long nextLong() throws IOException {
            int c;
            while ((c = read()) <= ' ') {
                if (c == -1) return -1;
            }
            boolean neg = false;
            if (c == '-') {
                neg = true;
                c = read();
            }
            long val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return neg ? -val : val;
        }
    }
}
