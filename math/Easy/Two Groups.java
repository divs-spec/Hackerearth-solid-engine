import java.io.*;

public class TestClass {
    static final long MOD = 1000000007L;

    static long pow(long n) {
        long res = 1;
        long base = 2;

        while (n > 0) {
            if ((n & 1) == 1) {
                res = (res * base) % MOD;
            }
            base = (base * base) % MOD;
            n >>= 1;
        }

        return res;
    }

    public static void main(String[] args) throws Exception {
        BufferedInputStream in = new BufferedInputStream(System.in);

        StringBuilder sb = new StringBuilder();

        int t = 0;
        int c;

        do {
            c = in.read();
        } while (c <= ' ');

        while (c > ' ') {
            t = t * 10 + c - '0';
            c = in.read();
        }

        while (t-- > 0) {
            long n = 0;

            do {
                c = in.read();
            } while (c <= ' ');

            while (c > ' ') {
                n = n * 10 + c - '0';
                c = in.read();
            }

            sb.append((pow(n) - 2 + MOD) % MOD).append('\n');
        }

        System.out.print(sb);
    }
}
