import java.io.*;
import java.util.*;

public class Main {

    // Maximum nodes = N * logN ≈ 100000 * 25
    static final int MAXNODE = 100000 * 25;

    static class Node {
        int l, r, sz;
    }

    static Node[] seg = new Node[MAXNODE];
    static int nxt = 0;

    static {
        for (int i = 0; i < MAXNODE; i++) seg[i] = new Node();
    }

    static int build(int begin, int end) {
        int root = nxt++;
        if (begin == end) return root;
        int mid = (begin + end) >> 1;
        seg[root].l = build(begin, mid);
        seg[root].r = build(mid + 1, end);
        return root;
    }

    static int update(int root, int begin, int end, int x, int v) {
        if (x < begin || x > end) return root;

        seg[nxt].l = seg[root].l;
        seg[nxt].r = seg[root].r;
        seg[nxt].sz = seg[root].sz;
        root = nxt++;

        if (begin == end) {
            seg[root].sz += v;
        } else {
            int mid = (begin + end) >> 1;
            seg[root].l = update(seg[root].l, begin, mid, x, v);
            seg[root].r = update(seg[root].r, mid + 1, end, x, v);
            seg[root].sz = seg[seg[root].l].sz + seg[seg[root].r].sz;
        }
        return root;
    }

    static int query(int root, int begin, int end, int k) {
        if (k > seg[root].sz) return -1;

        while (begin != end) {
            int mid = (begin + end) >> 1;
            int lsz = seg[seg[root].l].sz;
            if (k <= lsz) {
                root = seg[root].l;
                end = mid;
            } else {
                root = seg[root].r;
                begin = mid + 1;
                k -= lsz;
            }
        }
        return begin;
    }

    // ================= Main =================
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int N = fs.nextInt();
        int Q = fs.nextInt();

        int[] A = new int[N + 1];
        Pair[] B = new Pair[N + 1];

        for (int i = 1; i <= N; i++) {
            A[i] = fs.nextInt();
            B[i] = new Pair(-A[i], i);
        }

        Arrays.sort(B, 1, N + 1);

        int[] tree = new int[N + 1];
        tree[0] = build(1, N);

        for (int i = 1; i <= N; i++) {
            tree[i] = update(tree[i - 1], 1, N, B[i].idx, 1);
        }

        while (Q-- > 0) {
            int a = fs.nextInt();
            int b = fs.nextInt();

            int idx = upperBound(B, N, -a + 1) - 1;
            int pos = query(tree[idx], 1, N, b);

            out.append(pos == -1 ? -1 : A[pos]).append('\n');
        }

        System.out.print(out);
    }

    // ================= Utilities =================
    static class Pair implements Comparable<Pair> {
        int val, idx;
        Pair(int v, int i) { val = v; idx = i; }
        public int compareTo(Pair o) {
            if (val != o.val) return Integer.compare(val, o.val);
            return Integer.compare(idx, o.idx);
        }
    }

    static int upperBound(Pair[] arr, int n, int val) {
        int l = 1, r = n + 1;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (arr[m].val < val) l = m + 1;
            else r = m;
        }
        return l;
    }

    // ================= Fast Scanner =================
    static class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in;

        FastScanner(InputStream in) { this.in = in; }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c, sgn = 1, res = 0;
            do c = read(); while (c <= ' ');
            if (c == '-') { sgn = -1; c = read(); }
            while (c > ' ') {
                res = res * 10 + c - '0';
                c = read();
            }
            return res * sgn;
        }
    }
}
