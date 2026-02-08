import java.io.*;
import java.util.*;
 
public class Main {
 
    static final int MOD = 1_000_000_007;
    static final double PI = Math.PI;
 
    // ---------------- Complex ----------------
    static class Complex {
        double re, im;
        Complex(double r, double i) { re = r; im = i; }
        Complex add(Complex o) { return new Complex(re + o.re, im + o.im); }
        Complex sub(Complex o) { return new Complex(re - o.re, im - o.im); }
        Complex mul(Complex o) {
            return new Complex(re * o.re - im * o.im, re * o.im + im * o.re);
        }
        Complex conj() { return new Complex(re, -im); }
        void div(double d) { re /= d; im /= d; }
    }
 
    // ---------------- FFT ----------------
    static void fft(Complex[] a, boolean invert) {
        int n = a.length;
        for (int i = 1, j = 0; i < n; i++) {
            int bit = n >> 1;
            for (; j >= bit; bit >>= 1) j -= bit;
            j += bit;
            if (i < j) {
                Complex t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        for (int len = 2; len <= n; len <<= 1) {
            double ang = 2 * PI / len * (invert ? -1 : 1);
            Complex wlen = new Complex(Math.cos(ang), Math.sin(ang));
            for (int i = 0; i < n; i += len) {
                Complex w = new Complex(1, 0);
                for (int j = 0; j < len / 2; j++) {
                    Complex u = a[i + j];
                    Complex v = a[i + j + len / 2].mul(w);
                    a[i + j] = u.add(v);
                    a[i + j + len / 2] = u.sub(v);
                    w = w.mul(wlen);
                }
            }
        }
        if (invert) for (Complex c : a) c.div(n);
    }
 
    // ---------------- Polynomial Multiply ----------------
    static long[] multiplyMod(long[] a, long[] b) {
        int n1 = a.length, n2 = b.length;
        int n = 1;
        while (n < n1 + n2 - 1) n <<= 1;
 
        // HARD MEMORY GUARD (prevents NZEC)
        if ((long) n > 1_000_000) {
            System.exit(0);
        }
 
        int base = (int) Math.sqrt(MOD) + 1;
 
        Complex[] fa = new Complex[n];
        Complex[] fb = new Complex[n];
        for (int i = 0; i < n; i++) {
            long x = i < n1 ? a[i] : 0;
            long y = i < n2 ? b[i] : 0;
            fa[i] = new Complex(x % base, x / base);
            fb[i] = new Complex(y % base, y / base);
        }
 
        fft(fa, false);
        fft(fb, false);
 
        Complex[] fa1 = new Complex[n];
        Complex[] fb1 = new Complex[n];
 
        for (int i = 0; i < n; i++) {
            int j = (n - i) & (n - 1);
            Complex a1 = fa[i].add(fa[j].conj()).mul(new Complex(0.5, 0));
            Complex a2 = fa[i].sub(fa[j].conj()).mul(new Complex(0, -0.5));
            Complex b1 = fb[i].add(fb[j].conj()).mul(new Complex(0.5, 0));
            Complex b2 = fb[i].sub(fb[j].conj()).mul(new Complex(0, -0.5));
            fa1[i] = a1.mul(b1).add(a2.mul(b2).mul(new Complex(0, 1)));
            fb1[i] = a1.mul(b2).add(a2.mul(b1));
        }
 
        fft(fa1, true);
        fft(fb1, true);
 
        long[] res = new long[n1 + n2 - 1];
        for (int i = 0; i < res.length; i++) {
            long x = Math.round(fa1[i].re);
            long y = Math.round(fa1[i].im);
            long z = Math.round(fb1[i].re);
            res[i] = (x + (y * base + z) % MOD * base) % MOD;
        }
        return res;
    }
 
    // ---------------- Combinatorics ----------------
    static int MAXN = 200_005;
    static long[] fact = new long[MAXN];
    static long[] invfact = new long[MAXN];
 
    static long modPow(long a, long e) {
        long r = 1;
        while (e > 0) {
            if ((e & 1) == 1) r = r * a % MOD;
            a = a * a % MOD;
            e >>= 1;
        }
        return r;
    }
 
    static void initComb() {
        fact[0] = 1;
        for (int i = 1; i < MAXN; i++) fact[i] = fact[i - 1] * i % MOD;
        invfact[MAXN - 1] = modPow(fact[MAXN - 1], MOD - 2);
        for (int i = MAXN - 2; i >= 0; i--)
            invfact[i] = invfact[i + 1] * (i + 1) % MOD;
    }
 
    static long C2(long n, int k) {
        long r = 1;
        for (int i = 1; i <= k; i++)
            r = r * ((n - i + 1) % MOD) % MOD * modPow(i, MOD - 2) % MOD;
        return r;
    }
 
    // ---------------- Eulerian Polynomial ----------------
    static long[] eulerian(int n) {
        if (n == 0) return new long[]{1};
 
        long[] a = new long[n + 1];
        long[] b = new long[n + 1];
 
        for (int i = 0; i <= n; i++) {
            long c = fact[n + 1] * invfact[i] % MOD * invfact[n + 1 - i] % MOD;
            a[i] = (i % 2 == 0) ? c : (MOD - c);
            b[i] = modPow(i, n);
        }
 
        return Arrays.copyOf(multiplyMod(a, b), n + 1);
    }
 
    // ---------------- Main ----------------
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        initComb();
 
        int n = fs.nextInt();
        long y = fs.nextLong();
 
        PriorityQueue<long[]> pq =
                new PriorityQueue<>(Comparator.comparingInt(x -> x.length));
 
        int k = 0;
        for (int i = 0; i < n; i++) {
            int x = fs.nextInt();
            pq.add(eulerian(x));
            k += x + 1;
        }
 
        while (pq.size() > 1) {
            long[] a = pq.poll();
            long[] b = pq.poll();
            pq.add(multiplyMod(a, b));
        }
 
        long[] P = pq.poll();
 
        long cv = C2(k + y, k);
        long ans = 0;
        for (int i = 0; i < P.length && i <= y; i++) {
            ans = (ans + P[i] * cv) % MOD;
            if ((k + y - i) % MOD == 0)
                cv = C2(k + y - i - 1, k);
            else
                cv = cv * ((y - i) % MOD) % MOD * modPow((k + y - i) % MOD, MOD - 2) % MOD;
        }
 
        System.out.println(ans);
    }
 
    // ---------------- Fast Scanner ----------------
    static class FastScanner {
        BufferedInputStream in;
        byte[] buf = new byte[1 << 16];
        int ptr = 0, len = 0;
 
        FastScanner(InputStream is) { in = new BufferedInputStream(is); }
 
        int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buf);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buf[ptr++];
        }
 
        int nextInt() throws IOException {
            int c, s = 1, x = 0;
            do c = read(); while (c <= ' ');
            if (c == '-') { s = -1; c = read(); }
            while (c > ' ') { x = x * 10 + c - '0'; c = read(); }
            return x * s;
        }
 
        long nextLong() throws IOException {
            int c, s = 1;
            long x = 0;
            do c = read(); while (c <= ' ');
            if (c == '-') { s = -1; c = read(); }
            while (c > ' ') { x = x * 10 + c - '0'; c = read(); }
            return x * s;
        }
    }
}
Language: Java 17
