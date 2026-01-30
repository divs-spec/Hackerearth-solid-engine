import java.io.*;
import java.util.*;

public class Main {

    static final int N = 300000 + 100;
    static final int B_SIZE = 400;
    static final int B_CNT = N / B_SIZE + 5;
    static final int INF = (int) 2e9 + (int) 1e6;

    static class Item implements Comparable<Item> {
        int id, x, p;

        Item(int id, int x, int p) {
            this.id = id;
            this.x = x;
            this.p = p;
        }

        @Override
        public int compareTo(Item o) {
            return (this.id - this.p) - (o.id - o.p);
        }
    }

    static class Query implements Comparable<Query> {
        int a, b, id;

        Query(int a, int b, int id) {
            this.a = a;
            this.b = b;
            this.id = id;
        }

        @Override
        public int compareTo(Query o) {
            return Integer.compare(this.a, o.a);
        }
    }

    static class Block {
        Item[] items = new Item[B_SIZE];
        int ptr;
        int[] prefixMax = new int[B_SIZE];
        int[] suffixMax = new int[B_SIZE];

        void init(int[] x, int[] p) {
            for (int i = 0; i < B_SIZE; i++) {
                items[i] = new Item(i, x[i], p[i]);
            }
            Arrays.sort(items);
            rebuild();
        }

        void rebuild() {
            for (int i = 0; i < B_SIZE; i++) {
                prefixMax[i] = items[i].x + items[i].id;
                if (i > 0) prefixMax[i] = Math.max(prefixMax[i], prefixMax[i - 1]);
            }
            for (int i = B_SIZE - 1; i >= 0; i--) {
                suffixMax[i] = items[i].x + items[i].p;
                if (i + 1 < B_SIZE) suffixMax[i] = Math.max(suffixMax[i], suffixMax[i + 1]);
            }
        }

        int findPos(int id) {
            for (int i = 0; i < B_SIZE; i++) {
                if (items[i].id == id) return i;
            }
            throw new RuntimeException("ID not found");
        }

        void update(int pos) {
            while (pos > 0 && items[pos].compareTo(items[pos - 1]) < 0) {
                swap(pos, pos - 1);
                pos--;
            }
            while (pos + 1 < B_SIZE && items[pos + 1].compareTo(items[pos]) < 0) {
                swap(pos, pos + 1);
                pos++;
            }
            rebuild();
        }

        void swap(int i, int j) {
            Item t = items[i];
            items[i] = items[j];
            items[j] = t;
        }

        void setXP(int id, int x, int p) {
            int pos = findPos(id);
            items[pos].x = x;
            items[pos].p = p;
            update(pos);
        }

        void clear(int id) {
            setXP(id, 0, -INF);
        }

        int solve(int shift) {
            while (ptr < B_SIZE) {
                int sh = shift + items[ptr].id;
                if (sh < items[ptr].p) ptr++;
                else break;
            }
            int ans = -INF;
            if (ptr > 0) ans = Math.max(ans, prefixMax[ptr - 1] + shift);
            if (ptr < B_SIZE) ans = Math.max(ans, suffixMax[ptr]);
            return ans;
        }
    }

    static int n, q;
    static int[] x = new int[N], p = new int[N];
    static int[] curX = new int[N], curP = new int[N];
    static int[] t = new int[N], a = new int[N], b = new int[N];
    static int[] ans = new int[N];

    static Block[] blocks = new Block[B_CNT];
    static int blockCount;

    static int getF(int pos, int a) {
        return curX[pos] + Math.min(curP[pos], pos - a);
    }

    static void solve(int l, int r) {
        List<Integer> important = new ArrayList<>();

        for (int i = l; i <= r; i++) {
            if (t[i] == 1 || t[i] == 2) important.add(a[i]);
        }

        Collections.sort(important);
        important = new ArrayList<>(new LinkedHashSet<>(important));

        for (int pos : important) {
            blocks[pos / B_SIZE].clear(pos % B_SIZE);
        }

        System.arraycopy(x, 0, curX, 0, n);
        System.arraycopy(p, 0, curP, 0, n);

        for (int pos : important) {
            curX[pos] = 0;
            curP[pos] = -INF;
        }

        List<Query> queries = new ArrayList<>();
        for (int i = l; i <= r; i++) {
            if (t[i] == 3) queries.add(new Query(a[i], b[i], i));
        }

        Collections.sort(queries);
        for (Block block : blocks) if (block != null) block.ptr = 0;

        for (Query qu : queries) {
            int res = -INF;
            int L = qu.a, R = qu.b;

            while (L <= R && L % B_SIZE != 0) {
                res = Math.max(res, getF(L, qu.a));
                L++;
            }
            while (L <= R && (R + 1) % B_SIZE != 0) {
                res = Math.max(res, getF(R, qu.a));
                R--;
            }
            for (int i = 0; i < blockCount; i++) {
                int bl = i * B_SIZE;
                int br = bl + B_SIZE - 1;
                if (L <= bl && br <= R) {
                    res = Math.max(res, blocks[i].solve(bl - qu.a));
                }
            }
            ans[qu.id] = res;
        }

        for (int i = l; i <= r; i++) {
            if (t[i] == 1) x[a[i]] = b[i];
            else if (t[i] == 2) p[a[i]] = b[i];
            else {
                int best = -INF;
                for (int pos : important) {
                    if (a[i] <= pos && pos <= b[i]) {
                        best = Math.max(best, x[pos] + Math.min(p[pos], pos - a[i]));
                    }
                }
                ans[i] = Math.max(ans[i], best);
            }
        }

        for (int pos : important) {
            blocks[pos / B_SIZE].setXP(pos % B_SIZE, x[pos], p[pos]);
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        n = fs.nextInt();
        q = fs.nextInt();

        for (int i = 0; i < n; i++) x[i] = fs.nextInt();
        for (int i = 0; i < n; i++) p[i] = fs.nextInt();

        for (int i = 0; i < q; i++) {
            t[i] = fs.nextInt();
            a[i] = fs.nextInt() - 1;
            if (t[i] == 3) b[i] = fs.nextInt() - 1;
            else b[i] = fs.nextInt();
        }

        blockCount = (n + B_SIZE - 1) / B_SIZE;
        for (int i = 0; i < blockCount; i++) {
            blocks[i] = new Block();
            blocks[i].init(
                    Arrays.copyOfRange(x, i * B_SIZE, i * B_SIZE + B_SIZE),
                    Arrays.copyOfRange(p, i * B_SIZE, i * B_SIZE + B_SIZE)
            );
        }

        for (int l = 0; l < q; l += B_SIZE) {
            solve(l, Math.min(l + B_SIZE - 1, q - 1));
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            if (t[i] == 3) sb.append(ans[i]).append('\n');
        }
        System.out.print(sb);
    }

    // Fast I/O
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
}
