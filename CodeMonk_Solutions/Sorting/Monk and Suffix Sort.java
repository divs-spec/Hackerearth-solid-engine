import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class TestClass {
    public static void main(String args[]) throws Exception {
        // BufferedReader for fast input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String[] input = br.readLine().split(" ");
        String s = input[0];
        int k = Integer.parseInt(input[1]);

        int n = s.length();
        List<String> suffixes = new ArrayList<>();

        // Step 1: Generate all suffixes
        for (int i = 0; i < n; i++) {
            suffixes.add(s.substring(i));
        }

        // Step 2: Sort suffixes lexicographically
        Collections.sort(suffixes);

        // Step 3: Output the k-th smallest suffix (1-based index)
        System.out.println(suffixes.get(k - 1));
    }
}
