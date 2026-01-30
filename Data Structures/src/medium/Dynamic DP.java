import java.io.*;
import java.util.*;

public class Main {

    static final long INF = (long) 1e18;

    static class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in = System.in;

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
            do {
                c = read();
            } while (c <= ' ');

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

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();

        int n = fs.nextInt();

        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = fs.nextInt();

        int[] l = new int[n];
        int[] r = new int[n];
        for (int i = 0; i < n; i++) {
            l[i] = fs.nextInt() - 1;
            r[i] = fs.nextInt() - 1;
        }

        long[] b = new long[n];

        ArrayList<Long>[] add = new ArrayList[n];
        ArrayList<Long>[] rem = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            add[i] = new ArrayList<>();
            rem[i] = new ArrayList<>();
        }

        // multiset replacement
        TreeMap<Long, Integer> multiset = new TreeMap<>();

        for (int i = n - 1; i >= 0; i--) {

            for (long x : add[i]) {
                multiset.put(x, multiset.getOrDefault(x, 0) + 1);
            }

            if (i != n - 1) {
                b[i] = multiset.isEmpty() ? INF : multiset.firstKey();
            }

            if (i > 0) {
                long val = b[i] + a[i];
                add[r[i]].add(val);
                rem[l[i]].add(val);
            }

            for (long x : rem[i]) {
                int cnt = multiset.get(x);
                if (cnt == 1) multiset.remove(x);
                else multiset.put(x, cnt - 1);
            }
        }

        for (int i = 1; i < n; i++) {
            b[i] = Math.min(b[i], b[i - 1]);
        }

        int q = fs.nextInt();
        StringBuilder sb = new StringBuilder();

        while (q-- > 0) {
            int x = fs.nextInt();
            long y = fs.nextLong();
            sb.append(b[x - 1] + y).append('\n');
        }

        System.out.print(sb.toString());
    }
}
