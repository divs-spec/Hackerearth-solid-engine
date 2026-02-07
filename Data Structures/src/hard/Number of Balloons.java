import java.io.*;
import java.util.*;

public class Main {

    static int n, k, q;

    /* ================= Persistent Segment Tree ================= */
    static class Segment {
        static final int MAXN = 10_000_000; // same as C++
        static int eof = 1;
        static int[] L = new int[MAXN];
        static int[] R = new int[MAXN];
        static int[] seg = new int[MAXN];

        static int add(int v, int x, int root, int st, int en) {
            if (eof >= MAXN)
                throw new RuntimeException("Segment tree overflow");

            seg[eof] = seg[root] + x;
            L[eof] = L[root];
            R[eof] = R[root];
            root = eof++;

            if (en - st < 2)
                return root;

            int mid = (st + en) >> 1;
            if (v < mid)
                L[root] = add(v, x, L[root], st, mid);
            else
                R[root] = add(v, x, R[root], mid, en);

            return root;
        }

        static int add(int v, int x, int root) {
            return add(v, x, root, 0, n);
        }

        static int get(int l, int r, int root, int st, int en) {
            if (l <= st && en <= r)
                return seg[root];
            if (r <= st || en <= l)
                return 0;

            int mid = (st + en) >> 1;
            return get(l, r, L[root], st, mid)
                 + get(l, r, R[root], mid, en);
        }

        static int get(int l, int r, int root) {
            return get(l, r, root, 0, n);
        }
    }

    /* ================= Problem Data ================= */
    static final int MAXN = 100_000 + 10;
    static int[] ar = new int[MAXN];
    static int[] l = new int[MAXN];
    static int[] r = new int[MAXN];
    static int[] root = new int[MAXN];
    static ArrayList<Integer>[] vc = new ArrayList[MAXN];

    static void init(FastScanner fs) throws IOException {
        n = fs.nextInt();
        k = fs.nextInt();
        q = fs.nextInt();

        ArrayList<Integer> compress = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ar[i] = fs.nextInt();
            compress.add(ar[i]);
        }

        Collections.sort(compress);
        compress = new ArrayList<>(new LinkedHashSet<>(compress));

        for (int i = 0; i < n; i++) {
            ar[i] = Collections.binarySearch(compress, ar[i]);
            vc[ar[i]] = new ArrayList<>();
        }

        for (int i = 0; i < q; i++) {
            l[i] = fs.nextInt();
            r[i] = fs.nextInt();
        }
    }

    static void addValue(int v, int x, IntWrapper node) {
        if (vc[v].size() >= k) {
            int idx = vc[v].get(vc[v].size() - k);
            node.value = Segment.add(idx, x, node.value);
        }
        if (vc[v].size() > k) {
            int idx = vc[v].get(vc[v].size() - k - 1);
            node.value = Segment.add(idx, -x, node.value);
        }
    }

    static void build() {
        IntWrapper node = new IntWrapper(0);
        for (int i = 0; i < n; i++) {
            addValue(ar[i], -1, node);
            vc[ar[i]].add(i);
            addValue(ar[i], +1, node);
            root[i] = node.value;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        init(fs);
        build();

        int ans = 0;
        for (int i = 0; i < q; i++) {
            l[i] = (l[i] + ans) % n;
            r[i] = (r[i] + ans) % n;
            if (l[i] > r[i]) {
                int tmp = l[i];
                l[i] = r[i];
                r[i] = tmp;
            }
            ans = Segment.get(l[i], r[i] + 1, root[r[i]]);
            out.append(ans).append('\n');
        }

        System.out.print(out);
    }

    /* ================= Utilities ================= */
    static class IntWrapper {
        int value;
        IntWrapper(int v) { value = v; }
    }

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
            int c, sign = 1, val = 0;
            do {
                c = read();
            } while (c <= ' ');

            if (c == '-') {
                sign = -1;
                c = read();
            }
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }
}
