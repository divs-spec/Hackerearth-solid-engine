import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            int[] heaps = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            int xorSum = 0;

            for (int i = 0; i < n; i++) {
                heaps[i] = Integer.parseInt(st.nextToken());
                xorSum ^= heaps[i];
            }

            int count = 0;
            for (int i = 0; i < n; i++) {
                int reduced = heaps[i] ^ xorSum;
                if (heaps[i] > reduced) {
                    count++;
                }
            }

            System.out.println(count);
        }
    }
}
