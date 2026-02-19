import java.io.*;
import java.util.*;

public class Main {

    // ---------- Fast Input ----------
    static class FastScanner {
        private final byte[] data;
        private int idx = 0;

        FastScanner() throws IOException {
            data = System.in.readAllBytes();
        }

        long nextLong() {
            long num = 0;
            int sign = 1;
            while (idx < data.length && (data[idx] == ' ' || data[idx] == '\n' || data[idx] == '\r')) idx++;
            if (data[idx] == '-') {
                sign = -1;
                idx++;
            }
            while (idx < data.length && data[idx] >= '0') {
                num = num * 10 + (data[idx] - '0');
                idx++;
            }
            return num * sign;
        }

        int nextInt() {
            return (int) nextLong();
        }
    }

    // ---------- Segment Tree ----------
    static class SegTree {
        int n;
        long[] t;

        SegTree(int n) {
            this.n = n;
            t = new long[2 * n + 5];
        }

        void modify(int p, long value) {
            p += n;
            t[p] = value;
            while (p > 1) {
                p >>= 1;
                t[p] = t[p << 1] + t[p << 1 | 1];
            }
        }

        long query(int l, int r) {
            r++;
            long res = 0;
            for (l += n, r += n; l < r; l >>= 1, r >>= 1) {
                if ((l & 1) == 1) res += t[l++];
                if ((r & 1) == 1) res += t[--r];
            }
            return res;
        }
    }

    // ---------- Query ----------
    static class Query {
        int l, r, x, y, ind;
    }

    static int S;

    static boolean cmp(Query A, Query B) {
        if (A.l / S != B.l / S) return A.l < B.l;
        if ((A.l / S) % 2 == 1) return A.r > B.r;
        return A.r < B.r;
    }

    // ---------- Solve ----------
    static void solve(FastScanner fs) {
        int n = fs.nextInt();

        long[] a = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = fs.nextLong();
        }

        SegTree st = new SegTree(n + 5);
        S = (int) Math.sqrt(n);

        int q = fs.nextInt();
        Query[] Q = new Query[q];
        for (int i = 0; i < q; i++) {
            Q[i] = new Query();
            Q[i].l = fs.nextInt();
            Q[i].r = fs.nextInt();
            Q[i].x = fs.nextInt();
            Q[i].y = fs.nextInt();
            Q[i].ind = i;
        }

        Arrays.sort(Q, (A, B) -> {
    if (A.l / S != B.l / S)
        return Integer.compare(A.l, B.l);

    if ((A.l / S) % 2 == 1)
        return Integer.compare(B.r, A.r);

    return Integer.compare(A.r, B.r);
    });


        long[] ans = new long[q];
        long[] arr = new long[n + 1];
        HashMap<Long, Integer> freq = new HashMap<>();

        // update function (same logic as lambda in C++)
        class Updater {
            void apply(int idx, int delta) {
                long val = a[idx];
                int f = freq.getOrDefault(val, 0);

                arr[f] ^= val;
                st.modify(f, arr[f]);

                f += delta;
                freq.put(val, f);

                arr[f] ^= val;
                st.modify(f, arr[f]);
            }
        }

        Updater updater = new Updater();

        int curL = Q[0].l;
        int curR = Q[0].l - 1;

        for (int i = 0; i < q; i++) {
            while (curL > Q[i].l) updater.apply(--curL, 1);
            while (curR < Q[i].r) updater.apply(++curR, 1);
            while (curL < Q[i].l) updater.apply(curL++, -1);
            while (curR > Q[i].r) updater.apply(curR--, -1);

            ans[Q[i].ind] = st.query(Q[i].x, Q[i].y);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            sb.append(ans[i]).append('\n');
        }
        System.out.print(sb);
    }

    // ---------- Main ----------
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int T = fs.nextInt();
        while (T-- > 0) {
            solve(fs);
        }
    }
}
