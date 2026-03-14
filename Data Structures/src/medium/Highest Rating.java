import java.io.*;
import java.util.*;

class TestClass {
    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int M = Integer.parseInt(br.readLine().trim());
        int Q = Integer.parseInt(br.readLine().trim());
        int N = Integer.parseInt(br.readLine().trim());

        String[] s = br.readLine().split(" ");

        HashMap<Long, Integer> map = new HashMap<>();
        int ans = 0;

        for (int i = 0; i < N; i++) {

            long val = Long.parseLong(s[i]);

            for (int k = -Q; k <= Q; k++) {

                long newVal = val + (long)k * M;

                int freq = map.getOrDefault(newVal, 0) + 1;
                map.put(newVal, freq);

                ans = Math.max(ans, freq);
            }
        }

        System.out.println(ans);
    }
}
