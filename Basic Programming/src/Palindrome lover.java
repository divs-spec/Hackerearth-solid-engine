import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());  // Trimmed

        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine().trim());  // Trimmed
            StringTokenizer st = new StringTokenizer(br.readLine());

            int count0 = 0, count1 = 0;
            for (int i = 0; i < N; i++) {
                int x = Integer.parseInt(st.nextToken().trim()) % 2;
                if (x == 0) count0++;
                else count1++;
            }

            if (N % 2 == 0) {
                // Even length: all counts must be even
                sb.append((count0 % 2 == 0 && count1 % 2 == 0) ? "1\n" : "0\n");
            } else {
                // Odd length: only one odd count allowed
                sb.append((count0 % 2 == 1 && count1 % 2 == 0) || (count1 % 2 == 1 && count0 % 2 == 0) ? "1\n" : "0\n");
            }
        }

        System.out.print(sb);
    }
}
