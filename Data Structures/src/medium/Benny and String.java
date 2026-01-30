import java.io.*;
import java.util.*;

public class Main {

    static final int T = 1 << 17;
    static final int ALPHA = 26;
    static final int N = 123456;

    static class FastInput {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in = System.in;

        int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextChar() throws IOException {
            int c;
            while ((c = read()) <= 32) ;
            return c;
        }

        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= 32) ;
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int val = 0;
            while (c > 32) {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }

    static class Node {
        int[] longest = new int[ALPHA];
        int first, firstCount;
        int last, lastCount;
        int len;

        Node() {}

        static Node createLetter(int c) {
            Node n = new Node();
            n.longest[c] = 1;
            n.first = n.last = c;
            n.len = n.firstCount = n.lastCount = 1;
            return n;
        }
    }

    static Node merge(Node a, Node b) {
        if (a.len == 0) return b;
        if (b.len == 0) return a;

        Node r = new Node();
        for (int i = 0; i < ALPHA; i++)
            r.longest[i] = Math.max(a.longest[i], b.longest[i]);

        r.len = a.len + b.len;
        r.first = a.first;
        r.firstCount = a.firstCount +
                ((a.firstCount == a.len && b.first == a.first) ? b.firstCount : 0);

        r.last = b.last;
        r.lastCount = b.lastCount +
                ((b.lastCount == b.len && b.last == a.last) ? a.lastCount : 0);

        if (a.last == b.first) {
            r.longest[a.last] = Math.max(r.longest[a.last],
                    a.lastCount + b.firstCount);
        }
        return r;
    }

    static Node[] tr = new Node[2 * T + 5];
    static int[] add = new int[2 * T + 5];

    static int[] qa = new int[N];
    static int[] qb = new int[N];
    static int[] qc = new int[N];
    static char[] type = new char[N];

    static ArrayList<Integer>[] acc = new ArrayList[N];

    static Node processAdd(Node v, int delta) {
        if (delta == 0) return v;
        Node r = new Node();
        for (int i = 0; i < ALPHA; i++)
            r.longest[(i + delta) % ALPHA] = v.longest[i];

        r.first = (v.first + delta) % ALPHA;
        r.last = (v.last + delta) % ALPHA;
        r.len = v.len;
        r.firstCount = v.firstCount;
        r.lastCount = v.lastCount;
        return r;
    }

    static void relax(int v) {
        if (add[v] == 0) return;
        add[v << 1] += add[v];
        add[v << 1 | 1] += add[v];
        tr[v] = processAdd(tr[v], add[v]);
        add[v] = 0;
    }

    static Node collect(int v, int l, int r, int ql, int qr) {
        if (r <= ql || qr <= l) return new Node();
        if (ql <= l && r <= qr) {
            return processAdd(tr[v], add[v]);
        }
        relax(v);
        int m = (l + r) >> 1;
        return merge(
                collect(v << 1, l, m, ql, qr),
                collect(v << 1 | 1, m, r, ql, qr)
        );
    }

    static void makeAdd(int v, int l, int r, int ql, int qr, int delta) {
        if (r <= ql || qr <= l) return;
        if (ql <= l && r <= qr) {
            add[v] += delta;
            return;
        }
        relax(v);
        int m = (l + r) >> 1;
        makeAdd(v << 1, l, m, ql, qr, delta);
        makeAdd(v << 1 | 1, m, r, ql, qr, delta);
        tr[v] = merge(
                processAdd(tr[v << 1], add[v << 1]),
                processAdd(tr[v << 1 | 1], add[v << 1 | 1])
        );
    }

    static void setIt(int v, int l, int r, int pos, int val) {
        if (l + 1 == r) {
            tr[v] = Node.createLetter(val);
            add[v] = 0;
            return;
        }
        relax(v);
        int m = (l + r) >> 1;
        if (pos < m) setIt(v << 1, l, m, pos, val);
        else setIt(v << 1 | 1, m, r, pos, val);
        tr[v] = merge(
                processAdd(tr[v << 1], add[v << 1]),
                processAdd(tr[v << 1 | 1], add[v << 1 | 1])
        );
    }

    public static void main(String[] args) throws Exception {
        FastInput in = new FastInput();
        StringBuilder out = new StringBuilder();

        int n = in.nextInt();
        int q = in.nextInt();

        for (int i = 0; i < tr.length; i++) tr[i] = new Node();
        for (int i = 0; i < N; i++) acc[i] = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            setIt(1, 0, T, i, in.nextChar() - 'a');
        }

        for (int i = 0; i < q; i++) {
            char c = (char) in.nextChar();
            type[i] = c;
            if (c == '?') {
                qa[i] = in.nextInt() - 1;
                qb[i] = in.nextInt();
                qc[i] = in.nextChar() - 'a';
            } else if (c == '+') {
                qa[i] = in.nextInt() - 1;
                qb[i] = in.nextInt();
                qc[i] = in.nextInt() % ALPHA;
            } else if (c == '*') {
                qa[i] = in.nextInt() - 1;
                qc[i] = in.nextChar() - 'a';
            } else {
                qa[i] = in.nextInt() - 1;
                qc[i] = in.nextInt();
                acc[qc[i] - 1].add(i);
            }
        }

        for (int i = 0; i < q; i++) {
            for (int x : acc[i]) {
                qc[x] = collect(1, 0, T, qa[x], qa[x] + 1).first;
            }
            if (type[i] == '?') {
                out.append(collect(1, 0, T, qa[i], qb[i]).longest[qc[i]]).append('\n');
            } else if (type[i] == '+') {
                makeAdd(1, 0, T, qa[i], qb[i], qc[i]);
            } else {
                setIt(1, 0, T, qa[i], qc[i]);
            }
        }

        System.out.print(out);
    }
}
