import java.io.*;
import java.util.*;

public class Main {

    // Segment tree arrays
    static int[] segtree;
    static int[] lazy;

    // Build segment tree
    static void build(int[] arr, int pos, int l, int r) {
        if (l == r) {
            segtree[pos] = arr[l];
            return;
        }
        int mid = (l + r) / 2;
        build(arr, pos * 2 + 1, l, mid);
        build(arr, pos * 2 + 2, mid + 1, r);
        segtree[pos] = Math.min(segtree[pos * 2 + 1], segtree[pos * 2 + 2]);
    }

    // Range update with lazy propagation
    static void update(int pos, int l, int r, int ql, int qr, int val) {
        if (lazy[pos] != 0) {
            segtree[pos] += lazy[pos];
            if (l != r) {
                lazy[pos * 2 + 1] += lazy[pos];
                lazy[pos * 2 + 2] += lazy[pos];
            }
            lazy[pos] = 0;
        }

        if (ql > r || qr < l) return;

        if (ql <= l && r <= qr) {
            segtree[pos] += val;
            if (l != r) {
                lazy[pos * 2 + 1] += val;
                lazy[pos * 2 + 2] += val;
            }
            return;
        }

        int mid = (l + r) / 2;
        update(pos * 2 + 1, l, mid, ql, qr, val);
        update(pos * 2 + 2, mid + 1, r, ql, qr, val);
        segtree[pos] = Math.min(segtree[pos * 2 + 1], segtree[pos * 2 + 2]);
    }

    // Range minimum query
    static int query(int pos, int l, int r, int ql, int qr) {
        if (lazy[pos] != 0) {
            segtree[pos] += lazy[pos];
            if (l != r) {
                lazy[pos * 2 + 1] += lazy[pos];
                lazy[pos * 2 + 2] += lazy[pos];
            }
            lazy[pos] = 0;
        }

        if (ql > r || qr < l) return Integer.MAX_VALUE;
        if (ql <= l && r <= qr) return segtree[pos];

        int mid = (l + r) / 2;
        return Math.min(
                query(pos * 2 + 1, l, mid, ql, qr),
                query(pos * 2 + 2, mid + 1, r, ql, qr)
        );
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        String s = fs.nextLine().trim();
        int n = s.length();

        int[] prefix = new int[n];
        int cur = 0;
        for (int i = 0; i < n; i++) {
            cur += (s.charAt(i) == '(' ? 1 : -1);
            prefix[i] = cur;
        }

        segtree = new int[1 << 22];
        lazy = new int[1 << 22];

        build(prefix, 0, 0, n - 1);

        int m = fs.nextInt();
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < m; i++) {
            char type = fs.next().charAt(0);
            int idx = fs.nextInt() - 1;

            if (type == 'C') {
                if (s.charAt(idx) == ')') {
                    s = s.substring(0, idx) + "(" + s.substring(idx + 1);
                    update(0, 0, n - 1, idx, n - 1, 2);
                } else {
                    s = s.substring(0, idx) + ")" + s.substring(idx + 1);
                    update(0, 0, n - 1, idx, n - 1, -2);
                }
            } else { // Query
                int zero = (idx > 0) ? query(0, 0, n - 1, idx - 1, idx - 1) : 0;

                int lo = idx - 1, hi = n;
                while (hi - lo > 1) {
                    int mid = (lo + hi) / 2;
                    if (query(0, 0, n - 1, idx, mid) < zero)
                        hi = mid;
                    else
                        lo = mid;
                }

                if (hi != n) {
                    out.append(hi - idx).append('\n');
                } else {
                    lo = idx - 1;
                    hi = n;
                    while (hi - lo > 1) {
                        int mid = (lo + hi) / 2;
                        if (query(0, 0, n - 1, mid, n - 1) == zero)
                            lo = mid;
                        else
                            hi = mid;
                    }
                    out.append(lo - idx + 1).append('\n');
                }
            }
        }

        System.out.print(out);
    }

    // Fast input
    static class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        FastScanner(InputStream in) {
            br = new BufferedReader(new InputStreamReader(in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        String nextLine() throws IOException {
            return br.readLine();
        }
    }
}
