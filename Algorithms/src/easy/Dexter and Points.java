import java.io.*;
import java.util.*;

public class Main {
    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());

        StringTokenizer st = new StringTokenizer(br.readLine());
        long ans = 1;

        for (int i = 0; i < N; i++) {
            long a = Long.parseLong(st.nextToken());

            long even = a / 2 + 1;        // count of even numbers in [0, a]
            long odd = (a + 1) - even;    // count of odd numbers

            long ways = ( (even * even) % MOD + (odd * odd) % MOD ) % MOD;
            ans = (ans * ways) % MOD;
        }

        System.out.println(ans);
    }
}
