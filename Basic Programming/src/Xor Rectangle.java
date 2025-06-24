import java.util.*;
import java.io.*;

public class Main {
    static int MAX_BITS = 21; // since 2^20 > 10^6

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] S = new int[n + 1]; // 1-based
        String[] parts = br.readLine().split(" ");
        for (int i = 1; i <= n; i++) {
            S[i] = Integer.parseInt(parts[i - 1]);
        }

        int[][] bitPrefix = new int[MAX_BITS][n + 2];

        // Build prefix sums of bits
        for (int b = 0; b < MAX_BITS; b++) {
            for (int i = 1; i <= n; i++) {
                bitPrefix[b][i] = bitPrefix[b][i - 1] + ((S[i] >> b) & 1);
            }
        }

        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (q-- > 0) {
            String[] qparts = br.readLine().split(" ");
            int x1 = Integer.parseInt(qparts[0]);
            int y1 = Integer.parseInt(qparts[1]);
            int x2 = Integer.parseInt(qparts[2]);
            int y2 = Integer.parseInt(qparts[3]);

            int len1 = x2 - x1 + 1;
            int len2 = y2 - y1 + 1;
            long total = 0;

            for (int b = 0; b < MAX_BITS; b++) {
                int cnt1 = bitPrefix[b][x2] - bitPrefix[b][x1 - 1];
                int cnt2 = bitPrefix[b][y2] - bitPrefix[b][y1 - 1];

                long contrib = 1L << b;
                long bitCount = (long) cnt1 * (len2 - cnt2) + (long) (len1 - cnt1) * cnt2;
                total += contrib * bitCount;
            }

            sb.append(total).append("\n");
        }

        System.out.print(sb);
