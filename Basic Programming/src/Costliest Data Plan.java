import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            int n = Integer.parseInt(br.readLine());
            int[] a = new int[n];
            String[] tokens = br.readLine().trim().split("\\s+");
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(tokens[i]);
            }

            int[] prefix = new int[n + 1];
            int[] suffix = new int[n + 2];
            for (int i = 0; i < n; i++) {
                prefix[i + 1] = prefix[i] | a[i];
            }
            for (int i = n - 1; i >= 0; i--) {
                suffix[i + 1] = suffix[i + 2] | a[i];
            }

            int fullMask = prefix[n];
            int maxRemovable = 0;
            for (int i = 0; i < n; i++) {
                int withoutI = prefix[i] | suffix[i + 2];
                if (withoutI == fullMask) {
                    maxRemovable = Math.max(maxRemovable, a[i]);
                }
            }
            System.out.println(maxRemovable);
        }
    }
}
