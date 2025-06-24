import java.io.*;
import java.util.*;

class TestClass {
    static char[] map = {'a', 'b', 'c', 'd'};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            long k = Long.parseLong(br.readLine());
            int halfLen = 1;
            long total = 0;

            while (true) {
                long count = (long) Math.pow(4, halfLen);
                if (k <= count) break;
                k -= count;
                halfLen++;
            }

            // Convert (k-1) to base 4
            k--; // convert to 0-based index
            char[] firstHalf = new char[halfLen];
            for (int i = halfLen - 1; i >= 0; i--) {
                firstHalf[i] = map[(int) (k % 4)];
                k /= 4;
            }

            // Form the palindrome
            StringBuilder full = new StringBuilder();
            for (char c : firstHalf) full.append(c);
            for (int i = halfLen - 1; i >= 0; i--) full.append(firstHalf[i]);

            sb.append(full).append("\n");
        }

        System.out.print(sb);
    }
}
