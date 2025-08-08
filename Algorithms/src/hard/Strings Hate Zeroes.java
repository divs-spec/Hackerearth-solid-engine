import java.io.*;
import java.util.*;

class TestClass {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        int n = s.length();
        long ans = 0;

        // For each start index
        for (int i = 0; i < n; i++) {
            int[] freq = new int[10];
            int distinct = 0;
            int count0 = 0;

            // Extend end index
            for (int j = i; j < n; j++) {
                int d = s.charAt(j) - '0';
                if (freq[d] == 0) distinct++;
                freq[d]++;
                if (d == 0) count0++;

                int len = j - i + 1;
                if (count0 * distinct < len) {
                    ans++;
                }
            }
        }
        System.out.println(ans);
    }
}
