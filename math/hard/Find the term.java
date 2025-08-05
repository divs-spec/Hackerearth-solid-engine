import java.io.*;
import java.util.*;

public class TestClass {
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            String[] arr = br.readLine().split(" ");
            int a = Integer.parseInt(arr[0]);
            int b = Integer.parseInt(arr[1]);

            int m = Integer.parseInt(br.readLine());

            long[] seq = new long[6];
            seq[0] = a;
            seq[1] = b;
            seq[2] = a - b;
            seq[3] = -b;
            seq[4] = -a;
            seq[5] = b - a;

            long val = seq[(m - 1) % 6];

            // Normalize with modulo to ensure positive result
            val = ((val % MOD) + MOD) % MOD;

            sb.append(val).append("\n");
        }

        System.out.print(sb);
    }
}
