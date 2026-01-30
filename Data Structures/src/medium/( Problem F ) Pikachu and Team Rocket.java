import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        TaskD solver = new TaskD();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskD {
        public static int n;
        public static int q;
        public static int front;
        public static int back;
        public static int[] arr;

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            n = in.nextInt();
            q = in.nextInt();

            front = q;
            back = n + q - 1;
            arr = in.readIntArray(n);
            TaskD.SegmentTree root = new TaskD.SegmentTree(0, q + q + n);
            while (q-- > 0) {
                int type = in.nextInt();
                if (type == 1) {
                    int l = in.nextInt() - 1, r = in.nextInt() - 1;
                    out.println(root.query(l, r)[0]);
                } else {
                    int t = in.nextInt(), pos = in.nextInt() - 1;
                    int k = root.unset(pos);
                    if (t == 2) root.set(--front, k);
                    else root.set(++back, k);
                }
            }
        }

        static class SegmentTree {
            public int start;
            public int end;
            public int count;
            public int max;
            public TaskD.SegmentTree left;
            public TaskD.SegmentTree right;

            public SegmentTree(int start, int end) {
                this.start = start;
                this.end = end;
                if (start != end) {
                    int mid = (start + end) / 2;
                    left = new TaskD.SegmentTree(start, mid);
                    right = new TaskD.SegmentTree(mid + 1, end);
                    this.max = Math.max(left.max, right.max);
                    this.count = left.count + right.count;
                } else if (front <= start && start <= back) {
                    max = arr[start - q];
                    count = 1;
                } else {
                    max = -1000000;
                    count = 0;
                }
            }

            public int[] query(int from, int to) {
                if (from == 0 && to == this.count - 1) {
                    return new int[]{this.max, this.count};
                }

                if (left.count <= from) {
                    return right.query(from - left.count, to - left.count);
                }
                if (left.count > to) {
                    return left.query(from, to);
                }

                int want = to - from + 1;
                int[] r1 = left.query(from, left.count - 1);
                int[] r2 = right.query(0, want - r1[1] - 1);

                return new int[]{Math.max(r1[0], r2[0]), r1[1] + r2[1]};
            }

            public int unset(int index) {
                if (this.start == this.end) {
                    int v = this.max;
                    this.count = 0;
                    this.max = -1000000;
                    return v;
                }
                int res;
                if (left.count > index) {
                    res = left.unset(index);
                } else {
                    res = right.unset(index - left.count);
                }
                this.count = left.count + right.count;
                this.max = Math.max(left.max, right.max);
                return res;
            }

            public void set(int pos, int val) {
                if (start == pos && end == pos) {
                    this.count = 1;
                    this.max = val;
                    return;
                }
                int mid = (start + end) / 2;
                if (pos <= mid) left.set(pos, val);
                else right.set(pos, val);
                this.count = left.count + right.count;
                this.max = Math.max(left.max, right.max);
            }

        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1 << 16];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int[] readIntArray(int tokens) {
            int[] ret = new int[tokens];
            for (int i = 0; i < tokens; i++) {
                ret[i] = nextInt();
            }
            return ret;
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
