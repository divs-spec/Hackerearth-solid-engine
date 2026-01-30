import java.io.*;
import java.util.*;

public class Main {

    static final int MAX = 100002;
    static final int MAGIC = 333;
    static final int MM = 500;

    static int n, q;

    static int[] v = new int[MAX];
    static int[][] u = new int[MAGIC][MM];
    static int[] sz = new int[MAGIC];

    static boolean[] ex = new boolean[MAX];

    static ArrayList<Integer> vv = new ArrayList<>();
    static ArrayList<Integer>[] idx = new ArrayList[MAX];

    static class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in;

        FastScanner(InputStream in) {
            this.in = in;
        }

        int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') ;
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }

    static int lowerBound(ArrayList<Integer> arr, int x) {
        int l = 0, r = arr.size();
        while (l < r) {
            int m = (l + r) >>> 1;
            if (arr.get(m) < x) l = m + 1;
            else r = m;
        }
        return l;
    }

    static int upperBound(ArrayList<Integer> arr, int x) {
        int l = 0, r = arr.size();
        while (l < r) {
            int m = (l + r) >>> 1;
            if (arr.get(m) <= x) l = m + 1;
            else r = m;
        }
        return l;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        n = fs.nextInt();
        q = fs.nextInt();

        for (int i = 0; i < MAX; i++) idx[i] = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int a = fs.nextInt();
            v[i] = a;
            vv.add(a);
        }

        // Coordinate compression
        Collections.sort(vv);
        for (int i = 0; i < n; i++) {
            v[i] = Collections.binarySearch(vv, v[i]);
            idx[v[i]].add(i);
        }

        // Build sqrt blocks
        for (int i = 0; i < n; i += MAGIC) {
            Arrays.fill(ex, false);
            int block = i / MAGIC;
            for (int j = i; j < n; j++) {
                if (ex[v[j]]) continue;
                ex[v[j]] = true;
                u[block][sz[block]++] = v[j];
                if (sz[block] == MM) break;
            }
        }

        int last = 0;
        StringBuilder out = new StringBuilder();

        while (q-- > 0) {
            int a = fs.nextInt();
            int b = fs.nextInt();

            int l = (a + last) % n;
            int r = (b + last) % n;

            if (l > r) {
                int t = l;
                l = r;
                r = t;
            }

            int belong = l / MAGIC;
            TreeSet<Integer> set = new TreeSet<>();
            boolean bad = false;

            for (int i = 0; i < sz[belong]; i++) {
                int val = u[belong][i];
                ArrayList<Integer> list = idx[val];

                int lef = lowerBound(list, l);
                int rig = upperBound(list, r);
                int oc = rig - lef;

                if (oc == 0) continue;
                if (set.contains(oc)) {
                    bad = true;
                    break;
                }
                set.add(oc);
            }

            if (bad) {
                out.append("0\n");
                last = 0;
                continue;
            }

            if (set.size() == 1) {
                out.append("-1\n");
                last = -1;
                continue;
            }

            int ans = Integer.MAX_VALUE;
            int prev = -1;
            for (int x : set) {
                if (prev != -1) ans = Math.min(ans, x - prev);
                prev = x;
            }

            out.append(ans).append('\n');
            last = ans;
        }

        System.out.print(out);
    }
}
