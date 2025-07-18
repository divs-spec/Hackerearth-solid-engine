// Greedy pairing strategy with a hash map.
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Fast input/output
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());

        while (tc-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            int ans = 0;
            Map<Integer, Integer> map = new HashMap<>();

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                int a = Integer.parseInt(st.nextToken());

                if (map.getOrDefault(a + 1, 0) == 0) {
                    ans++;
                } else {
                    map.put(a + 1, map.get(a + 1) - 1);
                }

                map.put(a, map.getOrDefault(a, 0) + 1);
            }

            if (k - ans < 0) {
                System.out.println("-1");
            } else {
                System.out.println(k - ans);
            }
        }
    }
}
