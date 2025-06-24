import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        while (T-- > 0) {
            String s = br.readLine().trim();
            int n = s.length();

            Map<String, Integer> map = new HashMap<>();
            int[] freq = new int[26];
            String key = Arrays.toString(freq);
            map.put(key, 1); // initial state

            long count = 0;
            for (int i = 0; i < n; i++) {
                char ch = s.charAt(i);
                if (ch < 'a' || ch > 'z') continue; // Skip invalid chars

                freq[ch - 'a'] ^= 1; // toggle bit
                key = Arrays.toString(freq);

                count += map.getOrDefault(key, 0);
                map.put(key, map.getOrDefault(key, 0) + 1);
            }

            System.out.println(count);
        }
    }
}
