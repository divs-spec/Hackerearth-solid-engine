import java.io.*;
import java.util.*;

public class Main {

    // Function to count the number of set bits (1s) in the binary representation
    public static int countSetBits(int x) {
        return Integer.bitCount(x); // equivalent to __builtin_popcount in C++
    }

    public static void main(String[] args) throws IOException {
        // Fast input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            int ans = Integer.MAX_VALUE;

            String[] tokens = br.readLine().split(" ");

            for (int i = 0; i < n; i++) {
                int x = Integer.parseInt(tokens[i]);
                int setBits = countSetBits(x);
                ans = Math.min(ans, setBits);
            }

            sb.append(ans).append('\n');
        }

        // Fast output
        System.out.print(sb);
    }
}
