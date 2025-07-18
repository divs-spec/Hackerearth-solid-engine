import java.io.*;
import java.util.*;

public class Main {

    static class FastReader {
        final private int BUFFER_SIZE = 1 << 16;
        private final byte[] buffer = new byte[BUFFER_SIZE];
        private int bufferPointer = 0, bytesRead = 0;
        private final InputStream stream = System.in;

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) {
                bufferPointer = 0;
                bytesRead = stream.read(buffer, bufferPointer = 0, BUFFER_SIZE);
                if (bytesRead == -1)
                    buffer[0] = -1;
            }
            return buffer[bufferPointer++];
        }

        public int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int neg = 1;
            if (c == '-') {
                neg = -1;
                c = read();
            }

            int res = 0;
            do {
                res = res * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            return res * neg;
        }

        public long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int neg = 1;
            if (c == '-') {
                neg = -1;
                c = read();
            }

            long res = 0;
            do {
                res = res * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            return res * neg;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();

        int n = fr.nextInt();
        long[] books = new long[n];
        long[] location = new long[n];

        for (int i = 0; i < n; i++) {
            books[i] = fr.nextLong();
        }

        for (int i = 0; i < n; i++) {
            location[i] = fr.nextLong();
        }

        Arrays.sort(books);
        Arrays.sort(location);

        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += Math.abs(books[i] - location[i]);
        }

        System.out.println(ans);
    }
}
