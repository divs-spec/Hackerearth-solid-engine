import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.io.BufferedReader;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        FastPrinter out = new FastPrinter(outputStream);
        ClothesArrangement solver = new ClothesArrangement();
        solver.solve(1, in, out);
        out.close();
    }

    static class ClothesArrangement {
        public void solve(int testNumber, FastScanner in, FastPrinter out) {
            int n = in.nextInt();
            if (n < 1 || n > 500000) throw new AssertionError();
            int[] a = in.readIntArray(n);
            int q = in.nextInt();
            if (q < 1 || q > 500000) throw new AssertionError();
            int[] type = new int[q];
            int[] colorQ = new int[q];
            int[] kQ = new int[q];
            final int MAXN = 1000001;
            int[] count = new int[MAXN];
            for (int i : a) count[i]++;
            for (int i : a) if (i < 1 || i > 500000) throw new AssertionError();
            for (int i = 0; i < q; i++) {
                type[i] = in.nextInt();
                if (type[i] != 1 && type[i] != 2) throw new AssertionError();
                colorQ[i] = in.nextInt();
                if (colorQ[i] < 1 || colorQ[i] > 500000) throw new AssertionError();
                if (type[i] == 2) {
                    kQ[i] = in.nextInt();
                    if (kQ[i] < 1 || kQ[i] > 1000000) throw new AssertionError();
                } else {
                    count[colorQ[i]]++;
                }
            }
            int[][] numbers = new int[MAXN][];
            FenwickKth[] f = new FenwickKth[MAXN];
            Fenwick globalF = new Fenwick(n + q);
            for (int i = 0; i < MAXN; i++) {
                if (count[i] > 0) {
                    numbers[i] = new int[count[i]];
                    f[i] = new FenwickKth(count[i]);
                    count[i] = 0;
                }
            }
            int pos = 0;
            for (int x : a) {
                numbers[x][count[x]] = pos;
                f[x].add(count[x], 1);
                globalF.add(pos, 1);
                pos++;
                count[x]++;
            }
            for (int curQ = 0; curQ < q; curQ++) {
                int x = colorQ[curQ];
                if (type[curQ] == 1) {
                    numbers[x][count[x]] = pos;
                    globalF.add(pos, 1);
                    f[x].add(count[x], 1);
                    count[x]++;
                    pos++;
                } else {
                    int k = kQ[curQ];
                    int have = f[x] == null ? 0 : f[x].getSum(Integer.MAX_VALUE);
                    if (have < k) {
                        out.println(-1);
                    } else {
                        int localPos = f[x].getKth(have - k + 1);
                        f[x].add(localPos, -1);
                        int globalPos = numbers[x][localPos];
                        globalF.add(globalPos, -1);
                        out.println(globalF.getSum(globalPos, Integer.MAX_VALUE));
                    }
                }
            }
        }

    }

    static class FastScanner extends BufferedReader {
        public FastScanner(InputStream is) {
            super(new InputStreamReader(is));
        }

        public int read() {
            try {
                int ret = super.read();
//            if (isEOF && ret < 0) {
//                throw new InputMismatchException();
//            }
//            isEOF = ret == -1;
                return ret;
            } catch (IOException e) {
                throw new InputMismatchException();
            }
        }

        static boolean isWhiteSpace(int c) {
            return c >= 0 && c <= 32;
        }

        public int nextInt() {
            int c = read();
            while (isWhiteSpace(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int ret = 0;
            while (c >= 0 && !isWhiteSpace(c)) {
                if (c < '0' || c > '9') {
                    throw new NumberFormatException("digit expected " + (char) c
                            + " found");
                }
                ret = ret * 10 + c - '0';
                c = read();
            }
            return ret * sgn;
        }

        public String readLine() {
            try {
                return super.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        public int[] readIntArray(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }

    }

    static class FastPrinter extends PrintWriter {
        public FastPrinter(OutputStream out) {
            super(out);
        }

        public FastPrinter(Writer out) {
            super(out);
        }

    }

    static class FenwickKth {
        int[] a;

        public FenwickKth(int n) {
            a = new int[Integer.highestOneBit(n) << 1];
        }

        public void add(int x, int y) {
            for (int i = x; i < a.length; i |= i + 1) {
                a[i] += y;
            }
        }

        public int getSum(int x) {
            if (x >= a.length) x = a.length - 1;
            int ret = 0;
            for (int i = x; i >= 0; i = (i & (i + 1)) - 1) {
                ret += a[i];
            }
            return ret;
        }

        public int getKth(int k) {
            int l = 0;
            int r = a.length;
            while (l < r - 1) {
                int mid = l + r >> 1;
                if (a[mid - 1] >= k) {
                    r = mid;
                } else {
                    k -= a[mid - 1];
                    l = mid;
                }
            }
            return l;
        }

    }

    static class Fenwick {
        int[] a;

        public Fenwick(int n) {
            a = new int[n];
        }

        public void add(int x, int y) {
            for (int i = x; i < a.length; i |= i + 1) {
                a[i] += y;
            }
        }

        public int getSum(int x) {
            if (x >= a.length) x = a.length - 1;
            int ret = 0;
            for (int i = x; i >= 0; i = (i & (i + 1)) - 1) {
                ret += a[i];
            }
            return ret;
        }

        public int getSum(int l, int r) {
            return getSum(r - 1) - getSum(l - 1);
        }

    }
}
