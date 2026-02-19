import java.io.*;
import java.util.*;

public class Main {

    static final int N = 100005;
    static final long MOD = 1000000007L;

    static ArrayList<Integer>[] v = new ArrayList[N];
    static long[] a = new long[N];
    static long[] fact = new long[N];
    static long[] ifact = new long[N];

    static int n;
    static long x;
    static long c;

    // fast power (modular exponentiation)
    static long power(long x, long y, long m) {
        long res = 1;
        x %= m;
        while (y > 0) {
            if ((y & 1) == 1) res = (res * x) % m;
            x = (x * x) % m;
            y >>= 1;
        }
        return res;
    }

    // precompute factorials and inverse factorials
    static void process() {
        fact[0] = 1;
        for (int i = 1; i < N; i++)
            fact[i] = (fact[i - 1] * i) % MOD;

        for (int i = 0; i < N; i++)
            ifact[i] = power(fact[i], MOD - 2, MOD);
    }

    static long nCr(long n, long r) {
        if (r > n || r < 0) return 0;
        if (r == n) return 1;
        long ans = (ifact[(int)(n - r)] * ifact[(int)r]) % MOD;
        ans = (ans * fact[(int)n]) % MOD;
        return ans;
    }

    static long dfs(int u, int p) {
        long sum = a[u];
        for (int to : v[u]) {
            if (to != p) {
                sum += dfs(to, u);
            }
        }
        if (sum % x == 0) c++;
        return sum;
    }

    static void solve(FastScanner fs, StringBuilder out) throws Exception {
        n = fs.nextInt();
        x = fs.nextLong();

        for (int i = 0; i <= n; i++) {
            if (v[i] == null) v[i] = new ArrayList<>();
            else v[i].clear();
            a[i] = 0;
        }

        c = 0;

        for (int i = 1; i <= n; i++)
            a[i] = fs.nextLong();

        for (int i = 0; i < n - 1; i++) {
            int uu = fs.nextInt();
            int vv = fs.nextInt();
            v[uu].add(vv);
            v[vv].add(uu);
        }

        long sum = dfs(1, 0);
        if (sum % x != 0) c = 0;

        for (int i = 1; i <= n; i++) {
            out.append(nCr(c - 1, i - 1)).append(' ');
        }
        out.append('\n');
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int T = fs.nextInt();
        process();

        while (T-- > 0) {
            solve(fs, out);
        }

        System.out.print(out.toString());
    }

    // -------- FAST INPUT --------
    static class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int readByte() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        long nextLong() throws IOException {
            long val = 0;
            int c;
            boolean neg = false;
            do {
                c = readByte();
            } while (c <= ' ');

            if (c == '-') {
                neg = true;
                c = readByte();
            }

            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = readByte();
            }
            return neg ? -val : val;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}
