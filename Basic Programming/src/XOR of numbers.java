import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Fast input reader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nq = br.readLine().split(" ");
        int n = Integer.parseInt(nq[0]);
        int q = Integer.parseInt(nq[1]);

        int[] a = new int[n];
        String[] parts = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(parts[i]);
        }

        // Prefix XOR array
        int[] prefixXor = new int[n];
        prefixXor[0] = a[0];
        for (int i = 1; i < n; i++) {
            prefixXor[i] = prefixXor[i - 1] ^ a[i];
        }

        int totalXor = prefixXor[n - 1];

        // Handle queries
        StringBuilder sb = new StringBuilder();
        for (int qi = 0; qi < q; qi++) {
            String[] lr = br.readLine().split(" ");
            int l = Integer.parseInt(lr[0]);
            int r = Integer.parseInt(lr[1]);

            int excludedRangeXor;
            if (l == 1) {
                excludedRangeXor = prefixXor[r - 1];
            } else {
                excludedRangeXor = prefixXor[r - 1] ^ prefixXor[l - 2];
            }

            int result = totalXor ^ excludedRangeXor;
            sb.append(result).append("\n");
        }

        System.out.print(sb);
    }
}
