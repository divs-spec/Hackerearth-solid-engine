import java.io.*;
import java.util.*;

public class Main {

    static class Node {
        long cnt, lcnt, rcnt;
        int left, right;
        boolean isGood;

        Node() {
            cnt = lcnt = rcnt = 0;
            left = right = 0;
            isGood = true;
        }

        Node(int u) {
            cnt = lcnt = rcnt = (u > 0 ? 1 : 0);
            left = right = u;
            isGood = true;
        }

        Node merge(Node a) { // a is right node
            Node f = new Node();
            f.cnt = this.cnt + a.cnt;

            if (this.right != 0 && a.left != 0 && this.right <= a.left) {
                f.cnt += this.rcnt * a.lcnt;
                f.isGood = this.isGood && a.isGood;
                f.lcnt = (this.isGood ? 1 : 0) * a.lcnt;
                f.rcnt = (a.isGood ? 1 : 0) * this.rcnt;
            } else {
                f.isGood = false;
            }

            f.lcnt += this.lcnt;
            f.rcnt += a.rcnt;
            f.left = this.left;
            f.right = a.right;

            return f;
        }

        void assign(int u) {
            cnt = lcnt = rcnt = (u > 0 ? 1 : 0);
            left = right = u;
            isGood = true;
        }
    }

    static class SegmentTree {
        int n, N;
        Node[] seg;

        SegmentTree(int n) {
            this.n = n;
            this.N = 4 * n;
            seg = new Node[N + 5];
            for (int i = 0; i < seg.length; i++) seg[i] = new Node();
        }

        void update(int x, int s, int e, int p, int u) {
            if (e < s || s > p || e < p) return;

            if (s == e) {
                seg[x].assign(u);
                return;
            }

            int m = (s + e) >> 1;
            update(x << 1, s, m, p, u);
            update(x << 1 | 1, m + 1, e, p, u);

            seg[x] = seg[x << 1].merge(seg[x << 1 | 1]);
        }

        Node query(int x, int s, int e, int l, int r) {
            if (l > e || r < s || s > e) return new Node();

            if (l <= s && e <= r) return seg[x];

            int m = (s + e) >> 1;
            Node left = query(x << 1, s, m, l, r);
            Node right = query(x << 1 | 1, m + 1, e, l, r);

            return left.merge(right);
        }

        Node query(int l, int r) {
            if (l > r) return new Node();
            return query(1, 1, n, l, r);
        }

        void update(int p, int u) {
            update(1, 1, n, p, u);
        }
    }

    // Fast IO
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreElements())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public static void solve(FastScanner fs) throws Exception {
        int n = fs.nextInt();
        String s = fs.next();

        SegmentTree segtree = new SegmentTree(n);

        for (int i = 0; i < n; i++) {
            segtree.update(i + 1, s.charAt(i) - 'a' + 1);
        }

        int q = fs.nextInt();

        StringBuilder out = new StringBuilder();

        while (q-- > 0) {
            int x = fs.nextInt();
            int y = fs.nextInt();
            out.append(segtree.query(x, y).cnt).append('\n');
        }

        System.out.print(out);
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int test = fs.nextInt();
        while (test-- > 0) solve(fs);
    }
}
