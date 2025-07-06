import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            String[] parts = br.readLine().split(" ");
            int N = Integer.parseInt(parts[0]);
            int X = Integer.parseInt(parts[1]);
            String S = br.readLine();
            String Tstr = br.readLine();

            List<Integer> mismatch = new ArrayList<>();

            // Step 1: Record all mismatch positions
            for (int i = 0; i < N; i++) {
                if (S.charAt(i) != Tstr.charAt(i)) {
                    mismatch.add(i);
                }
            }

            int m = mismatch.size();
            if (m % 2 == 1) {
                System.out.println(-1);
                continue;
            }

            int cost = 0;
            for (int i = 0; i < m; ) {
                if (i + 1 < m && mismatch.get(i + 1) == mismatch.get(i) + 1) {
                    // Use adjacent flip for i, i+1 mismatch
                    cost += 1;
                    i += 2;
                } else {
                    // Otherwise, use cost X for two mismatched non-adjacent indices
                    cost += X;
                    i += 2;
                }
            }

            System.out.println(cost);
        }
    }
}

/*

✅ Problem Summary
We have:

Two binary strings S and T of length N.

Two operations allowed on string S to make it equal to T:

Flip two distinct characters at indices i and j — cost = X

Flip two adjacent characters at indices i and i+1 — cost = 1

🧠 Key Idea
Find mismatched positions: only where S[i] != T[i].

If mismatches are:

Even in number → possible to resolve.

Odd in number → impossible → return -1.

Traverse mismatches and:

Use adjacent flip when mismatches at i and i+1.

Else, use pair swap (general) with cost X.

🧮 Time Complexity
O(N) per test case

Handles up to 2000 total length efficiently
*/
