import java.io.*;
import java.util.*;

public class Main {

    static final long MOD = 1_000_000_007;

    static long countValidPermutations(int n, int[] A) {
        // Check non-increasing
        for (int i = 1; i < n; i++) {
            if (A[i] > A[i - 1]) {
                return 0;
            }
        }

        // Track used elements
        HashSet<Integer> used = new HashSet<>();
        for (int x : A) used.add(x);

        int remaining = n - used.size();

        // Factorial modulo
        long factorial = 1;
        for (int i = 1; i <= remaining; i++) {
            factorial = (factorial * i) % MOD;
        }

        return factorial;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());

        StringBuilder output = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            String[] parts = br.readLine().trim().split(" ");

            int[] A = new int[n];
            for (int i = 0; i < n; i++) {
                A[i] = Integer.parseInt(parts[i]);
            }

            output.append(countValidPermutations(n, A)).append("\n");
        }

        System.out.print(output);
    }
}
