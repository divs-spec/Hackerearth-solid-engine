import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        RetroactiveIntegers solver = new RetroactiveIntegers();
        solver.solve(1, in, out);
        out.close();
    }

    static class RetroactiveIntegers {
        public static int mod = 1000000007;
        public static int w = 15;

        public static int[][] getIdentity(int n) {
            int[][] res = new int[w][3];
            for (int i = 0; i < w; i++) {
                res[i][0] = i;
                res[i][1] = 1;
            }
            return res;
        }

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int q = in.nextInt();
            RetroactiveIntegers.Query[] qs = new RetroactiveIntegers.Query[q];
            int[] times = new int[q];
            for (int i = 0; i < q; i++) {
                qs[i] = new RetroactiveIntegers.Query(in, i);
                times[i] = qs[i].time;
            }
            Arrays.sort(times);
            for (int i = 0; i < q; i++) {
                qs[i].time = Arrays.binarySearch(times, qs[i].time);
            }
            RetroactiveIntegers.SegmentTree os = new RetroactiveIntegers.SegmentTree(q);
            for (int i = 0; i < q; i++) {
                if (qs[i].which != -1) {
                    int[][] x = os.query(0, qs[i].time);
                    out.println(x[qs[i].which][2]);
                } else {
                    os.modify(qs[i].time, qs[i].vv);
                }
            }
        }

        public static int[][] combine(int[][] a, int[][] b) {
            int[][] ret = new int[w][3];
            for (int i = 0; i < w; i++) {
                int tt = b[i][0];
                ret[i][0] = a[tt][0];
                ret[i][1] = (int) ((1L * a[tt][1] * b[i][1]) % mod);
                ret[i][2] = (int) ((1L * a[tt][2] * b[i][1] + b[i][2]) % mod);
            }
            return ret;
        }

        static class Query {
            public int time;
            public int idx;
            public int[][] vv;
            public int which;

            public Query(InputReader in, int idx) {
                this.idx = idx;
                time = in.nextInt();
                char c = in.next().charAt(0);
                vv = new int[w][3];
                for (int i = 0; i < w; i++) {
                    vv[i][0] = i;
                    vv[i][1] = 1;
                }
                which = -1;
                switch (c) {
                    case '?': {
                        which = in.next().charAt(0) - 'a';
                        break;
                    }
                    case '=': {
                        int a1 = in.next().charAt(0) - 'a';
                        int a2 = in.next().charAt(0) - 'a';
                        vv[a1][0] = a2;
                        break;
                    }
                    case '*': {
                        int a = in.next().charAt(0) - 'a';
                        int m = in.nextInt();
                        vv[a][1] = m;
                        break;
                    }
                    case '!': {
                        int a = in.next().charAt(0) - 'a';
                        int m = in.nextInt();
                        vv[a][1] = 0;
                        vv[a][2] = m;
                        break;
                    }
                    case '+': {
                        int a = in.next().charAt(0) - 'a';
                        int m = in.nextInt();
                        vv[a][2] = m;
                        break;
                    }
                }
            }

        }

        static class SegmentTree {
            int n;
            int[][][] value;

            public SegmentTree(int n) {
                this.n = n;
                value = new int[4 * n][][];
                init(0, 0, n - 1);
            }

            void init(int root, int left, int right) {
                value[root] = getIdentity(w);
                if (left != right) {
                    init(2 * root + 1, left, (left + right) / 2);
                    init(2 * root + 2, (left + right) / 2 + 1, right);
                }
            }

            public int[][] query(int from, int to) {
                return query(from, to, 0, 0, n - 1);
            }

            int[][] query(int from, int to, int root, int left, int right) {
                if (from == left && to == right)
                    return value[root];
                int mid = (left + right) >> 1;
                if (from <= mid && to > mid)
                    return combine(
                            query(from, Math.min(to, mid), root * 2 + 1, left, mid),
                            query(Math.max(from, mid + 1), to, root * 2 + 2, mid + 1, right));
                else if (from <= mid)
                    return query(from, Math.min(to, mid), root * 2 + 1, left, mid);
                else if (to > mid)
                    return query(Math.max(from, mid + 1), to, root * 2 + 2, mid + 1, right);
                else
                    throw new RuntimeException("Incorrect query from " + from + " to " + to);
            }

            public void modify(int pos, int[][] nv) {
                modify(pos, nv, 0, 0, n - 1);
            }

            void modify(int pos, int[][] nv, int root, int left, int right) {
                if (pos == left && pos == right) {
                    value[root] = nv;
                    return;
                }
                int mid = (left + right) >> 1;
                if (pos <= mid)
                    modify(pos, nv, 2 * root + 1, left, mid);
                if (pos > mid)
                    modify(pos, nv, 2 * root + 2, mid + 1, right);
                value[root] = combine(value[2 * root + 1], value[2 * root + 2]);
            }

        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (this.numChars == -1) {
                throw new InputMismatchException();
            } else {
                if (this.curChar >= this.numChars) {
                    this.curChar = 0;

                    try {
                        this.numChars = this.stream.read(this.buf);
                    } catch (IOException var2) {
                        throw new InputMismatchException();
                    }

                    if (this.numChars <= 0) {
                        return -1;
                    }
                }

                return this.buf[this.curChar++];
            }
        }

        public int nextInt() {
            int c;
            for (c = this.read(); isSpaceChar(c); c = this.read()) {
                ;
            }

            byte sgn = 1;
            if (c == 45) {
                sgn = -1;
                c = this.read();
            }

            int res = 0;

            while (c >= 48 && c <= 57) {
                res *= 10;
                res += c - 48;
                c = this.read();
                if (isSpaceChar(c)) {
                    return res * sgn;
                }
            }

            throw new InputMismatchException();
        }

        public String next() {
            int c;
            while (isSpaceChar(c = this.read())) {
                ;
            }

            StringBuilder result = new StringBuilder();
            result.appendCodePoint(c);

            while (!isSpaceChar(c = this.read())) {
                result.appendCodePoint(c);
            }

            return result.toString();
        }

        public static boolean isSpaceChar(int c) {
            return c == 32 || c == 10 || c == 13 || c == 9 || c == -1;
        }

    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void close() {
            writer.close();
        }

        public void println(int i) {
            writer.println(i);
        }

    }
}
